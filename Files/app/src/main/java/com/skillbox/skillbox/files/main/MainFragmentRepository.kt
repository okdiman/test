package com.skillbox.skillbox.files.main

import android.content.SharedPreferences
import android.util.Log
import com.skillbox.skillbox.files.network.Network
import java.io.File

class MainFragmentRepository() {
    suspend fun downloadFile(
        urlAddress: String,
        name: String,
        sharedPrefs: SharedPreferences,
        filesDir: File
    ): Boolean {
//        устанавливаем имя файла и адрес
        val fileName = "${System.currentTimeMillis()}_$name"
        val file = File(filesDir, fileName)
        try {
//            производит запись данных в созданный файл из скачанного файла из сети
            file.outputStream().use { fileOutputStream ->
                Network.api
                    .getFile(urlAddress)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutputStream)
                    }
//                делаем запись в shared prefs о скачивании файла
                sharedPrefs.edit()
                    .putString(urlAddress, fileName)
                    .commit()
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
        const val SHARED_PREF = "Shared preferences"
        const val FILES_DIR_NAME = "Folder for downloads files"
        const val FIRST_RUN = "First run"
    }
}