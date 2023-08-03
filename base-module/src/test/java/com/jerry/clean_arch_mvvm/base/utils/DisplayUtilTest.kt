package com.jerry.clean_arch_mvvm.base.utils

import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class DisplayUtilTest {

    private lateinit var displayUtil: DisplayUtil

    @BeforeEach
    fun setup() {
        displayUtil = DisplayUtil()
    }

    @Test
    fun `test DisplayUtil displayNormalText function with valid text and return same with input`()  {
        val testStr = ""
        val actual = displayUtil.displayNormalText(testStr)
        Assertions.assertEquals(testStr, actual)
    }

    @Test
    fun `test DisplayUtil displayNormalText function with null text and return "---" as result`() {
        val actual = displayUtil.displayNormalText(null)
        Assertions.assertEquals("---", actual)
    }

    @Test
    fun `test DisplayUtil displayDateFormat function with null and return "---" as result`() {
        val actual = displayUtil.displayDateFormat(null)
        Assertions.assertEquals("---", actual)
    }

    @Test
    fun `test DisplayUtil displayDateFormat function with valid long and return expected as result`() {
        val actual = displayUtil.displayDateFormat(1533581011853)
        Assertions.assertEquals("06-08-2018", actual)
    }
}