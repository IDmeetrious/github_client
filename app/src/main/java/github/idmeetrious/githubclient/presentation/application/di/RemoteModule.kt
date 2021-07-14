package github.idmeetrious.githubclient.presentation.application.di

import dagger.Module
import dagger.Provides
import github.idmeetrious.githubclient.data.api.ApiRequests
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {
    @[Singleton Provides]
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    fun provideApiRequests(retrofit: Retrofit): ApiRequests =
        retrofit.create(ApiRequests::class.java)
}