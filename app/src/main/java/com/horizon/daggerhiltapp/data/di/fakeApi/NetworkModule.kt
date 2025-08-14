package com.horizon.daggerhiltapp.data.di.fakeApi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.horizon.daggerhiltapp.domain.network.FakeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Module es un módulo Hilt que le dice a Dagger - Hilt cómo crear y proveer instancias de Retorfit, Gson y OkHTTP
//InstallIn(SingletonComponent::class) -> Todas las dependencias de este moulo vivirán durante toda la vida de la app (singleton)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    //Provee un objeto Gson, usado por Retrofit para convertir Json en objetos koltin y viceversa
    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().create()

    //Crea un interceptor que loggea las peticiones y respuestas HTTP(muy útil para debugging).
    //Body muestra el contenido completo (cabeceras + cuerpo)
    @Provides
    @Singleton
    fun providesLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            // Nivel de detalle del log (BODY = incluye cabeceras y cuerpo)
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //Crea el cliente OkHTTP, añadiendo el interceptor para las peticiones y respuestas en Logcat
    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    //Crea el objeto Retrofit configurado con:
    // - BASE_URL: Dirección base de la API
    //- okHttpClient: Cliente HTTP con interceptor
    //GsonConverterFactory: Convierte Json a objetos kotlin
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    //A partir de REtrofit, crea una implementación real de FakeApiService para poder hacer las llamadas a la API.
    @Provides
    @Singleton
    fun providesFakeApi(retrofit: Retrofit) : FakeApiService {
        return retrofit.create(FakeApiService::class.java)
    }

}