package com.skillbox.skillbox.contentprovideraddapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.skillbox.skillbox.contentprovideraddapp.course.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainFragmentRepository(private val context: Context) {

    //    сохраняем курс
    suspend fun saveCourse(title: String) =
//        переходим в фоновый поток с помощью диспетчера
        withContext(Dispatchers.IO) {
//              выполняем последовательно сохранение курса и всех его данных
//              присваиваем курсу ID
            val courseId = Random.nextLong()
//              создаем объект content values
            val contentValues = ContentValues().apply {
//              заполняем колонку ID курса
                put(ID, courseId)
//              заполняем колонку названия курса
                put(TITLE, title)
            }
            Log.i("saving", "${Thread.currentThread()}")
//              сохраняем курс
            context.contentResolver.insert(
//              указываем тип Uri
                COURSES_CONTENT_URI,
//              кладем contentValues
                contentValues
            )
        }

    //    удаление курса по Id
    suspend fun deleteCourseById(id: Long) = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
//            указываем uri
            COURSES_CONTENT_URI_ID,
//            указываем id нужного нам курса
            "$ID = ? ", arrayOf(id.toString())
        )
    }

    //    удаление всех курсов сразу
    suspend fun deleteAllCourses() = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
//            указываем uri
            COURSES_CONTENT_URI,
            "$ID = ? ", arrayOf()
        )
    }


    //    изменение курса
    suspend fun updateCourse(id: Long, contentValues: ContentValues) = withContext(Dispatchers.IO) {
        context.contentResolver.update(
//            указываем uri
            COURSES_CONTENT_URI_ID,
//            кладем contentValues
            contentValues,
//            указываем id нужного нам курса
            "$ID = ? ", arrayOf(id.toString())
        )
    }


    //    получаем список всех курсов на фоновом потоке с помощью диспетчера
    suspend fun getAllCourses(): List<Course> = withContext(Dispatchers.IO) {
//    получаем объект курсора для списка курсов
        context.contentResolver.query(
//            указываем Uri
            COURSES_CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
//            получаем курсы из объекта курсора
            getAllCoursesFromCursor(it)
        }.orEmpty()
    }

    //    получение курсов из объекта курсора
    private fun getAllCoursesFromCursor(cursor: Cursor): List<Course> {
//    проверяем присутсвие первой строки в курсоре
        if (cursor.moveToFirst().not()) return emptyList()
//    создаем список курсов
        val list = mutableListOf<Course>()
//    пока курсору есть куда двигаться по таблице списка курсов вниз, выполняем цикл
        do {
//            получаем индекс названия
            val titleIndex = cursor.getColumnIndex(TITLE)
//            получаем название курса из индекса
            val title = cursor.getString(titleIndex) ?: "Without title"
//            получаем индекс ID курсов
            val idIndex = cursor.getColumnIndex(ID)
//            получаем ID курса из индекса
            val id = cursor.getLong(idIndex)
//            добавляем курс в список
            list.add(Course(id, title))
        } while (cursor.moveToNext())
//        возвращаем список курсов
        return list
    }

    //    получаем определенный курс по ID
    suspend fun getCourseByID(id: Long): Course? = withContext(Dispatchers.IO) {
//    получаем объект курсора для списка курсов
        context.contentResolver.query(
//            указываем Uri
            COURSES_CONTENT_URI,
            null,
//            устанавливаем выборку по ID
            "$ID = ?",
//            выполняем экранирование для защиты от SQL injection
            arrayOf(id.toString()),
            null
        )?.use {
//            получаем курс из объекта курсора
            getSingleCourseFromCursor(it)
        }
    }

    //    получение курса из объекта курсора
    private fun getSingleCourseFromCursor(cursor: Cursor): Course? {
//    проверяем присутсвие первой строки в курсоре
        if (cursor.moveToFirst().not()) return null
//            получаем индекс названия
        val titleIndex = cursor.getColumnIndex(TITLE)
//            получаем название курса из индекса
        val title = cursor.getString(titleIndex) ?: "Without title"
//            получаем индекс ID курса
        val idIndex = cursor.getColumnIndex(ID)
//            получаем ID курса из индекса
        val id = cursor.getLong(idIndex)
//        возвращаем курс
        return Course(id, title)
    }


    companion object {
        private const val AUTHORITY = "com.skillbox.skillbox.contentprovider.provider"
        private val AUTHORITY_URI = Uri.parse("content://$AUTHORITY")
        private val COURSES_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "courses")
        private val COURSES_CONTENT_URI_ID = Uri.withAppendedPath(AUTHORITY_URI, "courses/#")
        const val ID = "id"
        const val TITLE = "title"
    }
}