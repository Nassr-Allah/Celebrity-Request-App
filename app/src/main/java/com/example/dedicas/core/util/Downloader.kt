package com.example.dedicas.core.util

interface Downloader {

    fun downloadFile(url: String, token: String): Long

}