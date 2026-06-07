package vacancyaggregator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateHistoryTest {

    @Test
    void updateHistoryShouldStoreFields() {
        UpdateHistory history = new UpdateHistory(
                "TrudVsem",
                "продавец",
                10,
                "2026-06-06T15:00:00"
        );

        assertEquals("TrudVsem", history.getSourceName());
        assertEquals("продавец", history.getSearchText());
        assertEquals(10, history.getAddedCount());
        assertEquals("2026-06-06T15:00:00", history.getUpdateDate());
    }

    @Test
    void toStringShouldContainSourceAndSearchText() {
        UpdateHistory history = new UpdateHistory(
                "TrudVsem",
                "оператор",
                5,
                "2026-06-06T15:00:00"
        );

        String result = history.toString();

        assertTrue(result.contains("TrudVsem"));
        assertTrue(result.contains("оператор"));
    }
}