package github.idmeetrious.githubclient.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_owner")
data class GitRepoOwnerData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "owner_id")
    val id: Long,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val logo: String
)
