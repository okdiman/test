package com.skillbox.skillbox.contentprovideraddapp

import android.app.Application
import android.content.ContentValues
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    //    создаем лайв дату для передачи списка курсов
    private val courseListLiveData = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>>
        get() = courseListLiveData

    //    создаем лайв дату для передачи запрошенного курса
    private val courseByIdLiveData = MutableLiveData<Course>()
    val courseById: LiveData<Course>
        get() = courseByIdLiveData


    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData

    //    репозиторий
    private val repo = MainFragmentRepository(application)

    //    добавление курса
    fun addNewCourse(title: String) {
//        устанавливаем значение загрузки в лайв дату
        isLoadingLiveData.postValue(true)
//        используем корутину для suspend функций
        viewModelScope.launch {
            try {
//                пытаемся сохранить курс
                repo.saveCourse(title)
            } catch (t: Throwable) {
//                в случае ошибки оповещаем лайв дату ошибок
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату загрузки о завершении работы
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    получение списка курсов
    fun getAllCourses() {
//    сообщаем о начале загрузки
        isLoadingLiveData.postValue(true)
//    создаем корутину для работы с suspend функциями
        viewModelScope.launch {
            try {
//                получаем список курсов и передаем в лайв дату
                courseListLiveData.postValue(repo.getAllCourses())
            } catch (t: Throwable) {
//                в случае ошибки передаем ошибку в лайв дату ошибки
                courseListLiveData.postValue(emptyList())
                isErrorLiveData.postValue(t.message)
            } finally {
//                сообщаем о завершении загрузки
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    удаление курса из списка по ID
    fun deleteCourseByIDFromMemory(id: Long) {
//    сообщаем о начале загрузки
        isLoadingLiveData.postValue(true)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                выполняем удаление курса
                repo.deleteCourseById(id)
            } catch (t: Throwable) {
//                оповещаем лайв дату ошибки об ошибке
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату об окончании процесса удаления
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    удаление всех курсов
    fun deleteAllCoursesFromMemory() {
//    сообщаем о начале загрузки
        isLoadingLiveData.postValue(true)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                выполняем удаление всех курсов
                repo.deleteAllCourses()
            } catch (t: Throwable) {
//                оповещаем лайв дату ошибки об ошибке
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату об окончании процесса удаления
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    получение курса по ID
    fun getCourseById(id: Long) {
//    сообщаем о начале загрузки
        isLoadingLiveData.postValue(true)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                получаем курс по ID и передаем в лайв дату
                courseByIdLiveData.postValue(repo.getCourseByID(id))
            } catch (t: Throwable) {
//                оповещаем лайв дату ошибки об ошибке
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату об окончании процесса удаления
                isLoadingLiveData.postValue(false)
            }
        }
    }

    //    изменение курса
    fun updateCourse(id: Long, contentValues: ContentValues) {
//    сообщаем о начале загрузки
        isLoadingLiveData.postValue(true)
//    открываем корутину для suspend функции репозитория
        viewModelScope.launch {
            try {
//                изменяем курс
                repo.updateCourse(id, contentValues)
            } catch (t: Throwable) {
//                оповещаем лайв дату ошибки об ошибке
                isErrorLiveData.postValue(t.message)
            } finally {
//                оповещаем лайв дату об окончании процесса удаления
                isLoadingLiveData.postValue(false)
            }
        }
    }

}