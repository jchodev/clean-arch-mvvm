package com.jerry.clean_arch_mvvm.assetpage.di

import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.network.AssetServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AssetModule {

    @Provides
    fun provideAssetMapper(): AssetMapper {
        return AssetMapper()
    }

    @Provides
    @Singleton
    fun providesProductApi(retrofit: Retrofit): AssetServiceApi {
        return retrofit.create(AssetServiceApi::class.java)
    }

}