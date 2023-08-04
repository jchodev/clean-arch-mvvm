package com.jerry.clean_arch_mvvm.marketpage.di

import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import com.jerry.clean_arch_mvvm.marketpage.data.mapper.MarketMapper
import com.jerry.clean_arch_mvvm.marketpage.network.MarketServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MarketModule {

    @Provides
    fun provideMarketMapper(displayUtil: DisplayUtil): MarketMapper {
        return MarketMapper(displayUtil)
    }

    @Provides
    @Singleton
    fun provideMarketServiceApi(retrofit: Retrofit): MarketServiceApi {
        return retrofit.create(MarketServiceApi::class.java)
    }
}
