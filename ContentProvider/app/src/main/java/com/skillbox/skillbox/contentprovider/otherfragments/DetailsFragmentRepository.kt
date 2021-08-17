package com.skillbox.skillbox.contentprovider.otherfragments

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.skillbox.skillbox.contentprovider.classes.Contact

class DetailsFragmentRepository(private val context: Context) {

    fun getContactInfo(contactId: Long, name: String): Contact {
        return Contact(contactId, name, getContactsPhones(contactId), getEmailsOfContact(contactId))
    }

    private fun getContactsPhones(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(phoneIndex)
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }

    private fun getEmailsOfContact(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getEmailsFromCursor(it)
        }.orEmpty()
    }

    private fun getEmailsFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val emailIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
            val email = cursor.getString(emailIndex)
            list.add(email)
            Log.i("contact", "$list")
        } while (cursor.moveToNext())
        Log.i("contact", "$list")
        return list
    }


//    suspend fun deleteContact(contactId: Long) = withContext(Dispatchers.IO){
//        context.contentResolver.delete(
//
//        )
//    }
}