package net.simplifiedcoding.ui.base

import androidx.lifecycle.ViewModel
import net.simplifiedcoding.data.network.UserApi
import net.simplifiedcoding.data.repository.BaseRepository

abstract class BaseViewModel (
    private val repository: BaseRepository
): ViewModel(){

    suspend fun logout(api: UserApi) = repository.logout(api)

}