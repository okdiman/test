package com.skillbox.skillbox.myapplication

interface DialogData {
    fun downloadDataToDialog (
        articlesToDialog: List<Article>,
        multiFourToDialog: BooleanArray,
        oceansTypesToDialog: Array<String>,
        activeListToDialog: MutableList<String>)
}