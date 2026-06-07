package vacancyaggregator.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import vacancyaggregator.model.Vacancy;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonExporter {

    public void export(List<Vacancy> vacancies, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), vacancies);

            System.out.println("Export completed: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}