package com.skillbox.skillbox.files.main

import android.content.Context
import android.os.Environment
import android.util.Log
import com.skillbox.skillbox.files.network.Network
import java.io.File

class MainFragmentRepository() {
    suspend fun downloadFile(urlAddress: String, context: Context) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return
        val sharedPrefs = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val filesDir = context.getExternalFilesDir(FILES_DIR_NAME)
        val fileName = "${System.currentTimeMillis()}_name"
        val file = File(filesDir, fileName)
        Log.i("download" ,"$filesDir, $fileName, $sharedPrefs")
        try {
            file.outputStream().use { fileOutputStream ->
                Network.api
                    .getFile(urlAddress)
                    .byteStream()
                    .use {
                        it.copyTo(fileOutputStream)
                    }
                sharedPrefs.edit()
                    .putString(urlAddress, fileName)
                    .commit()
            }
        } catch (t: Throwable) {
            Log.i("download", t.toString())
//            file.delete()
        } finally {

        }

    }


    companion object {
        const val SHARED_PREF = "Shared preferences"
        const val FILES_DIR_NAME = "Folder for downloads files"
    }
}