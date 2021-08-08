package com.skillbox.skillbox.files

import android.content.Context
import android.os.Environment
import com.skillbox.skillbox.files.network.Network
import java.io.File

class MainFragmentRepository(private val context: Context) {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    suspend fun downloadFile(urlAdress: String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return
        val url = urlAdress
        val filesDir = context.getExternalFilesDir(FILES_DIR_NAME)
        val fileName = "${System.currentTimeMillis()}_name"
        val file = File(filesDir, fileName)
        try {
            file.outputStream().use { fileOutputSteram ->
                Network.api
                    .getFile(url)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutputSteram)
                    }
                sharedPrefs.edit()
                    .putString(url, fileName)
                    .apply()
            }
        } catch (t: Throwable) {
            file.delete()
        } finally {

        }

    }


    companion object {
        const val SHARED_PREF = "Shared preferences"
        const val FILES_DIR_NAME = "Folder for downloads files"
    }
}