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
    fun validateFields_emptyUsername_expectedFalse() {
        val userName = ""
        val password = "123456"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    fun validateFields_emptyPassword_expectedFalse() {
        val userName = "mojtaba"
        val password = ""

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    fun validateFields_emptyUsernameAndPassword_expectedFalse() {
        val userName = ""
        val password = ""

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    fun validateFields_correctTypeUsernameAndPassword_expectedTrue() {
        val userName = "username"
        val password = "123456"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkFieldForValidation(userName, password)
            assertTrue(actual)
        }
    }

    @Test
    fun validation_incorrectUserame_expectedFalse() {
        val userName = "ali"
        val password = PASSWORD

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkAuthentication(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    fun validation_incorrectPassword_expectedFalse() {
        val userName = USER_NAME
        val password = "9874521"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkAuthentication(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    fun validation_incorrectBoth_expectedFalse() {
        val userName = "ali"
        val password = "147852"

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkAuthentication(userName, password)
            assertFalse(actual)
        }
    }

    @Test
    fun validation_correctBoth_expectedTrue() {
        val userName = USER_NAME
        val password = PASSWORD

        launchFragmentInHiltContainer<LoginFragment> {
            val actual = checkAuthentication(userName, password)
            assertTrue(actual)
        }
    }
}