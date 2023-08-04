package com.jerry.clean_arch_mvvm.assetpage.di

import com.jerry.clean_arch_mvvm.assetpage.data.repository.AssetsRepositoryImpl
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AssetAbstractModule {

    @Binds
    @Singleton
    abstract fun bindAssetsRepository(assetsRepositoryImpl: AssetsRepositoryImpl): AssetsRepository

}