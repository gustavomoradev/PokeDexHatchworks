package com.moradev.pokedexhatchworks.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val url = "https://pokeapi.co/"
    val TIMEOUT = 30L

    @Singleton
    @Provides
    fun providesPokeApi(okHttpClient: OkHttpClient, gson: Gson):PokeApi{


        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        logginInterceptor:HttpLoggingInterceptor
    ):OkHttpClient{


        val builder:OkHttpClient.Builder = OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
//            .certificatePinner()
            .addInterceptor(logginInterceptor)



        return builder.build()
    }

    @Singleton
    @Provides
    fun provideLogginInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideGson():Gson{
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }


}