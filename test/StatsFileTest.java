import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatsFileTest {

    private static class FakeRowSource implements StatsRowSource {
        private final List<String[]> rows;

        FakeRowSource(List<String[]> rows) {
            this.rows = rows;
        }

        @Override
        public Iterable<String[]> readRows() {
            return rows;
        }
    }

    @Test
    void countsOnlyRowsWithinLast30Days() {
        LocalDateTime now = LocalDateTime.of(2026, 2, 10, 12, 0);
        LocalDateTime within = now.minusDays(5);
        LocalDateTime outside = now.minusDays(45);

        List<String[]> rows = Arrays.asList(
                new String[]{within.toString(), "3"},
                new String[]{within.toString(), "3"},
                new String[]{outside.toString(), "3"},
                new String[]{within.toString(), "10"}
        );

        //using dependency injection
        StatsFile stats = new StatsFile(new FakeRowSource(rows), now);

        assertEquals(2, stats.numGames(3));
        assertEquals(1, stats.numGames(10));
        assertEquals(10, stats.maxNumGuesses());
    }

    @Test
    void malformedGuessCountThrowsNumberFormatException() {
        StatsFile stats = new StatsFile(new FakeRowSource(List.of()), LocalDateTime.now());
        assertThrows(NumberFormatException.class,
                () -> stats.processStatsRow(new String[]{LocalDateTime.now().toString(), "not-a-number"},
                        LocalDateTime.now().minusDays(30)));
    }

    @Test
    void malformedTimestampThrowsDateTimeParseException() {
        StatsFile stats = new StatsFile(new FakeRowSource(List.of()), LocalDateTime.now());
        assertThrows(java.time.format.DateTimeParseException.class,
                () -> stats.processStatsRow(new String[]{"not-a-timestamp", "5"},
                        LocalDateTime.now().minusDays(30)));
    }
}
