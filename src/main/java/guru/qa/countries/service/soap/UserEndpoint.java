package guru.qa.countries.service.soap;

import guru.qa.countries.config.AppConfig;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.service.CountryService;
import guru.qa.xml.countries.*;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class UserEndpoint {

    private final CountryService countryService;

    public UserEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "nameRequest")
    @ResponsePayload
    public CountryResponse country(@RequestPayload NameRequest request) {
        CountryGraphql country = countryService.graphqlCountryByName(request.getName());

        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.name());
        xmlCountry.setCode(country.code());
        response.setCountry(xmlCountry);
        return response;
    }

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "countryRequest")
    @ResponsePayload
    public CountriesResponse countries(@RequestPayload CountryRequest request) {
        List<CountryGraphql> countries = countryService.allCountriesGraphql();

        CountriesResponse response = new CountriesResponse();
        response.getCountries().addAll(
                countries.stream()
                        .map(x -> {
                            Country xmlCountry = new Country();
                            xmlCountry.setId(x.id().toString());
                            xmlCountry.setName(x.name());
                            xmlCountry.setCode(x.code());
                            return xmlCountry;
                        }).toList()
        );

        return response;
    }

}
