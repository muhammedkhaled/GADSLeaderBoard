package com.khaled.gadsleaderboard.ui.sumbit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.gadsleaderboard.data.repository.SubmissionRepository
import com.khaled.gadsleaderboard.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class SubmissionViewModel : ViewModel() {

    // repo
    private val submissionRepository = SubmissionRepository()

    // liveData
    var submissionMutableLiveData: MutableLiveData<Resource<Boolean>> =
        MutableLiveData()

    fun submitProject(
        url: String,
        email: String,
        firstName: String,
        lastName: String,
        projectLink: String
    ) = viewModelScope.launch {
        try {
            submissionMutableLiveData.postValue(Resource.Loading())
            val response =
                submissionRepository.submitProject(url, email, firstName, lastName, projectLink)
            if (response.isSuccessful) {
                submissionMutableLiveData.postValue(Resource.Success(true))
            } else {
                submissionMutableLiveData.postValue(Resource.Error("Response Failed"))
            }
        } catch (e: Exception) {
            submissionMutableLiveData.postValue(Resource.Error(e.message.toString()))

        }

    }

}