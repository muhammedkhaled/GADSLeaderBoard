package com.khaled.gadsleaderboard.data.repository

import com.khaled.gadsleaderboard.data.api.ServiceBuilder

class LeaderBoardRepository {

    suspend fun getLeaderBoard(filter: String) =
        ServiceBuilder.api.getLeaderBoard(filter)
}