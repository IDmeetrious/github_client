package github.idmeetrious.githubclient.data.repositories

import github.idmeetrious.githubclient.data.datasource.local.LocalDataSource
import github.idmeetrious.githubclient.data.datasource.remote.RemoteDataSource
import github.idmeetrious.githubclient.domain.entities.GitRepo
import github.idmeetrious.githubclient.domain.repositories.Repository
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): Repository {
    override fun getUserRepositories(user: String): Single<List<GitRepo>> =
        remoteDataSource.getRepositories(user)

    override fun getSavedRepositories(): Single<List<GitRepo>> =
        localDataSource.getRepos()
}