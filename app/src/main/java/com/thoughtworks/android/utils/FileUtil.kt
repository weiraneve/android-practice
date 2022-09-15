package com.thoughtworks.android.utils

import android.content.Context
import androidx.annotation.RawRes
import java.io.ByteArrayOutputStream
import java.io.IOException

class FileUtil {

    companion object {

        fun getStringFromRaw(context: Context, @RawRes id: Int): String {
            var str: String
            try {
                val `is` = context.resources.openRawResource(id)
                val os = ByteArrayOutputStream()
                var index = `is`.read()
                while (index != -1) {
                    os.write(index)
                    index = `is`.read()
                }
                str = os.toString()
                `is`.close()
            } catch (e: IOException) {
                str = ""
            }
            return str
        }

    }

}