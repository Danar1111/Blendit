package com.android.blendit.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.android.blendit.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)
    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

@SuppressLint("ConstantLocale")
val timeStamp: String = SimpleDateFormat(
    "dd-MM-yyyy", Locale.getDefault()
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun reduceFileImage(file: File?): File? {
    val bitmap = try {
        BitmapFactory.decodeFile(file?.path)
    } catch (e: Exception) {
        if (file != null) {
            Log.e("Utils", "Failed to decode file: ${file.path}", e)
        }
        return null
    }

    bitmap?.let {
        var compressQuality = 100
        var streamLength: Int
        do {
            val baosPic = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, compressQuality, baosPic)
            val bitmapData = baosPic.toByteArray()
            streamLength = bitmapData.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        it.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
    return null
}

fun convertImageToMultipart(photoFile: File?, formFieldName: String): MultipartBody.Part? {
    val reducedFile = reduceFileImage(photoFile)
    return reducedFile?.let { file ->
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        requestImageFile.let {
            MultipartBody.Part.createFormData(
                formFieldName, file.name, it
            )
        }
    }
}
