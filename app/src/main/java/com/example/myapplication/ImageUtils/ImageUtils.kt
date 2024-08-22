package com.example.myapplication.ImageUtils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    /**
     * Lưu ảnh từ Uri thành file.
     * @param context: Context của ứng dụng.
     * @param uri: Uri của ảnh được chọn.
     * @return: File đã được lưu từ Uri.
     */
    fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, "temp_image.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}