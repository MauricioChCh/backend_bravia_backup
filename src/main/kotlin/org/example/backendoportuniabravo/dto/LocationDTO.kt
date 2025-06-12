package org.example.backendoportuniabravo.dto


// response DTO
//data class LocationResponse(
//    var id: Long? = null,
//    var address: String? = null,
//    var city: CityDetails? = null,
//    var country: CountryDetails? = null,
//) // TODO:  look if we can remove this DTO

// result DTO
data class LocationResult(
    var id: Long? = null,
    var address: String? = null,
    var city: CityDetails? = null,
    var country: CountryDetails? = null,
)

// details DTO
data class CityDetails(
    var id: Long? = null,
    var name: String? = null,
)
data class CountryDetails(
    var id: Long? = null,
    var name: String? = null,
)
data class LocationDetails(
    var id: Long? = null,
    var address: String? = null,
    var city: CityDetails? = null,
    var country: CountryDetails? = null,
)

// update DTO
data class LocationUpdate(
    var id: Long? = null,
    var address: String? = null,
    var city: CityInput? = null,
    var country: CityInput? = null,
)

// input
data class CityInput(
    var id: Long? = null,
)
data class CountryInput(
    var id: Long? = null,
)
data class LocationInput(
    var address: String? = null,
    var city: CityInput? = null,
    var country: CountryInput? = null,
)


class LocationDTO {

}
