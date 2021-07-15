package github.idmeetrious.githubclient.presentation.application.di.component

import dagger.Component
import github.idmeetrious.githubclient.presentation.application.di.AppModule
import github.idmeetrious.githubclient.presentation.ui.search.SearchViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(searchViewModel: SearchViewModel)
}