package guru.qa.countries.service.soap;

import guru.qa.countries.config.AppConfig;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;
import guru.qa.countries.service.CountryService;
import guru.qa.xml.countries.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.UUID;

@Endpoint
public class CountryEndpoint {

    private final CountryService countryService;

    public CountryEndpoint(CountryService countryService) {
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

    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "countriesRequest")
    @ResponsePayload
    public CountriesResponse countries(@RequestPayload CountriesRequest request) {
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


    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "countryInputRequest")
    @ResponsePayload
    public CountryResponse addCountry(@RequestPayload CountryInputRequest request) {
        CountryGraphql country = countryService.createCountryGraphql(new CountryInputGraphql(
                request.getCountry().getName(),
                request.getCountry().getCode()
        ));
        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.name());
        xmlCountry.setCode(country.code());
        response.setCountry(xmlCountry);
        return response;
    }


    @PayloadRoot(namespace = AppConfig.SOAP_NAMESPACE, localPart = "countryUpdateRequest")
    @ResponsePayload
    public CountryResponse updateCountry(@RequestPayload CountryUpdateRequest request) {
        CountryGraphql country = countryService.updateCountryGraphql(
                UUID.fromString(request.getId()),
                new CountryInputGraphql(
                        request.getCountry().getName(),
                        request.getCountry().getCode()
                )
        );

        CountryResponse response = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.name());
        xmlCountry.setCode(country.code());
        response.setCountry(xmlCountry);
        return response;
    }

}
