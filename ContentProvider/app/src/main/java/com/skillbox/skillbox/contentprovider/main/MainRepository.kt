package com.skillbox.skillbox.contentprovider.main

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.skillbox.skillbox.contentprovider.classes.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainRepository(private val context: Context) {

    //    получаем список всех контактов на фоновом потоке с помощью диспетчера
    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
//    получаем объект курсора для списка контактов
        context.contentResolver.query(
//            указываем тип Uri
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
//            сортируем по имени
            ContactsContract.Data.DISPLAY_NAME
        )?.use {
//            получаем контакты из объекта курсора
            getContactsFromCursor(it)
        }.orEmpty()
    }

    //    получение контактов из объекта курсора
    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
//    проверяем присутсвие первой строки в курсоре
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
//    пока курсору есть куда двигаться по таблице списка контактов вниз, выполняем цикл
        do {
//            получаем индекс имени
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
//            получаем имя контакта из индекса
            val name = cursor.getString(nameIndex)?:"Without name"
//            получаем индекс ID контакта
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
//            получаем ID контакта из индекса
            val id = cursor.getLong(idIndex)
//            добавляем контакт в список
            list.add(Contact(id, name, emptyList(), emptyList()))
        } while (cursor.moveToNext())
//        возвращаем список контактов
        return list
    }
}