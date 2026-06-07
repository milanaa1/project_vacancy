package vacancyaggregator.service;

import vacancyaggregator.model.Vacancy;
import vacancyaggregator.repository.VacancyRepository;

import java.util.List;

public class VacancyService {

    private final VacancyRepository vacancyRepository = new VacancyRepository();

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }
    public List<Vacancy> search(String keyword) {
        return vacancyRepository.searchByKeyword(keyword);
    }
    public List<Vacancy> findByCity(String city) {
        return vacancyRepository.findByCity(city);
    }
    public List<Vacancy> findByMinSalary(int minSalary) {
        return vacancyRepository.findByMinSalary(minSalary);
    }

    public List<Vacancy> sortBySalaryDesc() {
        return vacancyRepository.sortBySalaryDesc();
    }

    public List<Vacancy> findByCompany(String company) {
        return vacancyRepository.findByCompany(company);
    }

    public List<Vacancy> sortByDateDesc() {
        return vacancyRepository.sortByDateDesc();
    }

    public List<Vacancy> sortByCompany() {
        return vacancyRepository.sortByCompany();
    }
}