package github.idmeetrious.githubclient.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.githubclient.data.api.ApiRequests
import github.idmeetrious.githubclient.data.datasource.remote.RemoteDataSource
import github.idmeetrious.githubclient.data.datasource.remote.RemoteDataSourceImpl
import github.idmeetrious.githubclient.data.mappers.DtoToEntityMapper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RemoteModule {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    fun provideApiRequests(): ApiRequests =
        retrofit.create(ApiRequests::class.java)

    @Provides
    fun provideRemoteDataSource(api: ApiRequests): RemoteDataSource =
        RemoteDataSourceImpl(api, DtoToEntityMapper())
}