package github.idmeetrious.githubclient.domain.entities

data class GitRepo(
    val id: Long,
    val name: String,
    val url: String = "",
    val owner: GitOwner
)
