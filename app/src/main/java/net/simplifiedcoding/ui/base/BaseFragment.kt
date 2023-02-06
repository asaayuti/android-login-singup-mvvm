package net.simplifiedcoding.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.UserPreferences
import net.simplifiedcoding.data.network.RemoteDataSource
import net.simplifiedcoding.data.network.UserApi
import net.simplifiedcoding.data.repository.BaseRepository
import net.simplifiedcoding.ui.auth.AuthActivity
import net.simplifiedcoding.ui.startNewActivity

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }

    // lifecycleScope.launch to call suspending function
    fun logout() = lifecycleScope.launch {
        // get auth token
        val authToken = userPreferences.authToken.first()
        // get api
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        // call logout function to hit api
        viewModel.logout(api)
        // clear local storage
        userPreferences.clear()
        // start login activity
        requireActivity().startNewActivity(AuthActivity::class.java)

    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}