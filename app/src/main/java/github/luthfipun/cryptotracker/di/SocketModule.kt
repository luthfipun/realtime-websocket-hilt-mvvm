package github.luthfipun.cryptotracker.di

import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.luthfipun.cryptotracker.network.SocketService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SocketModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson().newBuilder()
            .serializeNulls()
            .create()
    }

    @Singleton
    @Provides
    fun provideScarlet(gson: Gson, okHttpClient: OkHttpClient): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory("wss://realtime.aax.com/marketdata/v2/BTCUSDT@trade"))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideSocketService(scarlet: Scarlet): SocketService {
        return scarlet.create(SocketService::class.java)
    }
}