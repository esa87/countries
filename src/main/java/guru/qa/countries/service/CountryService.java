package guru.qa.countries.service;

import guru.qa.countries.domain.Country;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;

import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<CountryGraphql> allCountriesGraphql();

    CountryGraphql graphqlCountryByName(String countryName);

    CountryGraphql createCountryGraphql(CountryInputGraphql country);

    CountryGraphql updateCountryGraphql(UUID id, CountryInputGraphql country);
}