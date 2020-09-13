package com.khaled.gadsleaderboard.data.repository

import com.khaled.gadsleaderboard.data.api.ServiceBuilder



class SubmissionRepository {
    suspend fun submitProject(
        url: String,
        email: String,
        firstName: String,
        lastName: String,
        projectLink: String
    ) = ServiceBuilder.api.submitProject(
        url,
        email,
        firstName,
        lastName,
        projectLink
    )
}