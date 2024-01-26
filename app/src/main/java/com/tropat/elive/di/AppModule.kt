package com.tropat.elive.di

import com.tropat.elive.network.EliveApi
import com.tropat.elive.repository.EliveBooksRepository
import com.tropat.elive.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookRepository(api: EliveApi) = EliveBooksRepository(api)
    @Provides
    @Singleton
    fun provideBooksApi(): EliveApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EliveApi::class.java)
    }
}