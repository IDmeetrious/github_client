package github.idmeetrious.githubclient.data.mappers

import github.idmeetrious.githubclient.data.db.entities.GitRepoData
import github.idmeetrious.githubclient.data.db.entities.GitRepoOwnerData
import github.idmeetrious.githubclient.domain.entities.GitOwner
import github.idmeetrious.githubclient.domain.entities.GitRepo

class DbToEntityMapper {
    fun mapToDbData(repo: GitRepo): GitRepoData{
        repo.let {
            return GitRepoData(
                id = it.id,
                name = it.name,
                owner = GitRepoOwnerData(
                    it.owner.id,
                    it.owner.login,
                    it.owner.logo
                )
            )
        }
    }

    fun mapToEntity(repo: GitRepoData): GitRepo{
        repo.let {
            return GitRepo(
                id = it.id,
                name = it.name,
                owner = GitOwner(
                    it.owner.id,
                    it.owner.login,
                    it.owner.logo
                )
            )
        }
    }
}