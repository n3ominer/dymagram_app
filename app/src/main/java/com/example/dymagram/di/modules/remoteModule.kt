package com.example.dymagram.di.modules

import com.example.dymagram.data.model.following.Following
import com.example.dymagram.di.ApplicationConfiguration
import com.example.dymagram.network.services.FeedPostService
import com.example.dymagram.network.services.FeedStoriesService
import com.example.dymagram.network.services.FollowingService
import com.example.dymagram.network.services.GlobalDataService
import com.example.dymagram.network.services.MessagesService
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal var remoteModule = module {

    single { createOkHttpClient() } // Singleton

    single(named(fakeApiServerRetrofitClient)) { createRetrofitClient(get(), get<ApplicationConfiguration>().baseUrl) }


    // Services
    single { get<Retrofit>(named(fakeApiServerRetrofitClient)).create(GlobalDataService::class.java) }
    single { get<Retrofit>(named(fakeApiServerRetrofitClient)).create(FeedPostService::class.java) }
    single { get<Retrofit>(named(fakeApiServerRetrofitClient)).create(FeedStoriesService::class.java) }
    single { get<Retrofit>(named(fakeApiServerRetrofitClient)).create(Following::class.java) }
    single { get<Retrofit>(named(fakeApiServerRetrofitClient)).create(MessagesService::class.java) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        //.interceptors()
        .build()
}

fun createRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return  Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

        .build()
}

const val fakeApiServerRetrofitClient = "fakeApiServerRetrofitClient"