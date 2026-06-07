package vacancyaggregator.analytics;

import vacancyaggregator.model.Vacancy;
import vacancyaggregator.repository.VacancyRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsService {

    private final VacancyRepository repository =
            new VacancyRepository();

    public int getVacancyCount() {
        return repository.findAll().size();
    }

    public double getAverageSalary() {

        List<Vacancy> vacancies = repository.findAll();

        return vacancies.stream()
                .filter(v -> v.getSalary() != null)
                .mapToInt(Vacancy::getSalary)
                .average()
                .orElse(0);
    }

    public int getMaxSalary() {

        List<Vacancy> vacancies = repository.findAll();

        return vacancies.stream()
                .filter(v -> v.getSalary() != null)
                .mapToInt(Vacancy::getSalary)
                .max()
                .orElse(0);
    }

    public Map<String, Long> getVacanciesByCity() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Vacancy::getCity,
                        Collectors.counting()
                ));
    }
}