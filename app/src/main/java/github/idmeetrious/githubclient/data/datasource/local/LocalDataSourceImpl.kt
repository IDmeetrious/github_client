package github.idmeetrious.githubclient.data.datasource.local

import github.idmeetrious.githubclient.data.db.SavedReposDao
import github.idmeetrious.githubclient.data.mappers.DbToEntityMapper
import github.idmeetrious.githubclient.domain.entities.GitRepo
import io.reactivex.rxjava3.core.Single

class LocalDataSourceImpl(
    private val dao: SavedReposDao,
    private val mapper: DbToEntityMapper
) : LocalDataSource {
    override suspend fun saveRepoToDb(repo: GitRepo) =
        dao.saveRepo(mapper.mapToDbData(repo))


    override fun getRepos(): Single<List<GitRepo>> {
        val dataRepos = dao.getRepos()
        return dataRepos.map { list ->
            list.map { repoData ->
                mapper.mapToEntity(repoData)
            }
        }
    }
}