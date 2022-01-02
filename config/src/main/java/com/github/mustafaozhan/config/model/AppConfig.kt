package com.github.mustafaozhan.config.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    @SerialName("ad_config") val adConfig: AdConfig,
    @SerialName("timeout_anr_watch_dog") val timeOutANRWatchDog: Int
)
