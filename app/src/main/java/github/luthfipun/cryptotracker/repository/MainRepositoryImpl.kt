package github.luthfipun.cryptotracker.repository

import github.luthfipun.cryptotracker.network.SocketService
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val socketService: SocketService
) : MainRepository {

}