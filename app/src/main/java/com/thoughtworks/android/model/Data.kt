package com.thoughtworks.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(val name: String?, val phone: String?) : Parcelable {

}