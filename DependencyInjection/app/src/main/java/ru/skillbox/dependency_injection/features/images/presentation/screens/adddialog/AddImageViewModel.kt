package ru.skillbox.dependency_injection.features.images.presentation.screens.adddialog

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.skillbox.dependency_injection.R
import ru.skillbox.dependency_injection.core.utils.SingleLiveEvent
import ru.skillbox.dependency_injection.features.images.domain.repository.Repository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddImageViewModel @Inject constructor(
    private val imagesRepository: Repository
) : ViewModel() {

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val saveSuccessSingleLiveEvent = SingleLiveEvent<Unit>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>(false)

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessSingleLiveEvent

    fun saveImage(name: String, url: String) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                imagesRepository.saveImage(name, url)
                saveSuccessSingleLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t)
                toastSingleLiveEvent.postValue(R.string.image_add_error)
            } finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }
}