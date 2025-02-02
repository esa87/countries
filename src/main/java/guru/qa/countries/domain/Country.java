package guru.qa.countries.domain;

import java.util.UUID;

public record Country(
        String name,
        String code
) {
}