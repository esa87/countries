package guru.qa.countries.controller.graphql;

import guru.qa.countries.domain.Country;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;
import guru.qa.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Controller
public class CountryMutationController {

    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGraphql addCountry(@Argument CountryInputGraphql input) {
        return countryService.createCountryGraphql(input);
    }

    @MutationMapping
    public CountryGraphql updateCountry(@Argument UUID id, @Argument CountryInputGraphql input) {
        return countryService.updateCountryGraphql(id, input);
    }
}