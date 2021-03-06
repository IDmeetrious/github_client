package github.idmeetrious.githubclient.presentation.application.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        DbModule::class,
        RemoteModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
class AppModule(private val context: Context) {

    @[Singleton Provides]
    fun provideApplicationContext() = context
}