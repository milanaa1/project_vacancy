package vacancyaggregator.service;

import org.junit.jupiter.api.Test;
import vacancyaggregator.model.Vacancy;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VacancyServiceTest {

    @Test
    void vacancyShouldStoreMainFields() {
        Vacancy vacancy = new Vacancy(
                null,
                "Java Developer",
                "Test Company",
                "Москва",
                150000,
                "Java, SQL",
                "https://example.com",
                LocalDate.now()
        );

        assertEquals("Java Developer", vacancy.getTitle());
        assertEquals("Test Company", vacancy.getCompany());
        assertEquals("Москва", vacancy.getCity());
        assertEquals(150000, vacancy.getSalary());
    }

    @Test
    void vacancyWithoutSalaryShouldHaveNullSalary() {
        Vacancy vacancy = new Vacancy(
                null,
                "Продавец",
                "Ашан",
                "Москва",
                null,
                "Описание",
                "https://example.com",
                LocalDate.now()
        );

        assertNull(vacancy.getSalary());
    }

    @Test
    void vacancyToStringShouldContainTitleAndCompany() {
        Vacancy vacancy = new Vacancy(
                null,
                "Оператор",
                "Магнит",
                "Казань",
                null,
                "Описание",
                "https://example.com",
                LocalDate.now()
        );

        String result = vacancy.toString();

        assertTrue(result.contains("Оператор"));
        assertTrue(result.contains("Магнит"));
        assertTrue(result.contains("Не указана"));
    }
}