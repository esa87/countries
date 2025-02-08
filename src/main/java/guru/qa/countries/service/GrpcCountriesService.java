package guru.qa.countries.service;

import guru.qa.countries.domain.Country;
import guru.qa.countries.domain.graphql.CountryGraphql;
import guru.qa.countries.domain.graphql.CountryInputGraphql;
import guru.qa.grpc.countries.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GrpcCountriesService extends CountriesServiceGrpc.CountriesServiceImplBase {

    private static final Random random = new Random();
    private final CountryService countryService;

    public GrpcCountriesService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void allCountries(EmptyRequest request, StreamObserver<AllCountriesResponse> responseObserver) {
        List<CountryGraphql> countries = countryService.allCountriesGraphql();

        AllCountriesResponse.Builder responseBuilder = AllCountriesResponse.newBuilder();

        for (CountryGraphql country : countries) {
            CountryResponse countryResponse = CountryResponse.newBuilder()
                    .setId(country.id().toString())
                    .setName(country.name())
                    .setCode(country.code())
                    .build();

            responseBuilder.addAllCountries(countryResponse);
        }

        AllCountriesResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void country(NameRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryGraphql country = countryService.graphqlCountryByName(request.getName());
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setName(country.name())
                        .setCode(country.code())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryGraphql country = countryService.createCountryGraphql(new CountryInputGraphql(
                request.getName(),
                request.getCode()
        ));
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(country.id().toString())
                        .setName(country.name())
                        .setCode(country.code())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> addCountries(StreamObserver<CountResponse> responseObserver) {
       return new StreamObserver<CountryRequest>() {

           private int totalCountriesAdded = 0;

           @Override
           public void onNext(CountryRequest countryRequest) {
               totalCountriesAdded++;
           }

           @Override
           public void onError(Throwable throwable) {
               System.err.println("Error in streaming: " + throwable.getMessage());
           }

           @Override
           public void onCompleted() {
               CountResponse response = CountResponse.newBuilder()
                       .setCount(totalCountriesAdded)
                       .build();

               responseObserver.onNext(response);
               responseObserver.onCompleted();
           }
       };


    }


    @Override
    public void updateCountry(UpdateRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryGraphql country = countryService.updateCountryGraphql(UUID.fromString(request.getId()),
                new CountryInputGraphql(
                        request.getCountry().getName(),
                        request.getCountry().getCode()
                ));
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(country.id().toString())
                        .setName(country.name())
                        .setCode(country.code())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void randomCountry(CountRequest request, StreamObserver<CountryResponse> responseObserver) {
        List<CountryGraphql> countries = countryService.allCountriesGraphql();

        for (int i = 0; i < request.getCount(); i++) {
            CountryGraphql randomCountry = countries.get(random.nextInt(countries.size()));
            responseObserver.onNext(
                    CountryResponse.newBuilder()
                            .setId(randomCountry.id().toString())
                            .setName(randomCountry.name())
                            .setCode(randomCountry.code())
                            .build()
            );
        }

        responseObserver.onCompleted();
    }
}
