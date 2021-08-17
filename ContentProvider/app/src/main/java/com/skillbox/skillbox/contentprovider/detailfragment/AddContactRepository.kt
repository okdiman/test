package com.skillbox.skillbox.contentprovider.detailfragment

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddContactRepository(private val context: Context) {

//    сохраняем контакт
    suspend fun saveContact(name: String, phone: String, email: String) =
//        переходим в фоновый поток с помощью диспетчера
        withContext(Dispatchers.IO) {
//            выполняем последовательно сохранение контакта и всех его данных
            val contactId = saveRawContact()
            saveContactName(contactId, name)
            saveContactPhone(contactId, phone)
            saveContactEmail(contactId, email)
        }

//    сохраняем объект контакта в папку RawContacts, получаем Uri контакта
    private fun saveRawContact(): Long {
//    сохраняем контакт
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
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

//    присваиваем созданному контакту e-mail, усли он имеется
    private fun saveContactEmail(contactId: Long, email: String) {
//    если пользователь не указал e-mail, заканчиваем функцию
        if (email.isEmpty()) return
//    создаем объект ContentValues, который хотим положить к контакту
        val contentValues = ContentValues().apply {
//            находим контакт по ID
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
//            указываем какой тип данных кладем
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
//            указываем какой файл и в какое поле кладем
            put(ContactsContract.CommonDataKinds.Email.DATA, email)
        }
//    присваиваем нашему контакту объект ContentValues
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }
}