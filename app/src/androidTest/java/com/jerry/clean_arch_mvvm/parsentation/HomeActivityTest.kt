package com.jerry.clean_arch_mvvm.parsentation


import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.google.gson.Gson
import com.jerry.clean_arch_mvvm.HomeActivity
import com.jerry.clean_arch_mvvm.sharedtest.*
import com.jerry.clean_arch_mvvm.units.recyclerItemAtPosition

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matcher

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import java.net.HttpURLConnection


@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var mockWebServer: MockWebServer

    private val HTTP_ERROR = "Something went wrong"
    private val RECORD_NOT_FOUND = "Record not found"

    @Rule
    @JvmField
    var homeActivityResult = ActivityTestRule(HomeActivity::class.java, true, false)

    /*
        Step for this test:
        a. Asset page
           1. HTTP error
           2. server provided error
           3. success
        b. Market page
           1. HTTP error
           2. server provided error
           2. Record not found error
           3. success
     */
    @Test
    fun test_all_case() {

        //launch activity
        homeActivityResult.launchActivity(null)

        //assets - http error
        mockNetworkResponse("assets-customer-error", HttpURLConnection.HTTP_NOT_FOUND)
        SystemClock.sleep(1000)

        //check with assets - http error
        performUserSteps(
            checkData = {
                //check http error message
                Espresso.onView(ViewMatchers.withId(android.R.id.message)).check(
                    ViewAssertions.matches(ViewMatchers.withSubstring(HTTP_ERROR))
                )
            },
            fireAction = {
                Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
                SystemClock.sleep(1000)
            },
            assignNextStepNetworkData = Pair("assets-customer-error", HttpURLConnection.HTTP_OK)
        )

        //check with assets server provided error
        performUserSteps(
            checkData = {
                //check http error message
                Espresso.onView(ViewMatchers.withId(android.R.id.message)).check(
                    ViewAssertions.matches(ViewMatchers.withSubstring(AssetsTestStubs.errorMessage))
                )
            },
            fireAction = {
                Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
                SystemClock.sleep(1000)
            },
            assignNextStepNetworkData = Pair("assets", HttpURLConnection.HTTP_OK)
        )

        //check with assets success
        performUserSteps(
            checkData = {
                Espresso.onView(withId(com.jerry.clean_arch_mvvm.assetpage.R.id.recyclerViewAssets))
                    .check(
                        ViewAssertions.matches(
                            recyclerItemAtPosition(
                                0,
                                ViewMatchers.hasDescendant(ViewMatchers.withText(
                                    AssetsTestStubs.testAssetsResponseData.assetData!![0].symbol)
                                )
                            )
                        )
                    )
            },
            fireAction = {
                //Check on 1 item
                clickOnViewAtRow(1)

                //goto market page and waiting loading
                SystemClock.sleep(1000)
            },
            assignNextStepNetworkData = Pair("market-customer-error", HttpURLConnection.HTTP_NOT_FOUND)
        )

        //check with market http error
        performUserSteps(
            checkData = {
                //check http error message
                Espresso.onView(ViewMatchers.withId(android.R.id.message)).check(
                    ViewAssertions.matches(ViewMatchers.withSubstring(HTTP_ERROR))
                )
            },
            fireAction = {
                Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
                SystemClock.sleep(1000)
            },
            assignNextStepNetworkData = Pair("market-customer-error", HttpURLConnection.HTTP_OK)
        )

        //check with market customer error
        performUserSteps(
            checkData = {
                //check http error message
                Espresso.onView(ViewMatchers.withId(android.R.id.message)).check(
                    ViewAssertions.matches(ViewMatchers.withSubstring(
                        MarketTestStubs.errorMessage))
                )
            },
            fireAction = {
                Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
                SystemClock.sleep(1000)
            },
            assignNextStepNetworkData = Pair("market-record-not-found", HttpURLConnection.HTTP_OK)
        )

        //check with market record not found error
        performUserSteps(
            checkData = {
                //check http error message
                Espresso.onView(ViewMatchers.withId(android.R.id.message)).check(
                    ViewAssertions.matches(ViewMatchers.withSubstring(RECORD_NOT_FOUND))
                )
            },
            fireAction = {
                Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
                SystemClock.sleep(1000)
            },
            assignNextStepNetworkData = Pair("market", HttpURLConnection.HTTP_OK)
        )

        //Check market success case
        performUserSteps(
            checkData = {
                Espresso.onView(withId(com.jerry.clean_arch_mvvm.marketpage.R.id.textViewExchangeId)).check(
                    ViewAssertions.matches(
                        ViewMatchers.withText(
                            "exchange Id 2"
                        )
                    )
                )
            },
            fireAction = {
                SystemClock.sleep(2000)
            },
            assignNextStepNetworkData = null
        )
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    private fun mockNetworkResponse(path: String, responseCode: Int) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(path))
        )
    }


    private fun getJson(path: String) : String {
        return when (path) {
            "assets" -> {
                Gson().toJson( AssetsTestStubs.testAssetsResponseData)
            }
            "assets-customer-error" -> {
                Gson().toJson(AssetsTestStubs.testAssetsResponseData.copy(
                    error = AssetsTestStubs.errorMessage
                ))
            }
            "market" -> {
                Gson().toJson(MarketTestStubs.testMarketResponseData)
            }
            "market-customer-error" -> {
                Gson().toJson(MarketTestStubs.testMarketResponseData.copy(
                    marketData = null,
                    error = MarketTestStubs.errorMessage
                ))
            }
            "market-record-not-found" -> {
                Gson().toJson(MarketTestStubs.testMarketResponseData.copy(
                    marketData = emptyList()
                ))
            }
            else -> {
                ""
            }
        }
    }


    private fun performUserSteps(
        checkData: ()-> Unit = {},
        fireAction : () -> Unit = {},
        assignNextStepNetworkData: Pair<String, Int>? = null,
    ) {
        //check the UI information is correct or not
        checkData()

        //assign next http call data
        assignNextStepNetworkData?.let {
            mockNetworkResponse(it.first, it.second)
            SystemClock.sleep(1000)
        }

        //usually 'click on "retry" button' / click on list view
        fireAction();
    }


    private fun clickOnViewAtRow(position: Int) {
        Espresso.onView(ViewMatchers.withId(com.jerry.clean_arch_mvvm.assetpage.R.id.recyclerViewAssets)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
                (position, ClickOnButtonView()))
    }

    private class ClickOnButtonView : ViewAction {
        internal var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            //asset_item.xml card
            click.perform(uiController, view.findViewById((com.jerry.clean_arch_mvvm.assetpage.R.id.cardViewAsset)))
        }
    }
}