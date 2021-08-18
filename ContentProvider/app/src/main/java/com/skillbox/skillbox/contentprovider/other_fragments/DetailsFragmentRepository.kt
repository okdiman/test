package com.skillbox.skillbox.contentprovider.other_fragments

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.skillbox.skillbox.contentprovider.classes.Contact
import com.skillbox.skillbox.contentprovider.custom_content_provider.CustomContentProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsFragmentRepository(private val context: Context) {

    //    получение данных о контакте
    fun getContactInfo(contactId: Long, name: String): Contact {
        return Contact(contactId, name, getContactsPhones(contactId), getEmailsOfContact(contactId))
    }

    //    получение списка номеров телефонов контакта
    private fun getContactsPhones(contactId: Long): List<String> {
        return context.contentResolver.query(
//            указываем колонку в таблице
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
//            делаем выборку по нашему ID
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
//            производим экранирование для защиты от SQL injection
            arrayOf(contactId.toString()),
            null
        )?.use {
//            получаем список номеров из курсора
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    //    получение списка номеров из курсора
    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
//        если таблица пустая, возвращаем пустой лист
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
//        пока курсор не дойдет о последней строки таблицы, сохраняем номера в список
        do {
//            получаем индекс номера телефона контакта
            val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
//            получаем номер из индекса
            val number = cursor.getString(phoneIndex)
//            сохраняем в список
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }

    //    получение списка e-mail'ов контакта
    private fun getEmailsOfContact(contactId: Long): List<String> {
        return context.contentResolver.query(
//            указываем колонку в таблице
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
//            делаем выборку по нашему ID
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
//            производим экранирование для защиты от SQL injection
            arrayOf(contactId.toString()),
            null
        )?.use {
//            получеам список e-mail'ов из курсора
            getEmailsFromCursor(it)
        }.orEmpty()
    }

    //    получение списка e-mail'ов из курсора
    private fun getEmailsFromCursor(cursor: Cursor): List<String> {
//        если таблица пустая, возвращаем пустой лист
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
//        пока курсор не дойдет о последней строки таблицы, сохраняем номера в список
        do {
//            получаем индекс e-mail контакта
            val emailIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
//            получаем e-mail из индекса
            val email = cursor.getString(emailIndex)
//            сохраняем в список
            list.add(email)
        } while (cursor.moveToNext())
        return list
    }


    suspend fun deleteContact(contactId: Long) = withContext(Dispatchers.IO) {
        context.contentResolver.delete(
            ContactsContract.Data.CONTENT_URI,
            ContactsContract.Data._ID + "=?", arrayOf(contactId.toString())
        )
    }

}