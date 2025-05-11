package org.example.backendoportuniabravo.mapper

import org.example.backendoportuniabravo.dto.CityDetails
import org.example.backendoportuniabravo.dto.CountryDetails
import org.example.backendoportuniabravo.dto.LocationDetails
import org.example.backendoportuniabravo.entity.City
import org.example.backendoportuniabravo.entity.Country
import org.example.backendoportuniabravo.entity.Location
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
interface CityMapper {
    fun provinceToCityDetails(
        city: City
    ) : CityDetails
    
    fun provinceListToCityDetailsList(
        cityList: List<City>
    ) : List<CityDetails>
    
    fun provinceDetailsToProvince(
        cityDetails: CityDetails
    ) : City
    
    fun provinceDetailsListToProvince(
        cityDetailsList: List<CityDetails>
    ) : List<City>
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