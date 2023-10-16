package com.hb.base

import android.app.Application
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 *A base class for Android applications that use the Apollo client to make GraphQL requests to the PokéAPI.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
open class BaseApp : Application() {

    // The base URL for the Apollo client.
    private val BASE_URL = "https://beta.pokeapi.co/graphql/v1beta/"
    lateinit var apolloClient: ApolloClient
    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        apolloClient = ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}