package github.idmeetrious.githubclient.presentation.application.di.component

import dagger.Component
import github.idmeetrious.githubclient.presentation.application.di.AppModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

}