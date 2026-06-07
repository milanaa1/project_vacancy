package vacancyaggregator.model;

public class UpdateHistory {

    private String sourceName;
    private String searchText;
    private int addedCount;
    private String updateDate;

    public UpdateHistory(String sourceName, String searchText, int addedCount, String updateDate) {
        this.sourceName = sourceName;
        this.searchText = searchText;
        this.addedCount = addedCount;
        this.updateDate = updateDate;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSearchText() {
        return searchText;
    }

    public int getAddedCount() {
        return addedCount;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    @Override
    public String toString() {
        return "\nИсточник: " + sourceName +
                "\nЗапрос: " + searchText +
                "\nДобавлено вакансий: " + addedCount +
                "\nДата обновления: " + updateDate +
                "\n";
    }
}