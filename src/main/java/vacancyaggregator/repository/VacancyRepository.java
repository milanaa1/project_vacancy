package vacancyaggregator.repository;

import vacancyaggregator.database.DatabaseManager;
import vacancyaggregator.model.Vacancy;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VacancyRepository {

    public void save(Vacancy vacancy) {
        String sql = """
                INSERT OR IGNORE INTO vacancies
                (title, company, city, salary, description, url, publication_date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vacancy.getTitle());
            statement.setString(2, vacancy.getCompany());
            statement.setString(3, vacancy.getCity());

            if (vacancy.getSalary() == null) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, vacancy.getSalary());
            }

            statement.setString(5, vacancy.getDescription());
            statement.setString(6, vacancy.getUrl());
            statement.setString(7, vacancy.getPublicationDate().toString());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vacancy> findAll() {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = "SELECT * FROM vacancies ORDER BY publication_date DESC";

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null
                                ? null
                                : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
    public List<Vacancy> searchByKeyword(String keyword) {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vacancies
            WHERE LOWER(title) LIKE LOWER(?)
               OR LOWER(description) LIKE LOWER(?)
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";

            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null ? null : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
    public List<Vacancy> findByCity(String city) {

        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vacancies
            WHERE LOWER(city) = LOWER(?)
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, city);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null
                                ? null
                                : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
    public List<Vacancy> findByMinSalary(int minSalary) {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vacancies
            WHERE salary >= ?
            ORDER BY salary DESC
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, minSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null ? null : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }

    public List<Vacancy> sortBySalaryDesc() {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
        SELECT *
        FROM vacancies
        WHERE salary IS NOT NULL AND salary >= ?
        ORDER BY salary DESC
        """;

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null ? null : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
    public List<Vacancy> findByCompany(String company) {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vacancies
            WHERE LOWER(company) = LOWER(?)
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, company);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null ? null : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }

    public List<Vacancy> sortByDateDesc() {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vacancies
            ORDER BY publication_date DESC
            """;

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Vacancy vacancy = new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getObject("salary") == null ? null : resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(resultSet.getString("publication_date"))
                );

                vacancies.add(vacancy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }

    public List<Vacancy> sortByCompany() {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vacancies
            ORDER BY company ASC
            """;

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {

                vacancies.add(new Vacancy(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("company"),
                        resultSet.getString("city"),
                        resultSet.getInt("salary"),
                        resultSet.getString("description"),
                        resultSet.getString("url"),
                        LocalDate.parse(
                                resultSet.getString("publication_date")
                        )
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacancies;
    }

    public void deleteAll() {
        String sql = "DELETE FROM vacancies";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}