package vacancyaggregator.export;

import vacancyaggregator.model.Vacancy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlExporter {

    public void export(List<Vacancy> vacancies, String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("""
                    <html>
                    <head>
                        <title>Vacancies</title>
                    </head>
                    <body>
                    <h1>Vacancies</h1>
                    <table border="1">
                    <tr>
                        <th>Title</th>
                        <th>Company</th>
                        <th>City</th>
                        <th>Salary</th>
                    </tr>
                    """);

            for (Vacancy vacancy : vacancies) {

                writer.write(
                        "<tr>" +
                                "<td>" + vacancy.getTitle() + "</td>" +
                                "<td>" + vacancy.getCompany() + "</td>" +
                                "<td>" + vacancy.getCity() + "</td>" +
                                "<td>" + vacancy.getSalary() + "</td>" +
                                "</tr>"
                );
            }

            writer.write("""
                    </table>
                    </body>
                    </html>
                    """);

            System.out.println("HTML exported");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}