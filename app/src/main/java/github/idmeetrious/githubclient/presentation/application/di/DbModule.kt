package github.idmeetrious.githubclient.presentation.application.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import github.idmeetrious.githubclient.data.datasource.local.LocalDataSource
import github.idmeetrious.githubclient.data.datasource.local.LocalDataSourceImpl
import github.idmeetrious.githubclient.data.db.SavedReposDao
import github.idmeetrious.githubclient.data.db.SavedReposDatabase
import github.idmeetrious.githubclient.data.mappers.DbToEntityMapper
import javax.inject.Singleton

@Module
class DbModule(private val context: Context) {

    @[Singleton Provides]
    fun provideSavedReposDatabase() = Room.databaseBuilder(
        context, SavedReposDatabase::class.java, "saved_repositories_database"
    ).build()

    @[Singleton Provides]
    fun provideSavedReposDao(db: SavedReposDatabase) = db.dao()

    @[Provides]
    fun provideLocalDataSource(dao: SavedReposDao): LocalDataSource =
        LocalDataSourceImpl(dao, DbToEntityMapper())
}