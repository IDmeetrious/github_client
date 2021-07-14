package github.idmeetrious.githubclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import github.idmeetrious.githubclient.data.db.entities.GitRepoData
import io.reactivex.rxjava3.core.Single

@Dao
interface SavedReposDao : BaseDao<GitRepoData> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepo(repo: GitRepoData)

    @Query("SELECT * FROM repos")
    fun getRepos(): Single<List<GitRepoData>>
}