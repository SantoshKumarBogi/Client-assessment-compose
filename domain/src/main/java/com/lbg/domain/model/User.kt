package com.lbg.domain.model

import kotlinx.serialization.Serializable

/**
 * User data class for the app.
 */
@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val username: String,
    val address: Address,
    val website: String,
    val company: Company,
) {
    @Serializable
    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Location,
    )

    @Serializable
    data class Location(
        val lat: String,
        val lng: String,
    )

    @Serializable
    data class Company(
        val name: String,
        val catchPhrase: String,
        val bs: String,
    )
}
