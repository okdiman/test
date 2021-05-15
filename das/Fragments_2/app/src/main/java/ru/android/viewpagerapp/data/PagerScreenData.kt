package ru.android.viewpagerapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.android.viewpagerapp.R

data class PagerScreenData(
    val tags: List<ArticleTag>,
    @StringRes val topicTitle: Int,
    @StringRes val topicText: Int,
    @DrawableRes val topicImage: Int
) {

    companion object {
        fun getListOfScreenData(): List<PagerScreenData> {
            return listOf(
                PagerScreenData(
                    tags = listOf(ArticleTag.NEWS, ArticleTag.IT),
                    topicTitle = R.string.news_1_topic,
                    topicText = R.string.news_1_string,
                    topicImage = R.drawable.news_1
                ),
                PagerScreenData(
                    tags = listOf(ArticleTag.ANDROID, ArticleTag.IOS),
                    topicTitle = R.string.news_2_topic,
                    topicText = R.string.news_2_string,
                    topicImage = R.drawable.news_2
                ),
                PagerScreenData(
                    tags = listOf(ArticleTag.POLITICS, ArticleTag.NEWS),
                    topicTitle = R.string.news_3_topic,
                    topicText = R.string.news_3_string,
                    topicImage = R.drawable.news_3
                ),
                PagerScreenData(
                    tags = listOf(ArticleTag.WEB, ArticleTag.IOS),
                    topicTitle = R.string.news_1_topic,
                    topicText = R.string.news_2_string,
                    topicImage = R.drawable.news_3
                ),
                PagerScreenData(
                    tags = listOf(ArticleTag.WEB, ArticleTag.ANDROID),
                    topicTitle = R.string.news_3_topic,
                    topicText = R.string.news_4_string,
                    topicImage = R.drawable.news_1
                ),
                PagerScreenData(
                    tags = listOf(
                        ArticleTag.NEWS,
                        ArticleTag.WEB,
                        ArticleTag.DESIGN,
                        ArticleTag.IT
                    ),
                    topicTitle = R.string.news_2_topic,
                    topicText = R.string.news_3_string,
                    topicImage = R.drawable.news_4
                )

            )
        }
    }
}
