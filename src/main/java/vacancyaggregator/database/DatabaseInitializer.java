package vacancyaggregator.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String sql = """
                CREATE TABLE IF NOT EXISTS vacancies(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    company TEXT,
                    city TEXT,
                    salary INTEGER,
                    description TEXT,
                    url TEXT UNIQUE,
                    publication_date TEXT
                );
                """;
        String historySql = """
        CREATE TABLE IF NOT EXISTS update_history(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            source_name TEXT,
            search_text TEXT,
            added_count INTEGER,
            update_date TEXT
        );
        """;

        try (
                Connection connection = DatabaseManager.getConnection();

                Statement statement = connection.createStatement()
        ) {
            statement.execute(sql);
            statement.execute(historySql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}