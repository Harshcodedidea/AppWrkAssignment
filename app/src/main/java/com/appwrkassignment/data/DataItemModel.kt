package com.appwrkassignment.data

import java.io.Serializable


data class DataItemModel(
    var title: String? = "",
    var description: String? = "",
    var status: Boolean? = false
): Serializable
