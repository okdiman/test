package com.skillbox.skillbox.contentprovideraddapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainFragmentRepository(private val context: Context) {
    //    сохраняем пользователя
    suspend fun saveUser(name: String, age: Int) =
//        переходим в фоновый поток с помощью диспетчера
        withContext(Dispatchers.IO) {
//            выполняем последовательно сохранение пользователя и всех его данных
            val contactId = saveRawUser()
            saveUserName(contactId, name)
            saveUsersAge(contactId, age)
        }

    //    сохраняем объект пользователя, получаем Uri пользователя
    private fun saveRawUser(): Long {
//    сохраняем пользователя
        val uri = context.contentResolver.insert(
//            указываем тип Uri
            USERS_CONTENT_URI,
            ContentValues()
        )
        return uri?.lastPathSegment?.toLongOrNull() ?: error("Can't save the raw, $uri")
    }

    //    присваиваем созданному пользователю имя
    private fun saveUserName(contactId: Long, name: String) {
//    создаем объект ContentValues, который хотим положить к пользователю
        val contentValues = ContentValues().apply {
//            находим пользователя по ID
            put(COLUMN_USER_ID, contactId)
//            указываем какой тип данных кладем
            put(
                MIMETYPE,
                CONTENT_ITEM_TYPE_NAME
            )
//            указываем какой файл и в какое поле кладем
            put(DATA, name)
        }
//    присваиваем нашему пользователю объект ContentValues
        context.contentResolver.insert(USERS_CONTENT_URI, contentValues)
    }

    //    присваиваем созданному пользователю возраст
    private fun saveUsersAge(contactId: Long, age: Int) {
//    создаем объект ContentValues, который хотим положить к пользователю
        val contentValues = ContentValues().apply {
//            находим пользователя по ID
            put(COLUMN_USER_ID, contactId)
//            указываем какой тип данных кладем
            put(
                MIMETYPE,
                CONTENT_ITEM_TYPE_AGE
            )
//            указываем какой файл и в какое поле кладем
            put(DATA, age)
        }
//    присваиваем нашему пользователю объект ContentValues
        context.contentResolver.insert(USERS_CONTENT_URI, contentValues)
    }


    //    получаем список всех контактов на фоновом потоке с помощью диспетчера
    suspend fun getAllContacts(): List<User> = withContext(Dispatchers.IO) {
//    получаем объект курсора для списка контактов
        context.contentResolver.query(
//            указываем тип Uri
            USERS_CONTENT_URI,
            null,
            null,
            null,
//            сортируем по имени
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
            val idIndex = cursor.getColumnIndex(_ID)
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

        private const val COLUMN_USER_ID = "id"
        private const val CONTENT_ITEM_TYPE_NAME = "vnd.android.cursor.item/name"
        private const val CONTENT_ITEM_TYPE_AGE = "vnd.android.cursor.item/age"
        private const val MIMETYPE = "mimetype"
        private const val DATA = "data1"
        private const val NAME = "name"
        private const val _ID = "id"
        private const val AGE = "age"


        private const val USERS = "com.skillbox.skillbox.contentprovider.provider.users"
        private const val COURSES = "com.skillbox.skillbox.contentprovider.provider.courses"

        private const val TYPE_USERS = 1
        private const val TYPE_COURSES = 2
        private const val TYPE_USER_ID = 3
        private const val TYPE_COURSE_ID = 4
    }
}