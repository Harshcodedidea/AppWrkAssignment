package com.appwrkassignment.presentation.viewmodels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.appwrkassignment.base.BaseViewModel
import com.appwrkassignment.data.DataItemModel
import com.appwrkassignment.managers.UserProfilePrefs
import kotlinx.coroutines.launch


class HomeViewModel(val userProfilePrefs: UserProfilePrefs) : BaseViewModel() {


    val itemList: MutableLiveData<MutableList<DataItemModel>?> = MutableLiveData()


    init {
        viewModelScope.launch {
            if (userProfilePrefs.getItemDate()?.isNotEmpty() == true) {
                itemList.value = userProfilePrefs.getItemDate()
            } else {
                itemList.value = createDummyData()
            }
        }
    }

    suspend fun createDummyData(): MutableList<DataItemModel> {
        val list = mutableListOf<DataItemModel>()

        repeat(10) { index ->
            val randomData = getRandomWordWithDescription()
            list.add(DataItemModel(title = randomData.first, description = randomData.second))
        }
        userProfilePrefs.saveItemData(list)
        return list
    }

    fun getRandomWordWithDescription(): Pair<String, String> {

        val words = listOf(
            "Arcane", "Solstice", "Whisper", "Maven", "Flux", "Meadow",
            "Zephyr", "Ember", "Drift", "Prism", "Nimbus", "Pulse",
            "Vortex", "Halo", "Echo", "Rune", "Orbit", "Nova"
        )

        val descriptions = listOf(
            "A simple and clean design.",
            "Lightweight and easy to use.",
            "Optimized for fast performance.",
            "Built with modern technology.",
            "Reliable and efficient solution.",
            "Perfect for daily use.",
            "Compact yet powerful.",
            "Designed for flexibility.",
            "Smooth user experience.",
            "Minimal setup required."
        )

        return Pair(
            words.random(),
            descriptions.random()
        )
    }

    fun updateList(item: DataItemModel): MutableList<DataItemModel> {

        val list = userProfilePrefs.getItemDate() ?: mutableListOf()

        val index = list.indexOfFirst { it.title == item.title } // or any unique key
        if (index != -1) {
            list[index].status = item.status
            userProfilePrefs.saveItemData(list) // save updated list back
        }
        return list
    }

    class HomeViewModelFactory(
        val userProfilePrefs: UserProfilePrefs,
    ) : ViewModelProvider.Factory {

        /**
         * Creates a new instance of the specified ViewModel class.
         * @param modelClass the class of the ViewModel to be created.
         * @return the created ViewModel instance.
         */
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(
                userProfilePrefs,
            ) as T
        }
    }

    fun getFilteredData(filter: String = "All"): MutableList<DataItemModel> {
        return when (filter) {
            "Pending" -> itemList.value?.filter { it.status != true }
            "Completed" -> itemList.value?.filter { it.status == true }
            else -> itemList.value
        }?.toMutableList() ?: mutableListOf()
    }
}