package com.example.dedicas.feature_chat.domain

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.dedicas.core.util.Downloader

class DownloadFileUseCase(private val context: Context) : Downloader {

    @RequiresApi(Build.VERSION_CODES.M)
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    private val contentResolver = context.contentResolver

    @RequiresApi(Build.VERSION_CODES.M)
    override fun downloadFile(url: String, token: String): Long {
        val mimeType = contentResolver.getType(url.toUri())
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .addRequestHeader("Authorization", token)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}.$mimeType")
        return downloadManager.enqueue(request)
    }

}