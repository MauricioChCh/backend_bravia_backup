package org.example.backendoportuniabravo.mappers

import org.example.backendoportuniabravo.dto.CountryDetails
import org.example.backendoportuniabravo.dto.LocationDetails
import org.example.backendoportuniabravo.dto.ProvinceDetails
import org.example.backendoportuniabravo.entities.Country
import org.example.backendoportuniabravo.entities.Location
import org.example.backendoportuniabravo.entities.Province
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LocationMapper {
    fun locationToLocationDetails(
        location: Location
    ) : LocationDetails
    
    fun locationListToLocationDetailsList(
        locationList: List<Location>
    ) : List<LocationDetails>
    
    fun locationDetailsToLocation(
        locationDetails: LocationDetails
    ) : Location
    
    fun locationDetailsListToLocation(
        locationDetailsList: List<LocationDetails>
    ) : List<Location>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProvinceMapper {
    fun provinceToProvinceDetails(
        province: Province
    ) : ProvinceDetails
    
    fun provinceListToProvinceDetailsList(
        provinceList: List<Province>
    ) : List<ProvinceDetails>
    
    fun provinceDetailsToProvince(
        provinceDetails: ProvinceDetails
    ) : Province
    
    fun provinceDetailsListToProvince(
        provinceDetailsList: List<ProvinceDetails>
    ) : List<Province>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CountryMapper {
    fun countryToCountryDetails(
        country: Country
    ) : CountryDetails
    
    fun countryListToCountryDetailsList(
        countryList: List<Country>
    ) : List<CountryDetails>
    
    fun countryDetailsToCountry(
        countryDetails: CountryDetails
    ) : Country
    
    fun countryDetailsListToCountry(
        countryDetailsList: List<CountryDetails>
    ) : List<Country>
}