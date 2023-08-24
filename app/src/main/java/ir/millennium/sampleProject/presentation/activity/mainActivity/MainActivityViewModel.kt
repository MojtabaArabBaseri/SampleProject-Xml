package ir.millennium.sampleProject.presentation.activity.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainActivityViewModel(state: SavedStateHandle) : ViewModel() {
    private val SOME_DATA = "someData"
    private val someData: MutableLiveData<String>

    init {
        someData = state.getLiveData(SOME_DATA)
    }

    fun setShowingFileNowList(item: String) {
        someData.value = item
    }

    fun getShowingFileNowList(): LiveData<String> {
        return someData
    }
}