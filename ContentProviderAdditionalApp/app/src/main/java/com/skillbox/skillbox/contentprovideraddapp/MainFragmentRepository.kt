package com.skillbox.skillbox.contentprovideraddapp

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainFragmentRepository(private val context: Context) {
    //    сохраняем контакт
    suspend fun saveUser(name: String, age: Int) =
//        переходим в фоновый поток с помощью диспетчера
        withContext(Dispatchers.IO) {
//            выполняем последовательно сохранение контакта и всех его данных
            val contactId = saveRawContact()
            saveContactName(contactId, name)
            saveContactPhone(contactId, age)
        }

    //    сохраняем объект контакта в папку RawContacts, получаем Uri контакта
    private fun saveRawContact(): Long {
//    сохраняем контакт
        val uri = context.contentResolver.insert(
//            указываем тип Uri
            Uri.parse(USERS),
            ContentValues()
        )
        return uri?.lastPathSegment?.toLongOrNull() ?: error("Can't save the raw")
    }

    //    присваиваем созданному контакту имя
    private fun saveContactName(contactId: Long, name: String) {
//    создаем объект ContentValues, который хотим положить к контакту
        val contentValues = ContentValues().apply {
//            находим контакт по ID
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
//            указываем какой тип данных кладем
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
//            указываем какой файл и в какое поле кладем
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
//    присваиваем нашему контакту объект ContentValues
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    //    присваиваем созданному контакту номер телефона
    private fun saveContactPhone(contactId: Long, phone: String) {
//    создаем объект ContentValues, который хотим положить к контакту
        val contentValues = ContentValues().apply {
//            находим контакт по ID
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
//            указываем какой тип данных кладем
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
//            указываем какой файл и в какое поле кладем
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
//    присваиваем нашему контакту объект ContentValues
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    companion object {
        private const val USERS = "com.skillbox.skillbox.contentprovider.provider.users"
        private const val COURSES = "com.skillbox.skillbox.contentprovider.provider.courses"

        private const val TYPE_USERS = 1
        private const val TYPE_COURSES = 2
        private const val TYPE_USER_ID = 3
        private const val TYPE_COURSE_ID = 4
    }
}