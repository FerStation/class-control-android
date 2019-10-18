package br.com.fernandobrscunha.classcontrol.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class School (
    var uid: String? = "",
    var name: String? = "",
    var type: String? = "",
    var baseValue: Double = 0.0
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "type" to type,
            "baseValue" to baseValue
        )
    }
}