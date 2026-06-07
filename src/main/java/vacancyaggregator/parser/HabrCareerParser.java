package vacancyaggregator.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vacancyaggregator.model.Vacancy;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParser implements VacancyParser {

    @Override
    public List<Vacancy> parse(String searchText) {
        List<Vacancy> vacancies = new ArrayList<>();

        try {
            String query = URLEncoder.encode(searchText, StandardCharsets.UTF_8);

            Document doc = Jsoup.connect("https://career.habr.com/vacancies?q=" + query)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements cards = doc.select(".vacancy-card");

            for (Element card : cards) {
                if (vacancies.size() >= 10) {
                    break;
                }

                String title = card.select(".vacancy-card__title a").text();

                if (title == null || title.isBlank()) {
                    continue;
                }

                String company = card.select(".vacancy-card__company-title").text();

                if (company == null || company.isBlank()) {
                    company = "Habr Career";
                }

                String city = card.select(".vacancy-card__meta").text();

                if (city == null || city.isBlank()) {
                    city = "Не указано";
                }

                String url = card.select(".vacancy-card__title a").attr("abs:href");

                String description = "Вакансия загружена с Habr Career";

                vacancies.add(new Vacancy(
                        null,
                        title,
                        company,
                        city,
                        null,
                        description,
                        url,
                        LocalDate.now()
                ));
            }

        } catch (Exception e) {
            System.out.println("Habr Career source unavailable: " + e.getMessage());
        }

        return vacancies;
    }
}