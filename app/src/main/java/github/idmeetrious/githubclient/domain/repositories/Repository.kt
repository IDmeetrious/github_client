package github.idmeetrious.githubclient.domain.repositories

import github.idmeetrious.githubclient.domain.entities.GitRepo
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getUserRepositories(user: String): Single<List<GitRepo>>
    fun getSavedRepositories(): Single<List<GitRepo>>
    fun downloadRepositoryZip(uri: String): Single<ByteArray>
    suspend fun saveRepositoryToDb(gitRepo: GitRepo)
}