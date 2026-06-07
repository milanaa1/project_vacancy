package vacancyaggregator.parser;

import vacancyaggregator.model.Vacancy;

import java.util.List;

public interface VacancyParser {

    List<Vacancy> parse(String searchText);
}