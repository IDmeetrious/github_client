package github.idmeetrious.githubclient.data.api.dto

import com.google.gson.annotations.SerializedName

data class GitRepoDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("owner")
    val owner: GitRepoOwnerDto
)
