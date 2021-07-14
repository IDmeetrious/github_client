package github.idmeetrious.githubclient.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.githubclient.domain.repositories.Repository
import github.idmeetrious.githubclient.domain.usecase.*

@Module
class UserCaseModule {
    @Provides
    fun provideGetUserRepositoriesUseCase(repository: Repository): GetUserRepositoriesUseCase =
        GetUserRepositoriesUseCaseImpl(repository)

    @Provides
    fun provideGetSavedRepositoriesUseCase(repository: Repository): GetSavedRepositoriesUseCase =
        GetSavedRepositoriesUseCaseImpl(repository)

    @Provides
    fun provideDownloadRepositoryUseCase(repository: Repository): SaveRepositoryToFileUseCase =
        SaveRepositoryToFileUseCaseImpl(repository)
}