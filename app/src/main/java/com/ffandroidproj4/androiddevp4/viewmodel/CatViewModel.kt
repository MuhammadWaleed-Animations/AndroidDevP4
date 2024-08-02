package com.ffandroidproj4.androiddevp4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ffandroidproj4.androiddevp4.model.CatModel
import com.ffandroidproj4.androiddevp4.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatViewModel (application: Application) : AndroidViewModel(application) {
    private val catRepository = CatRepository(application)
    private val _cats = MutableLiveData<List<CatModel>>()
    val cats: LiveData<List<CatModel>> get() = _cats
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchCats()
    }

    private fun fetchCats() {
        viewModelScope.launch {
            try {
                // Fetch students using the repository
                val result = withContext(Dispatchers.IO) {
                    val catsList = mutableListOf<CatModel>()
                    var fetchError: Throwable? = null

                    catRepository.getCats(
                        onResult = { cats ->
                            catsList.addAll(cats ?: emptyList())
                        },
                        onError = { error ->
                            fetchError = error
                        }
                    )

                    if (fetchError != null) {
                        throw fetchError!!
                    }
                    catsList
                }
                _cats.postValue(result)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}