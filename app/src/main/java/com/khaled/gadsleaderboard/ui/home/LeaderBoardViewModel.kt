package com.khaled.gadsleaderboard.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.gadsleaderboard.data.models.LeaderBoardResponseItem
import com.khaled.gadsleaderboard.data.repository.LeaderBoardRepository
import com.khaled.gadsleaderboard.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class LeaderBoardViewModel : ViewModel() {

    // repo
    val leaderBoardRepository = LeaderBoardRepository()

    // liveData
    var leaderBoardMutableLiveData: MutableLiveData<Resource<ArrayList<LeaderBoardResponseItem>>> =
        MutableLiveData()
    var filterBoardMutableLiveData: MutableLiveData<String> = MutableLiveData()

    fun getLeaderBoard() = viewModelScope.launch {
        try {
            leaderBoardMutableLiveData.postValue(Resource.Loading())
            val response = leaderBoardRepository.getLeaderBoard(filterBoardMutableLiveData.value!!)
            if (response.isSuccessful) {
                response.body()?.let { leaderBoardResponse ->
                    leaderBoardMutableLiveData.postValue(Resource.Success(leaderBoardResponse))
                }
            } else {
                leaderBoardMutableLiveData.postValue(Resource.Error("Response Failed"))
            }
        } catch (e: Exception) {
            leaderBoardMutableLiveData.postValue(Resource.Error(e.message.toString()))

        }

    }
}