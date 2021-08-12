package com.skillbox.skillbox.files.main

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.skillbox.skillbox.files.network.Network
import kotlinx.coroutines.flow.*
import java.io.File


class DataStoreRepository(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

    private suspend fun save(key: Preferences.Key<String>, text: String) {
        context.dataStore.edit {
            it[key] = text
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
            val specialKey = stringPreferencesKey(urlAddress)
//            проверяем наличие данных в dataStore
            if (checkingIfAFileExistsOnTheSystem(specialKey)){
                return false
            }
//           производит запись данных в созданный файл из скачанного файла из сети
            file.outputStream().use { fileOutputStream ->
                Network.api
                    .getFile(urlAddress)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutputStream)
                    }
//                делаем запись в dataStore о скачивании файла
                save(specialKey, fileName)
            }
            return true
        } catch (t: Throwable) {
//            в случае ошибки удаляем файл с носителя
            Log.i("download", t.toString())
            file.delete()
            return false
        }
    }

//    проверка наличия данных в dataStore
    private suspend fun checkingIfAFileExistsOnTheSystem(key: Preferences.Key<String>): Boolean {
        return context.dataStore.data.map {
            it.contains(key)
        }.first()
    }

    companion object {
        const val DATA_STORE_NAME = "data store"
    }
}