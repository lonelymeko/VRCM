package io.github.vrcmteam.vrcm.network.api.attributes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VerificationStatus {
    @SerialName("hidden")
    Hidden,

    @SerialName("verified")
    Verified,

    @SerialName("18+")
    EighteenPlus
}