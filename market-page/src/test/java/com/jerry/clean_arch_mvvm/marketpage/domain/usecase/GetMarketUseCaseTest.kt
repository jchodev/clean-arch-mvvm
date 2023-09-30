package com.jerry.clean_arch_mvvm.marketpage.domain.usecase

import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs.Companion.errorMessage
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs.Companion.testMarketResponseData
import com.jerry.clean_arch_mvvm.marketpage.data.mapper.MarketMapper
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.repository.MarketRepository
import com.jerry.clean_arch_mvvm.marketpage.exception.MarketNotFoundException
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class GetMarketUseCaseTest {

    private lateinit var repository: MarketRepository
    private lateinit var usecase: GetMarketUseCase
    private lateinit var mapper: MarketMapper
    private lateinit var displayUtil: DisplayUtil

    @BeforeEach
    fun setUp() {
        repository =  mockkClass(MarketRepository::class)
        displayUtil = DisplayUtil()
        mapper = MarketMapper(displayUtil = displayUtil)
        usecase = GetMarketUseCase(repository, mapper)
    }

    @Test
    fun `test GetMarketUseCase invoke() return success state`() = runTest {
        val expected = mapper.mapToUiData(testMarketResponseData.marketData!![1])

        //assign
        coEvery { repository.getMarket(any()) } returns testMarketResponseData

        //action
        val actual = usecase.invoke("")

        //verify
        Assertions.assertTrue(actual is UseCaseResult.Success)
        Assertions.assertEquals(
            (actual as UseCaseResult.Success<MarketUiItem>).data,
            expected
        )
    }

    @Test
    fun `test GetMarketUseCase invoke() return customer error state`() = runTest {

        val result = testMarketResponseData.copy(
            error = errorMessage
        )

        //assign
        coEvery { repository.getMarket(any()) } returns result

        //action
        val actual = usecase.invoke("")

        //verify
        Assertions.assertTrue(actual is UseCaseResult.CustomerError)
        Assertions.assertEquals(
            result.error,
            (actual as UseCaseResult.CustomerError).error
        )
    }

    @Test
    fun `test GetMarketUseCase invoke() return Failure status`() = runTest {
        //assign
        coEvery { repository.getMarket(any()) }.throws(SocketTimeoutException())

        //action
        val actual = usecase.invoke("")

        //verify
        Assertions.assertTrue(actual is UseCaseResult.Failure)
        Assertions.assertTrue(
            (actual as UseCaseResult.Failure).throwable is SocketTimeoutException
        )
    }

    @Test
    fun `test GetMarketUseCase invoke() return Failure with MarketNotFoundException`() = runTest {
        val result = testMarketResponseData.copy(
            marketData = null
        )


        //assign
        coEvery { repository.getMarket(any()) } returns result

        //action
        val actual = usecase.invoke("")

        //verify
        Assertions.assertTrue(actual is UseCaseResult.Failure)
        Assertions.assertTrue(
            (actual as UseCaseResult.Failure).throwable is MarketNotFoundException
        )
    }
}