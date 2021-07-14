package github.idmeetrious.githubclient.data.api.dto

import com.google.gson.annotations.SerializedName

data class GitRepoOwnerDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val logo: String
)
