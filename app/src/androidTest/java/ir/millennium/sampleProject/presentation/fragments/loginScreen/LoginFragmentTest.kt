package ir.millennium.sampleProject.presentation.fragments.loginScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.millennium.sampleProject.launchFragmentInHiltContainer
import ir.millennium.sampleProject.presentation.utils.Constants.PASSWORD
import ir.millennium.sampleProject.presentation.utils.Constants.USER_NAME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    @DisplayName("empty user name")
    fun emptyUserName() {
        val userName = ""
        val password = "123456"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    @DisplayName("empty password")
    fun emptyPassword() {
        val userName = "mojtaba"
        val password = ""

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    @DisplayName("empty username and password")
    fun emptyUserNameAndPassword() {
        val userName = ""
        val password = "123456"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertFalse(actual)
        }

    }

    @Test
    @DisplayName("correct type username and password")
    fun correctTypeUsernameAndPassword() {
        val userName = "username"
        val password = "123456"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertTrue(actual)
        }
    }

    @Test
    @DisplayName("correct username and password")
    fun correctUsernameAndPassword() {
        val userName = USER_NAME
        val password = PASSWORD

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkAuthentication(userName, password)
            assertTrue(actual)
        }
    }

    @Test
    @DisplayName("inCorrect username and password")
    fun inCorrectUsernameAndPassword() {
        val userName = "ali"
        val password = "147852"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkAuthentication(userName, password)
            assertFalse(actual)
        }
    }
}