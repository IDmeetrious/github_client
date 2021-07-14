package github.idmeetrious.githubclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import github.idmeetrious.githubclient.data.db.entities.GitRepoData
import github.idmeetrious.githubclient.domain.entities.GitRepo

@Database(entities = [GitRepoData::class], version = 1, exportSchema = false)
abstract class SavedReposDatabase: RoomDatabase() {
    abstract fun dao(): SavedReposDao
}