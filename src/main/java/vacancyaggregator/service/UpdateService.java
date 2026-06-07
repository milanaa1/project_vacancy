package vacancyaggregator.service;

import vacancyaggregator.model.UpdateHistory;
import vacancyaggregator.model.Vacancy;
import vacancyaggregator.parser.HabrCareerParser;
import vacancyaggregator.parser.TheMuseParser;
import vacancyaggregator.parser.TrudVsemParser;
import vacancyaggregator.parser.VacancyParser;
import vacancyaggregator.repository.UpdateHistoryRepository;
import vacancyaggregator.repository.VacancyRepository;

import java.util.List;

public class UpdateService {

    private final VacancyRepository vacancyRepository =
            new VacancyRepository();

    private final UpdateHistoryRepository historyRepository =
            new UpdateHistoryRepository();

    public int updateFromTrudVsem(String searchText) {
        vacancyRepository.deleteAll();
        return updateFromSource("TrudVsem", new TrudVsemParser(), searchText);
    }

    public int updateFromTheMuse(String searchText) {
        vacancyRepository.deleteAll();
        return updateFromSource("TheMuse", new TheMuseParser(), searchText);
    }

    public int updateFromAllSources(String searchText) {
        vacancyRepository.deleteAll();

        int total = 0;

        total += updateFromSource("TrudVsem", new TrudVsemParser(), searchText);
        total += updateFromSource("TheMuse", new TheMuseParser(), searchText);
        total += updateFromSource("HabrCareer", new HabrCareerParser(), searchText);

        return total;
    }

    private int updateFromSource(
            String sourceName,
            VacancyParser parser,
            String searchText
    ) {
        List<Vacancy> vacancies = parser.parse(searchText);

        for (Vacancy vacancy : vacancies) {
            vacancyRepository.save(vacancy);
        }

        historyRepository.save(sourceName, searchText, vacancies.size());

        return vacancies.size();
    }

    public List<UpdateHistory> getUpdateHistory() {
        return historyRepository.findAll();
    }

    public int updateFromHabrCareer(String searchText) {
        vacancyRepository.deleteAll();
        return updateFromSource("HabrCareer", new HabrCareerParser(), searchText);
    }
}