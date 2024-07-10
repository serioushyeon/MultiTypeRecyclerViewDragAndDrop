package com.serioushyeon.multityperecyclerviewdraganddrop

sealed class MultiTypeItem {
    data class TextItem(val text: String): MultiTypeItem()
    object BreathButtonItem : MultiTypeItem()
    object PPTButtonItem : MultiTypeItem()
    //object Divider: EditScriptItem()
}
