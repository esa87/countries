package guru.qa.countries.service;

import guru.qa.countries.data.CountryEntity;
import guru.qa.countries.data.CountryRepository;
import guru.qa.countries.domain.Country;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        return allCountriesGraphql().stream()
                .map(gqlCountry -> new Country(
                        gqlCountry.name(),
                        gqlCountry.code()
                )).toList();
    }

    @Override
    public List<CountryGraphql> allCountriesGraphql() {
        return countryRepository.findAll()
                .stream()
                .map(ce -> {
                    return new CountryGraphql(
                            ce.getId(),
                            ce.getCountryName(),
                            ce.getCountryCode()
                    );
                }).toList();
    }

    @Override
    public Country getCountryByName(String countryName) {
        CountryEntity ce = countryRepository.findByCountryName(countryName).get();
        return new Country(ce.getCountryName(), ce.getCountryCode());
    }

    @Override
    public CountryGraphql graphqlCountryByName(String countryName) {
        CountryEntity ce = countryRepository.findByCountryName(countryName).get();
        return new CountryGraphql(ce.getId(), ce.getCountryName(), ce.getCountryCode());
    }

    @Override
    public Country createCountry(Country country) {
        if (countryRepository.findByCountryName(country.name()).isEmpty()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCountryName(country.name());
            countryEntity.setCountryCode(country.code());
            countryRepository.save(countryEntity);
        }
        CountryEntity ce = countryRepository.findByCountryName(country.name()).get();
        return new Country(ce.getCountryName(), ce.getCountryCode());
    }

    @Override
    public CountryGraphql createCountryGraphql(CountryInputGraphql country) {
        if (countryRepository.findByCountryName(country.name()).isEmpty()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCountryName(country.name());
            countryEntity.setCountryCode(country.code());
            countryRepository.save(countryEntity);
        }
        CountryEntity ce = countryRepository.findByCountryName(country.name()).get();
        return new CountryGraphql(ce.getId(), ce.getCountryName(), ce.getCountryCode());
    }

    @Override
    public Country updateCountry(UUID id, Country country) {
        if (!countryRepository.findById(id).isEmpty()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setId(id);
            countryEntity.setCountryName(country.name());
            countryEntity.setCountryCode(country.code());
            countryRepository.save(countryEntity);
        }
        CountryEntity ce = countryRepository.findByCountryName(country.name()).get();
        return new Country(ce.getCountryName(), ce.getCountryCode());
    }

    @Override
    public CountryGraphql updateCountryGraphql(UUID id, CountryInputGraphql country) {
        if (!countryRepository.findById(id).isEmpty()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setId(id);
            countryEntity.setCountryName(country.name());
            countryEntity.setCountryCode(country.code());
            countryRepository.save(countryEntity);
        }
        CountryEntity ce = countryRepository.findByCountryName(country.name()).get();
        return new CountryGraphql(ce.getId(), ce.getCountryName(), ce.getCountryCode());
    }
}