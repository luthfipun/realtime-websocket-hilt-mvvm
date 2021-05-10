package github.luthfipun.cryptotracker.di

import android.app.Application
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.lifecycle.android.BuildConfig
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.ShutdownReason
import com.tinder.scarlet.websocket.okhttp.OkHttpWebSocket
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.luthfipun.cryptotracker.domain.util.FlowStreamAdapter
import github.luthfipun.cryptotracker.network.SocketService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SocketModule {

    private const val TIME_OUT = 60L

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val builder =  OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .followRedirects(false)
            .followSslRedirects(false)

        if (BuildConfig.DEBUG){
            builder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        return builder.build()
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
    fun provideScarlet(app: Application, gson: Gson, okHttpClient: OkHttpClient): Scarlet {
        return Scarlet(
            OkHttpWebSocket(
                okHttpClient,
                OkHttpWebSocket.SimpleRequestFactory(
                    { Request.Builder().url("wss://realtime.aax.com/marketdata/v2/BTCUSDT@trade").build()},
                    { ShutdownReason.GRACEFUL }
                )
            ),
            Scarlet.Configuration(
                backoffStrategy = LinearBackoffStrategy(TIME_OUT),
                messageAdapterFactories = listOf(GsonMessageAdapter.Factory(gson)),
                streamAdapterFactories = listOf(FlowStreamAdapter.Factory),
                lifecycle = AndroidLifecycle.ofApplicationForeground(app)
            )
        )
    }

    @Singleton
    @Provides
    fun provideSocketService(scarlet: Scarlet): SocketService {
        return scarlet.create(SocketService::class.java)
    }
}