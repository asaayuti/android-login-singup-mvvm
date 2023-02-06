package net.simplifiedcoding.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import net.simplifiedcoding.data.network.AuthApi
import net.simplifiedcoding.data.network.Resource
import net.simplifiedcoding.data.repository.AuthRepository
import net.simplifiedcoding.databinding.FragmentLoginBinding
import net.simplifiedcoding.ui.base.BaseFragment
import net.simplifiedcoding.ui.enable
import net.simplifiedcoding.ui.home.HomeActivity
import net.simplifiedcoding.ui.startNewActivity
import net.simplifiedcoding.ui.visible

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(it.value.access_token!!)
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }


        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()
            binding.progressbar.visible(true)
            viewModel.login(email, password)
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

}