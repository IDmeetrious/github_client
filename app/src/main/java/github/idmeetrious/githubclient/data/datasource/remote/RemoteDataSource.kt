package github.idmeetrious.githubclient.data.datasource.remote

import github.idmeetrious.githubclient.domain.entities.GitRepo
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {
    fun getRepositories(name: String): Single<List<GitRepo>>
}