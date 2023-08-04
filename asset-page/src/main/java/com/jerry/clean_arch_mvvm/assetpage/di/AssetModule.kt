package com.jerry.clean_arch_mvvm.assetpage.di

import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.network.AssetServiceApi
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
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
    fun provideAssetMapper(displayUtil: DisplayUtil): AssetMapper {
        return AssetMapper(displayUtil)
    }

    @Provides
    @Singleton
    fun provideAssetServiceApi(retrofit: Retrofit): AssetServiceApi {
        return retrofit.create(AssetServiceApi::class.java)
    }

}