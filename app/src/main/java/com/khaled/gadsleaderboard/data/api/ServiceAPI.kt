package com.khaled.gadsleaderboard.data.api

import com.khaled.gadsleaderboard.data.models.LeaderBoardResponse
import retrofit2.Response
import retrofit2.http.*

interface ServiceAPI {

    @GET("{filter}")
    suspend fun getLeaderBoard(@Path("filter") filter: String): Response<LeaderBoardResponse>

    @POST
    @FormUrlEncoded
    suspend fun submitProject(
        @Url url: String,
        @Field("entry.1824927963") email: String,
        @Field("entry.1877115667") firstName: String,
        @Field("entry.2006916086") lastName: String,
        @Field("entry.284483984") projectLink: String
    ): Response<Void>

}