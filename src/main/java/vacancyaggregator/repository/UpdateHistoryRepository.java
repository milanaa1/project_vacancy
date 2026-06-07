package vacancyaggregator.repository;

import vacancyaggregator.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class UpdateHistoryRepository {

    public void save(String sourceName, String searchText, int addedCount) {
        String sql = """
                INSERT INTO update_history
                (source_name, search_text, added_count, update_date)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, sourceName);
            statement.setString(2, searchText);
            statement.setInt(3, addedCount);
            statement.setString(4, LocalDateTime.now().toString());

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}