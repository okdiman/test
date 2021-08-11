package com.skillbox.skillbox.files.main

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.skillbox.skillbox.files.network.Network
import java.io.File


class DataStoreRepository(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

    private suspend fun save(key: String, text: String) {
        context.dataStore.edit {
            val specialKey = stringPreferencesKey(key)
            it[specialKey] = text
        }
    }

    suspend fun downloadFile(
        urlAddress: String,
        name: String,
        filesDir: File
    ): Boolean {
//        устанавливаем имя файла и адрес
        val fileName = "${System.currentTimeMillis()}_$name"
        val file = File(filesDir, fileName)
        try {
//           производит запись данных в созданный файл из скачанного файла из сети
            file.outputStream().use { fileOutputStream ->
                Network.api
                    .getFile(urlAddress)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutputStream)
                    }
//                делаем запись в dataStore о скачивании файла
                save(urlAddress, fileName)
            }
            return true
        } catch (t: Throwable) {
//            в случае ошибки удаляем файл с носителя
            Log.i("download", t.toString())
            file.delete()
            return false
        }
    }

    companion object {
        const val DATA_STORE_NAME = "data store"
    }
}