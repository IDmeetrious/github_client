package github.idmeetrious.githubclient.data.api

import github.idmeetrious.githubclient.data.api.dto.GitRepoDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {
    @GET("/users/{username}/repos")
    fun getReposByUser(
        @Path("username") user: String
    ): Single<List<GitRepoDto>>
}