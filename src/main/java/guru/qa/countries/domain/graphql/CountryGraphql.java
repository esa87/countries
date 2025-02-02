package guru.qa.countries.domain.graphql;

import java.util.UUID;

public record CountryGraphql(
        UUID id,
        String name,
        String code
) {
}