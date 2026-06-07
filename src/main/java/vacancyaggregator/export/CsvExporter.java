package vacancyaggregator.export;

import vacancyaggregator.model.Vacancy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public void export(List<Vacancy> vacancies, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("title,company,city,salary,description,url,publicationDate\n");

            for (Vacancy vacancy : vacancies) {
                writer.write(
                        vacancy.getTitle() + "," +
                                vacancy.getCompany() + "," +
                                vacancy.getCity() + "," +
                                vacancy.getSalary() + "," +
                                vacancy.getDescription() + "," +
                                vacancy.getUrl() + "," +
                                vacancy.getPublicationDate() + "\n"
                );
            }

            System.out.println("Export completed: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}