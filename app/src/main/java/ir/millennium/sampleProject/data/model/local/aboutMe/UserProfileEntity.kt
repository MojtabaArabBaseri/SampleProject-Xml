package ir.millennium.sampleProject.data.model.local.aboutMe

data class UserProfileEntity(
    val image: Int,
    val fullName: Int,
    val socialNetwork: List<UserProfileSocialNetworkEntity>
)
