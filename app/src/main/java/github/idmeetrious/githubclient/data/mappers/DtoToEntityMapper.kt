package github.idmeetrious.githubclient.data.mappers

import github.idmeetrious.githubclient.data.api.dto.GitRepoDto
import github.idmeetrious.githubclient.domain.entities.GitOwner
import github.idmeetrious.githubclient.domain.entities.GitRepo

class DtoToEntityMapper {
    fun mapToEntity(repoDto: GitRepoDto): GitRepo {
        repoDto.let {
            return GitRepo(
                id = it.id,
                name = it.name,
                url = it.url,
                owner = GitOwner(
                    it.owner.id,
                    it.owner.login,
                    it.owner.logo
                )
            )
        }
    }
}