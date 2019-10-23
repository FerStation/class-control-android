package br.com.fernandobrscunha.classcontrol.models

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Class (
    //var school: School? = null,
    //var value: Double = school!!.baseValue,
    var value: Double = 0.0,
    var amount: Int = 0,
    var date: Date = Calendar.getInstance().time,
    var payment: Date? = null,
    var comments: String? = ""
)