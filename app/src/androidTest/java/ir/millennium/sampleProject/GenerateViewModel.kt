package ir.millennium.sampleProject

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import kotlin.reflect.KClass

val mockedViewModels = HashMap<Class<*>, ViewModel>()

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    // the production producer
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return createMockedViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
}
/// ... and similar for the fragment-ktx delegates

/**
 * Wraps the default factoryPromise with one that looks in the mockedViewModels map
 */
fun <VM : ViewModel> createMockedViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryPromise: () -> ViewModelProvider.Factory
): Lazy<VM> {
    // the mock producer
    val mockedFactoryPromise: () -> ViewModelProvider.Factory = {
        // if there are any mocked ViewModels, return a Factory that fetches them
        if (mockedViewModels.isNotEmpty()) {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return mockedViewModels[modelClass] as T
                        ?: factoryPromise().create(modelClass)  // return the normal one if no mock found
                }
            }
        } else {
            // if no mocks, call the normal factoryPromise directly
            factoryPromise()
        }
    }

    return ViewModelLazy(viewModelClass, storeProducer, mockedFactoryPromise)
}