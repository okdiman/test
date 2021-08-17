package com.skillbox.skillbox.contentprovider.main

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.skillbox.skillbox.contentprovider.classes.Contact
import com.skillbox.skillbox.contentprovider.utils.IncorrectFormException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class GeneralRepository(private val context: Context) {

    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Data.DISPLAY_NAME
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

            list.add(Contact(id, name, null, null))

        } while (cursor.moveToNext())
        return list
    }

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

    suspend fun saveContact(name: String, phone: String, email: String) =
        withContext(Dispatchers.IO) {
//            if (phonePattern.matcher(phone).matches().not() || name.isBlank()) {
//                throw IncorrectFormException()
//            }
            val contactId = saveRawContact()
            saveContactName(contactId, name)
            saveContactPhone(contactId, phone)
            saveContactEmail(contactId, email)
        }

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        return uri?.lastPathSegment?.toLongOrNull() ?: error("Can't save the raw")
    }

    private fun saveContactName(contactId: Long, name: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactPhone(contactId: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactEmail(contactId: Long, email: String) {
        if (email.isEmpty()) return
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Email.DATA, email)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

//    suspend fun deleteContact(contactId: Long) = withContext(Dispatchers.IO){
//        context.contentResolver.delete(
//
//        )
//    }

}