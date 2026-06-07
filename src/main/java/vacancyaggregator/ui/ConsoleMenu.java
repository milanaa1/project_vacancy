package vacancyaggregator.ui;

import vacancyaggregator.analytics.AnalyticsService;
import vacancyaggregator.export.CsvExporter;
import vacancyaggregator.export.HtmlExporter;
import vacancyaggregator.export.JsonExporter;
import vacancyaggregator.model.UpdateHistory;
import vacancyaggregator.model.Vacancy;
import vacancyaggregator.service.AutoUpdateService;
import vacancyaggregator.service.UpdateService;
import vacancyaggregator.service.VacancyService;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final VacancyService vacancyService = new VacancyService();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Update vacancies from source");
            System.out.println("2. Show vacancies");
            System.out.println("3. Search");
            System.out.println("4. Filter by city");
            System.out.println("5. Filter by salary");
            System.out.println("6. Statistics");
            System.out.println("7. Export CSV");
            System.out.println("8. Sort by salary");
            System.out.println("9. Filter by company");
            System.out.println("10. Sort by date");
            System.out.println("11. Export JSON");
            System.out.println("12. Export HTML");
            System.out.println("13. Sort by company");
            System.out.println("14. Show update history");
            System.out.println("15. Start auto update");
            System.out.println("16. Stop auto update");
            System.out.println("0. Exit");

            String command = scanner.nextLine();

            switch (command) {
                case "1" -> updateVacancies(scanner);
                case "2" -> showVacancies();
                case "3" -> search(scanner);
                case "4" -> filterByCity(scanner);
                case "5" -> filterBySalary(scanner);
                case "6" -> showStatistics();
                case "7" -> exportCsv();
                case "8" -> sortBySalary();
                case "9" -> filterByCompany(scanner);
                case "10" -> sortByDate();
                case "11" -> exportJson();
                case "12" -> exportHtml();
                case "13" -> sortByCompany();
                case "14" -> showUpdateHistory();
                case "15" -> startAutoUpdate(scanner);
                case "16" -> autoUpdateService.stopAutoUpdate();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Unknown command");
            }
        }
    }

    private void showVacancies() {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();

        if (vacancies.isEmpty()) {
            System.out.println("No vacancies found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }
    private void search(Scanner scanner) {

        System.out.print("Enter keyword: ");

        String keyword = scanner.nextLine();

        List<Vacancy> vacancies =
                vacancyService.search(keyword);

        if (vacancies.isEmpty()) {
            System.out.println("Nothing found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }
    private void filterByCity(Scanner scanner) {

        System.out.print("Enter city: ");

        String city = scanner.nextLine();

        List<Vacancy> vacancies =
                vacancyService.findByCity(city);

        if (vacancies.isEmpty()) {
            System.out.println("Nothing found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }
    private void filterBySalary(Scanner scanner) {
        System.out.print("Enter minimum salary: ");

        int minSalary = Integer.parseInt(scanner.nextLine());

        List<Vacancy> vacancies = vacancyService.findByMinSalary(minSalary);

        if (vacancies.isEmpty()) {
            System.out.println("Nothing found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }

    private final AnalyticsService analyticsService = new AnalyticsService();

    private void showStatistics() {
        System.out.println("Total vacancies: " + analyticsService.getVacancyCount());
        System.out.println("Average salary: " + analyticsService.getAverageSalary());
        System.out.println("Max salary: " + analyticsService.getMaxSalary());
        System.out.println("\nVacancies by city:");

        analyticsService.getVacanciesByCity()
                .forEach((city, count) ->
                        System.out.println(city + ": " + count));
    }

    private final CsvExporter csvExporter = new CsvExporter();

    private void exportCsv() {
        csvExporter.export(
                vacancyService.getAllVacancies(),
                "vacancies.csv"
        );
    }

    private final UpdateService updateService = new UpdateService();

    private void updateVacancies(Scanner scanner) {
        System.out.println("Choose source:");
        System.out.println("1. TrudVsem");
        System.out.println("2. The Muse");
        System.out.println("3. Habr Career");
        System.out.println("4. All sources");

        String source = scanner.nextLine();

        System.out.print("Enter search text: ");
        String searchText = scanner.nextLine();

        int count;

        switch (source) {
            case "1" -> count = updateService.updateFromTrudVsem(searchText);
            case "2" -> count = updateService.updateFromTheMuse(searchText);
            case "3" -> count = updateService.updateFromHabrCareer(searchText);
            case "4" -> count = updateService.updateFromAllSources(searchText);
            default -> {
                System.out.println("Unknown source");
                return;
            }
        }

        System.out.println("Loaded vacancies: " + count);
    }

    private void sortBySalary() {
        List<Vacancy> vacancies = vacancyService.sortBySalaryDesc();

        if (vacancies.isEmpty()) {
            System.out.println("No vacancies found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }
    private void filterByCompany(Scanner scanner) {
        System.out.print("Enter company: ");

        String company = scanner.nextLine();

        List<Vacancy> vacancies = vacancyService.findByCompany(company);

        if (vacancies.isEmpty()) {
            System.out.println("Nothing found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }

    private void sortByDate() {
        List<Vacancy> vacancies =
                vacancyService.sortByDateDesc();

        if (vacancies.isEmpty()) {
            System.out.println("No vacancies found");
            return;
        }

        for (Vacancy vacancy : vacancies) {
            System.out.println(vacancy);
        }
    }

    private final JsonExporter jsonExporter = new JsonExporter();

    private void exportJson() {
        jsonExporter.export(
                vacancyService.getAllVacancies(),
                "vacancies.json"
        );
    }

    private final HtmlExporter htmlExporter = new HtmlExporter();

    private void exportHtml() {
        htmlExporter.export(
                vacancyService.getAllVacancies(),
                "vacancies.html"
        );
    }

    private void sortByCompany() {

        List<Vacancy> vacancies =
                vacancyService.sortByCompany();

        vacancies.forEach(System.out::println);
    }

    private void showUpdateHistory() {
        List<UpdateHistory> history = updateService.getUpdateHistory();

        if (history.isEmpty()) {
            System.out.println("Update history is empty");
            return;
        }

        for (UpdateHistory item : history) {
            System.out.println(item);
        }
    }

    private final AutoUpdateService autoUpdateService =
            new AutoUpdateService();

    private void startAutoUpdate(Scanner scanner) {

        System.out.print("Enter search text: ");
        String searchText = scanner.nextLine();

        System.out.print("Enter interval in seconds: ");
        int interval = Integer.parseInt(scanner.nextLine());

        autoUpdateService.startAutoUpdate(
                searchText,
                interval
        );
    }
}