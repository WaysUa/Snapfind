package com.main.snapfind.features.main_photos.data.netowrk.entities

import com.main.snapfind.features.main_photos.data.netowrk.entities.Sponsor

data class Sponsorship(
    val impression_urls: List<String>,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
)