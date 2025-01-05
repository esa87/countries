package guru.qa.countries.controller;

import guru.qa.countries.domain.Country;
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
        return countryService.allCountries();
    }

    @GetMapping("/current")
    public Country currentCountry(@RequestParam String countryName) {
        return countryService.getCountryByName(countryName);
    }

    @PostMapping("/create")
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @PatchMapping("/update/{id}")
    public Country updateCountry(@PathVariable("id") UUID id, @RequestBody Country country) {
        return countryService.updateCountry(id, country);
    }
}
