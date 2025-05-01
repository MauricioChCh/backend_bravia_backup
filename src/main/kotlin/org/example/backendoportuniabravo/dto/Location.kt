package org.example.backendoportuniabravo.dto


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