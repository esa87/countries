package guru.qa.countries;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.countries.data.CountryEntity;
import guru.qa.countries.data.CountryRepository;
import guru.qa.countries.domain.Country;
import guru.qa.countries.service.DbCountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CountriesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void allCountryEndpoint() throws Exception {
        CountryEntity ce = new CountryEntity();
        ce.setCountryName("Testovo");
        ce.setCountryCode("TV");
        countryRepository.save(ce);
        mockMvc.perform(get("/api/countries/all")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Testovo"));
    }

    @Test
    void currentCountryEndpoint() throws Exception {
        CountryEntity ce = new CountryEntity();
        ce.setCountryName("Testovo");
        ce.setCountryCode("TV");
        countryRepository.save(ce);
        mockMvc.perform(get("/api/countries/current")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("countryName", "Testovo")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Testovo"));
    }


    @Test
    void addCountry() throws Exception {
        Country country = new Country("Testovia", "TS");
        mockMvc.perform(post("/api/countries/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(country))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Testovia"));
    }

    @Test
    void editCountry() throws Exception {
        CountryEntity ce = new CountryEntity();
        ce.setCountryName("Testovo");
        ce.setCountryCode("TV");
        countryRepository.save(ce);
        Country country = new Country(
                ce.getCountryName(),
                "TK"
        );
        ce.setId(countryRepository.findByCountryName(country.name()).get().getId());
        mockMvc.perform(patch("/api/countries/update/" + ce.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(country))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("TK"));
    }
}