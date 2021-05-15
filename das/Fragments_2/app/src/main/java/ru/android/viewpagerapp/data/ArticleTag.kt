package ru.android.viewpagerapp.data


enum class ArticleTag(val humanTag: String) {
    NEWS("News"),
    IT("Information Technology"),
    ANDROID("Android"),
    IOS("iOS"),
    WEB("Web"),
    DESIGN("Design"),
    POLITICS("Politics"),
    MEMES("Memes");

    companion object {
        fun fromInt(value: Int): ArticleTag {
            return values()[value]
        }
    }
}