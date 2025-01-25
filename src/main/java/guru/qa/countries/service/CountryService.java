package guru.qa.countries.service;

import guru.qa.countries.domain.Country;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;

import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<Country> allCountries();

    List<CountryGraphql> allCountriesGraphql();

    Country getCountryByName(String countryName);

    CountryGraphql graphqlCountryByName(String countryName);

    Country createCountry(Country country);

    CountryGraphql createCountryGraphql(CountryInputGraphql country);

    Country updateCountry(UUID id, Country country);

    CountryGraphql updateCountryGraphql(UUID id, CountryInputGraphql country);
}