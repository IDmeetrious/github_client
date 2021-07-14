package github.idmeetrious.githubclient.domain.usecase

import io.reactivex.rxjava3.core.Single

interface DownloadRepositoryUseCase {
    fun invoke(uri: String): Single<ByteArray>
}