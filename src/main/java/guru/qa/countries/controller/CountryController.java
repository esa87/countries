package guru.qa.countries.controller;

import guru.qa.countries.domain.Country;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;
import guru.qa.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<Country> all() {
        List<CountryGraphql> countryGraphqls = countryService.allCountriesGraphql();
        List <Country> countries = countryGraphqls.stream()
                .map(countryGraphql -> new  Country(
                        countryGraphql.name(),
                        countryGraphql.code()
        )).toList();
        return countries;
    }

    @GetMapping("/current")
    public Country currentCountry(@RequestParam String countryName) {
        CountryGraphql countryGraphql = countryService.graphqlCountryByName(countryName);
        return new Country(
                countryGraphql.name(),
                countryGraphql.code()
        );
    }

    @PostMapping("/create")
    public Country createCountry(@RequestBody Country country) {
        CountryGraphql countryGraphql= countryService.createCountryGraphql(new CountryInputGraphql(
                country.name(),
                country.code()
        ));
        return new Country(
                countryGraphql.name(),
                countryGraphql.code()
        );
    }

    @PatchMapping("/update/{id}")
    public Country updateCountry(@PathVariable("id") UUID id, @RequestBody Country country) {
        CountryGraphql countryGraphql= countryService.updateCountryGraphql(id, new CountryInputGraphql(
                country.name(),
                country.code()
        ));
        return new Country(
                countryGraphql.name(),
                countryGraphql.code()
        );
    }
}