package guru.qa.countries.domain.graphql;

import java.util.UUID;

public record CountryInputGraphql(
        String name,
        String code
) {
}