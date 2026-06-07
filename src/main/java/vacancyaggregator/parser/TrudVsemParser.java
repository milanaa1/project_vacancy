package vacancyaggregator.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import vacancyaggregator.model.Vacancy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrudVsemParser implements VacancyParser {

    @Override
    public List<Vacancy> parse(String searchText) {
        List<Vacancy> vacancies = new ArrayList<>();

        try {
            String urlString =
                    "http://opendata.trudvsem.ru/api/v1/vacancies?keyword="
                            + searchText;

            URL url = URI.create(urlString).toURL();

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "VacancyAggregator/1.0");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream(),
                            StandardCharsets.UTF_8
                    )
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JSONObject json = new JSONObject(response.toString());

            JSONArray items = json
                    .getJSONObject("results")
                    .getJSONArray("vacancies");

            for (int i = 0; i < items.length() && vacancies.size() < 10; i++) {
                JSONObject wrapper = items.getJSONObject(i);
                JSONObject item = wrapper.getJSONObject("vacancy");



                String title = item.optString("job-name", "Без названия");
                String company = "Не указано";

                JSONObject companyJson = item.optJSONObject("company");

                if (companyJson != null) {
                    company = companyJson.optString("name", "Не указано");
                }

                JSONObject region = item.optJSONObject("region");
                String city = "Россия";

                if (region != null) {
                    city = region.optString("name", "Россия");
                }

                Integer salary = null;

                if (item.has("salary_min") && !item.isNull("salary_min")) {
                    salary = item.optInt("salary_min");
                } else if (item.has("salary") && !item.isNull("salary")) {
                    salary = item.optInt("salary");
                }

                String description = item.optString("duty", "Описание отсутствует");

                String urlVacancy = item.optString("vac_url");

                if (urlVacancy == null || urlVacancy.isBlank()) {
                    urlVacancy = "https://trudvsem.ru/";
                }

                String text = (title + " " + description).toLowerCase();

                if (!text.contains(searchText.toLowerCase())) {
                    continue;
                }

                vacancies.add(new Vacancy(
                        null,
                        title,
                        company,
                        city,
                        salary,
                        description,
                        urlVacancy,
                        LocalDate.now()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vacancies;
    }
}