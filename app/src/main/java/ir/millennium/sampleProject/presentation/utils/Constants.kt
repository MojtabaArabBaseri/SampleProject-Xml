package ir.millennium.sampleProject.presentation.utils

import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.data.model.local.aboutMe.UserProfileEntity
import ir.millennium.sampleProject.data.model.local.aboutMe.UserProfileSocialNetworkEntity

object Constants {

    const val SPLASH_DISPLAY_LENGTH = 3500

    const val USER_NAME = "mojtaba"

    const val PASSWORD = "123456"

    var BACK_PRESSED: Long = 0

    const val MOVIE_DATABASE_NAME = "SampleProjectDB.db"

    const val BASIC_URL = "https://newsapi.org/v2/"

    const val API_KEY = "1f02e97880074860bd0aa75e83801ff4"

    const val MY_LINKEDIN_URL = "https://www.linkedin.com/in/mojtaba-arabbaseri-003343b0/"

    const val HEADER_CACHE_CONTROL = "Cache-Control"

    const val HEADER_PRAGMA = "Pragma"

    const val CACHE_SIZE_FOR_RETROFIT = (5 * 1024 * 1024).toLong()

    val USER_PROFILE_DATA = UserProfileEntity(
        image = R.mipmap.image_user,
        fullName = R.string.full_name,
        socialNetwork = ArrayList<UserProfileSocialNetworkEntity>().apply {
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.github,
                    link = R.string.link_github
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.gitlab,
                    link = R.string.link_gitlab
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.linkedin,
                    link = R.string.link_inkedin
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.telegram,
                    link = R.string.link_telegram
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.whatsapp,
                    link = R.string.link_whatsapp
                )
            )
            add(
                UserProfileSocialNetworkEntity(
                    title = R.string.instagram,
                    link = R.string.link_instagram
                )
            )
        })
}