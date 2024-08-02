package com.ffandroidproj4.androiddevp4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ffandroidproj4.androiddevp4.model.Dog
import com.ffandroidproj4.androiddevp4.repository.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DogViewModel(application: Application) : AndroidViewModel(application)
{
    private val dogRepository = DogRepository(application)
    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> get() = _dogs
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchDogs()
    }

    private fun fetchDogs() {
        viewModelScope.launch {
            try {
                // Fetch students using the repository
                val result = withContext(Dispatchers.IO) {
                    val dogsList = mutableListOf<Dog>()
                    var fetchError: Throwable? = null

                    dogRepository.getDogs(
                        onResult = { dogs ->
                            dogsList.addAll(dogs ?: emptyList())
                        },
                        onError = { error ->
                            fetchError = error
                        }
                    )

                    if (fetchError != null) {
                        throw fetchError!!
                    }
                    dogsList
                }
                _dogs.postValue(result)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }


}