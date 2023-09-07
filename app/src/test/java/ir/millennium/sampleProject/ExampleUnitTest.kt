package ir.millennium.sampleProject

import ir.millennium.sampleProject.presentation.fragments.SplashFragment
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    companion object {

        lateinit var splashFragment: SplashFragment

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            splashFragment = SplashFragment()
        }
    }

    @Test
    @RepeatedTest(10)
    fun firstTest() {
        assertTrue(true)
    }
}