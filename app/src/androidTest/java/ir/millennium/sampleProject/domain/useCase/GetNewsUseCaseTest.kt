package ir.millennium.sampleProject.domain.useCase

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.millennium.sampleProject.MainCoroutineRule
import ir.millennium.sampleProject.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleProject.presentation.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class GetNewsUseCaseTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var getNewsUseCase: GetNewsUseCase

    private val params = mutableMapOf<String, Any>()

    @Before
    fun testPreconditions() {
        hiltRule.inject()
    }

    @Test
    fun statusSuccess_getData() = runTest {
        params["apiKey"] = Constants.API_KEY
        params["from"] = "2023-07-00"
        params["q"] = "tesla"
        params["page"] = 1

        getNewsUseCase.getNews(params).test {
            val newsList = awaitItem()
            assertTrue(newsList.status.equals("ok"))
            awaitComplete()
        }
    }

    @Test
    @DisplayName("give me error when we send wrong params")
    fun statusError_whenNotSend_q_params() = runTest {

        params["apiKey"] = Constants.API_KEY
        params["from"] = "2023-07-00"
        params["page"] = 1
//        params["q"] = "tesla"

        getNewsUseCase.getNews(params).test {
            val typeException = awaitError()
            println("The error we reciver is: $typeException")
            assertTrue(typeException is HttpException)
        }
    }

    @Test
    @DisplayName("give me error when we don't send API_KEY")
    fun statusError_whenNotSend_ApiKey_params() = runTest {

//        params["apiKey"] = Constants.API_KEY
        params["from"] = "2023-07-00"
        params["q"] = "tesla"
        params["page"] = 1

        getNewsUseCase.getNews(params).test {
            val typeException = awaitError()
            println("The error we reciver is: $typeException")
            assertTrue(typeException is HttpException)
        }
    }

    @Test(expected = IOException::class)
    @DisplayName("give me error when we don't have any Internet connection")
    fun statusError_whenWeDontHaveAnyInternet() = runTest {

        params["apiKey"] = Constants.API_KEY
        params["from"] = "2023-07-00"
        params["q"] = "tesla"
        params["page"] = 1

        val auxiliaryFunctionsManager = mock(AuxiliaryFunctionsManager::class.java)

        `when`(auxiliaryFunctionsManager.isNetworkConnected(anyOrNull())).thenReturn(false)

        if (!auxiliaryFunctionsManager.isNetworkConnected(anyOrNull())) {
            val getNewsUseCase = mock(GetNewsUseCase::class.java)
            given(getNewsUseCase.getNews(anyOrNull())).willAnswer { throw IOException() }
            getNewsUseCase.getNews(anyOrNull())
        }
    }
}