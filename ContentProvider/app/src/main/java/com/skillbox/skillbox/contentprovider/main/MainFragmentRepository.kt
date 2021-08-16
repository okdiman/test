package com.skillbox.skillbox.contentprovider.main

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.skillbox.skillbox.contentprovider.classes.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainFragmentRepository(private val context: Context) {

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val name = cursor.getString(nameIndex)

            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)

            list.add(Contact(id, name))
        } while (cursor.moveToNext())

        return list
    }
}