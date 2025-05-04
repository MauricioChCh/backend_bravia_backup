package org.example.backendoportuniabravo.dto


// response DTO
data class LocationResponse(
    var id: Long? = null,
    var address: String? = null,
    var province: ProvinceDetails? = null,
    var country: CountryDetails? = null,
)

// result DTO
data class LocationResult(
    var id: Long? = null,
    var address: String? = null,
    var province: ProvinceDetails? = null,
    var country: CountryDetails? = null,
)

// details DTO
data class ProvinceDetails(
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
    var province: ProvinceDetails? = null,
    var country: CountryDetails? = null,
)

// update DTO
data class LocationUpdate(
    var id: Long? = null,
    var address: String? = null,
    var province: ProvinceInput? = null,
    var country: ProvinceInput? = null,
)

// input
data class ProvinceInput(
    var id: Long? = null,
)
data class CountryInput(
    var id: Long? = null,
)
data class LocationInput(
    var address: String? = null,
    var province: ProvinceInput? = null,
    var country: CountryInput? = null,
)


