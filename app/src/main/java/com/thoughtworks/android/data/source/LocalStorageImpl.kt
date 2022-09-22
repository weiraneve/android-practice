package com.thoughtworks.android.data.source

import android.content.Context
import com.thoughtworks.android.common.Constants
import com.thoughtworks.android.utils.SharedPreferenceUtil

class LocalStorageImpl(private val context: Context): LocalStorage {

    companion object {
        private const val KEY_KNOWN = "known"
    }

    override var isHintShown: Boolean
        get() = SharedPreferenceUtil.readBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, false)
        set(value) {
            SharedPreferenceUtil.writeBoolean(context, Constants.SHARED_PREFERENCE_FILE, KEY_KNOWN, value)
        }
}