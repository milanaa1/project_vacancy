package vacancyaggregator.repository;

import vacancyaggregator.database.DatabaseManager;
import vacancyaggregator.model.UpdateHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<UpdateHistory> findAll() {

        List<UpdateHistory> history = new ArrayList<>();

        String sql = """
            SELECT *
            FROM update_history
            ORDER BY update_date DESC
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                history.add(new UpdateHistory(
                        resultSet.getString("source_name"),
                        resultSet.getString("search_text"),
                        resultSet.getInt("added_count"),
                        resultSet.getString("update_date")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return history;
    }
}