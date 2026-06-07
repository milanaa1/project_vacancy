package vacancyaggregator.model;

import java.time.LocalDate;

public class Vacancy {

    private Long id;
    private String title;
    private String company;
    private String city;
    private Integer salary;
    private String description;
    private String url;
    private LocalDate publicationDate;

    public Vacancy() {}

    public Vacancy(Long id,
                   String title,
                   String company,
                   String city,
                   Integer salary,
                   String description,
                   String url,
                   LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.city = city;
        this.salary = salary;
        this.description = description;
        this.url = url;
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Integer getSalary() {
        return salary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        String shortDescription = description;

        if (shortDescription != null && shortDescription.length() > 250) {
            shortDescription = shortDescription.substring(0, 250) + "...";
        }

        return "\nVacancy:" +
                "\nTitle: " + title +
                "\nCompany: " + company +
                "\nCity: " + city +
                "\nSalary: " + (salary == null ? "Не указана" : salary) +
                "\nDescription: " + shortDescription +
                "\nUrl: " + url +
                "\nPublication date: " + publicationDate +
                "\n";
    }
}