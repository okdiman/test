package com.skillbox.skillbox.myapplication

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.skillbox.myapplication.classes.Resorts

class ResortListViewModel() : ViewModel() {

    //   создаем mutable live data, чтобы иметь возможность вносить изменения, но доступным извне делает только неизменяемую
    private val resortLiveData = MutableLiveData<List<Resorts>>()
    val resorts: LiveData<List<Resorts>>
        get() = resortLiveData

    //    используем SingleLiveEvent класс, чтобы избежать повторения событий тоста при пересоздании активити
    private val showToastLiveData = SingleLiveEvent<Unit>()

    //    показ тоста
    val showToast: LiveData<Unit>
        get() = showToastLiveData

    //    привязываем репозиторий
    private val repository = ResortRepository()

    //    добавление нового элемента
    fun addResort(
        context: Context,
        resortsType: String,
        name: String,
        country: String,
        photo: String,
        place: String
    ) {
//    получаем новый элемент из репозитория
        val newResort = repository.addResort(resortsType, name, country, photo, place)
//    получаем новый список элементов
        val updatedList = arrayListOf(newResort) + resortLiveData.value.orEmpty()
//    передаем новый список live data, для оповещения об изменениях
        resortLiveData.postValue(updatedList)
//    передаем оповещение о необходимости вызова тоста
        showToastLiveData.postValue(Toast.makeText(context, "Element was added", Toast.LENGTH_SHORT).show())
    }

    //     удаление элемента
    fun deleteResort(context: Context, position: Int) {
        resortLiveData.postValue(repository.deleteResort(resortLiveData.value.orEmpty(), position))
        //    передаем оповещение о необходимости вызова тоста
        showToastLiveData.postValue(Toast.makeText(context, "Element was deleted", Toast.LENGTH_SHORT).show())
    }

}