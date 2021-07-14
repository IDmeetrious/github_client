package github.idmeetrious.githubclient.presentation.application

import android.app.Application
import github.idmeetrious.githubclient.presentation.application.di.AppModule
import github.idmeetrious.githubclient.presentation.application.di.DbModule
import github.idmeetrious.githubclient.presentation.application.di.component.AppComponent
import github.idmeetrious.githubclient.presentation.application.di.component.DaggerAppComponent
import javax.inject.Singleton

@Singleton
class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .dbModule(DbModule(applicationContext))
            .build()
    }
}