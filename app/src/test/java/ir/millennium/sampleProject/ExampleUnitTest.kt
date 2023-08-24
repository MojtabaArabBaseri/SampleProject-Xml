package ir.millennium.sampleProject

import ir.millennium.sampleProject.ui.fragments.SplashFragment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

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
    fun firstTest() {
        assertTrue(true)
    }

    @DisplayName("This is Sum Method")
    @RepeatedTest(10)
    fun sumTest() {
        val number1 = (1..10).random()
        val number2 = (1..10).random()
        val expected = number1 + number2
        val actual = splashFragment.sum(number1, number2)
        assertEquals(expected, actual)
    }

    @Test
    fun lazyBehaviuorCheck() {
        val myName: String by lazy { "Mojtaba Arab Baseri" }
        assertEquals("Mojtaba Arab Baseri", myName)
    }

    private var nameNull: String? = null
    private var nameNotNull: String by Delegates.notNull()
    private val myNationalityType1: String by lazy { "09159176670" }
    private val myNationalityType2 = lazy { "09159176670" }

    @Test
    fun delegationBehaviuorCheck() {

        var nameWithInitialize: String by Delegates.observable("MojtabaArabBaseri") { prop, old, new ->
            println("$old -> $new")
        }

        nameWithInitialize = "Kiana Hoshangi"
        nameWithInitialize = "Azar Ghamangiz"
        nameWithInitialize = "Mariiii"

        assertEquals("Mariiii", nameWithInitialize)
    }
}