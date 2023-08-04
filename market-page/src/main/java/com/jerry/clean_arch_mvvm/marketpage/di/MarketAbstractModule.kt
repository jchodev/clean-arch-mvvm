package com.jerry.clean_arch_mvvm.marketpage.di

import com.jerry.clean_arch_mvvm.marketpage.data.repository.MarketRepositoryImpl
import com.jerry.clean_arch_mvvm.marketpage.domain.repository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MarketAbstractModule {

    @Binds
    @Singleton
    abstract fun bindMarketRepository(marketRepositoryImpl: MarketRepositoryImpl): MarketRepository

}