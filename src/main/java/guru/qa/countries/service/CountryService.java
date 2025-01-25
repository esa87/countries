package guru.qa.countries.service;

import guru.qa.countries.domain.Country;

import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<Country> allCountries();

    Country getCountryByName(String countryName);

    Country createCountry(Country country);

    Country updateCountry(UUID id, Country country);
}