package github.idmeetrious.githubclient.data.datasource.remote

import github.idmeetrious.githubclient.data.api.ApiRequests
import github.idmeetrious.githubclient.data.mappers.DtoToEntityMapper
import github.idmeetrious.githubclient.domain.entities.GitRepo
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(
    private val api: ApiRequests,
    private val mapper: DtoToEntityMapper
) : RemoteDataSource {
    override fun getRepositories(name: String): Single<List<GitRepo>> {
        val apiRepos = api.getReposByUser(name)
        return apiRepos.map { dtoList ->
            dtoList.map { dto -> mapper.mapToEntity(dto) }
        }
    }

    override fun downloadRepository(uri: String): Single<ByteArray> {
        return api.downloadRepoZip(uri)
    }
}