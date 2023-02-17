package com.example.dedicas.feature_request.domain

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.dedicas.feature_browsing.data.BrowsingRepository
import java.io.File
import javax.inject.Inject

class GetFilePathUseCase @Inject constructor(
    private val repository: BrowsingRepository
) {

    operator fun invoke(uri: Uri, contentResolver: ContentResolver, context: Context): File? {
        val file = try {
            val inputStream = contentResolver.openInputStream(uri)
            val fileName = contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                Log.d("GetFileUseCase", "${cursor.getString(sizeIndex).toLong() / 1000000} mb")
                cursor.moveToFirst()
                cursor.getString(nameIndex)
            }
            val cacheDir = context.cacheDir
            val tempFile = fileName?.let { File(cacheDir, fileName) }
            tempFile?.createNewFile()
            tempFile?.outputStream().use {
                if (it != null) {
                    inputStream?.copyTo(it)
                }
            }
            inputStream?.close()
            tempFile
        } catch (e: Exception) {
            Log.d("GetFileUseCase", e.localizedMessage ?: "File Error")
            null
        }
        return file
    }

}