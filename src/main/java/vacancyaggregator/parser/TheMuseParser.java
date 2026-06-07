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

public class TheMuseParser implements VacancyParser {

    @Override
    public List<Vacancy> parse(String searchText) {
        List<Vacancy> vacancies = new ArrayList<>();

        try {
            String urlString =
                    "https://www.themuse.com/api/public/jobs?page=1";

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
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length() && vacancies.size() < 10; i++) {
                JSONObject item = results.getJSONObject(i);

                String title = item.optString("name", "No title");

                JSONObject companyJson = item.optJSONObject("company");
                String company = companyJson == null
                        ? "The Muse"
                        : companyJson.optString("name", "The Muse");

                JSONArray locations = item.optJSONArray("locations");
                String city = "Remote / Foreign";

                if (locations != null && locations.length() > 0) {
                    city = locations
                            .getJSONObject(0)
                            .optString("name", "Remote / Foreign");
                }

                String description = item
                        .optString("contents", "No description")
                        .replaceAll("<[^>]*>", "");

                Integer salary = null;

                java.util.regex.Pattern pattern =
                        java.util.regex.Pattern.compile("\\$(\\d{1,3}(?:,\\d{3})*)");

                java.util.regex.Matcher matcher =
                        pattern.matcher(description);

                if (matcher.find()) {
                    salary = Integer.parseInt(
                            matcher.group(1).replace(",", "")
                    );
                }


                String urlVacancy = item.optString("refs");

                JSONObject refs = item.optJSONObject("refs");
                if (refs != null) {
                    urlVacancy = refs.optString("landing_page", "https://www.themuse.com/");
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
            System.out.println("The Muse source unavailable: " + e.getMessage());
        }

        return vacancies;
    }
}