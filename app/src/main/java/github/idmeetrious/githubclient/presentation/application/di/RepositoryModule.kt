package github.idmeetrious.githubclient.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.githubclient.data.datasource.local.LocalDataSource
import github.idmeetrious.githubclient.data.datasource.remote.RemoteDataSource
import github.idmeetrious.githubclient.data.repositories.RepositoryImpl
import github.idmeetrious.githubclient.domain.repositories.Repository

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository = RepositoryImpl(remoteDataSource, localDataSource)
}