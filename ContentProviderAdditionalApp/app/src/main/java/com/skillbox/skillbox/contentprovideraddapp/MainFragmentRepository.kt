package com.skillbox.skillbox.contentprovideraddapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainFragmentRepository(private val context: Context) {
    //    сохраняем пользователя
    suspend fun saveUser(name: String, age: Int) =
//        переходим в фоновый поток с помощью диспетчера
        withContext(Dispatchers.IO) {
            try {
                //            выполняем последовательно сохранение пользователя и всех его данных
                val contactId = Random.nextLong()
                //        создаем объект content values
                val contentValues = ContentValues().apply {
//            заполняем колонку ID пользователя
                    put(ID, contactId)
//            заполняем колонку имени пользователя
                    put(NAME, name)
//             заполняем колонку возраста пользователя
                    put(AGE, age)
                }
//    сохраняем пользователя
                context.contentResolver.insert(
//            указываем тип Uri
                    USERS_CONTENT_URI,
                    contentValues
                )
                return@withContext true
            } catch (t: Throwable) {
                return@withContext false
            }
        }


    //    сохраняем курс
    suspend fun saveCourse(title: String) =
//        переходим в фоновый поток с помощью диспетчера
        withContext(Dispatchers.IO) {
            try {
                //            выполняем последовательно сохранение пользователя и всех его данных
                val contactId = Random.nextLong()
                //        создаем объект content values
                val contentValues = ContentValues().apply {
//            заполняем колонку ID пользователя
                    put(ID, contactId)
//            заполняем колонку имени пользователя
                    put(TITLE, title)
                }
//    сохраняем пользователя
                context.contentResolver.insert(
//            указываем тип Uri
                    COURSES_CONTENT_URI,
                    contentValues
                )
                return@withContext true
            } catch (t: Throwable) {
                return@withContext false
            }
        }


    //    удаление пользователя
    suspend fun deleteContact(id: Long) = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
//            указываем uri
            USERS_CONTENT_URI,
//            указываем id нужного нам контакта
            "$ID = ? ", arrayOf(id.toString())
        )
    }

    //    удаление курса
    suspend fun deleteCourse(id: Long) = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
//            указываем uri
            COURSES_CONTENT_URI,
//            указываем id нужного нам контакта
            "$ID = ? ", arrayOf(id.toString())
        )
    }

    //    удаление всех курсов сразу
    suspend fun deleteAllCourses() = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
//            указываем uri
            COURSES_CONTENT_URI,
//            указываем id нужного нам контакта
            "$ID = ? ", arrayOf()
        )
    }



    //    изменение пользователя
    suspend fun updateUser(id: Long, contentValues: ContentValues) = withContext(Dispatchers.IO) {
        context.contentResolver.update(
//            указываем uri
            USERS_CONTENT_URI,
            contentValues,
//            указываем id нужного нам контакта
            "$ID = ? ", arrayOf(id.toString())
        )
    }
    //    изменение курса
    suspend fun updateCourse(id: Long, contentValues: ContentValues) = withContext(Dispatchers.IO) {
        context.contentResolver.update(
//            указываем uri
            COURSES_CONTENT_URI,
            contentValues,
//            указываем id нужного нам контакта
            "$ID = ? ", arrayOf(id.toString())
        )
    }


    //    получаем список всех контактов на фоновом потоке с помощью диспетчера
    suspend fun getAllContacts(): List<User> = withContext(Dispatchers.IO) {
//    получаем объект курсора для списка контактов
        context.contentResolver.query(
//            указываем Uri
            USERS_CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
//            получаем контакты из объекта курсора
            getContactsFromCursor(it)
        }.orEmpty()
    }

    //    получение контактов из объекта курсора
    private fun getContactsFromCursor(cursor: Cursor): List<User> {
//    проверяем присутсвие первой строки в курсоре
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<User>()
//    пока курсору есть куда двигаться по таблице списка контактов вниз, выполняем цикл
        do {
//            получаем индекс имени
            val nameIndex = cursor.getColumnIndex(NAME)
//            получаем имя контакта из индекса
            val name = cursor.getString(nameIndex) ?: "Without name"
//            получаем индекс ID контакта
            val idIndex = cursor.getColumnIndex(ID)
//            получаем ID контакта из индекса
            val id = cursor.getLong(idIndex)
            val ageIndex = cursor.getColumnIndex(AGE)
            val age = cursor.getInt(ageIndex)
//            добавляем контакт в список
            list.add(User(id, name, age))
        } while (cursor.moveToNext())
//        возвращаем список контактов
        return list
    }


    companion object {
        private const val AUTHORITY = "com.skillbox.skillbox.contentprovider.provider"
        private val AUTHORITY_URI = Uri.parse("content://$AUTHORITY")
        private val USERS_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "users")
        private val COURSES_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "courses")
        private val COURSES_CONTENT_URI_ID = Uri.withAppendedPath(AUTHORITY_URI, "courses/#")

        private const val NAME = "name"
        private const val ID = "id"
        private const val AGE = "age"
        private const val TITLE = "title"

    }
}