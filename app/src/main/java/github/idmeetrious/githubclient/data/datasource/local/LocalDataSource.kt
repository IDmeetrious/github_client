package github.idmeetrious.githubclient.data.datasource.local

import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.entities.GitRepo
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {
    suspend fun saveRepoToDb(repo: GitRepo)
    fun getRepos(): Single<List<GitRepo>>
}