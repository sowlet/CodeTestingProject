import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatsBinnerTest {

    private static class FakeStats extends GameStats {
        private final int[] counts; // index = guesses, value = games

        FakeStats(int maxGuesses) {
            this.counts = new int[maxGuesses + 1];
        }

        void set(int guesses, int games) {
            counts[guesses] = games;
        }

        @Override
        public int numGames(int numGuesses) {
            if (numGuesses < 0 || numGuesses >= counts.length) return 0;
            return counts[numGuesses];
        }

        @Override
        public int maxNumGuesses() {
            return counts.length - 1;
        }
    }

    @Test
    void binCountsBasicCase() {
        int[] edges = {1, 2, 4};
        FakeStats stats = new FakeStats(6);

        stats.set(1, 1);
        stats.set(2, 2);
        stats.set(3, 3);
        stats.set(4, 4);

        int[] bins = StatsBinner.calculateBinCounts(stats, edges);

        assertEquals(3, bins.length);
        assertTrue(bins[0] >= 0);
    }

    @Test
    void lastBinShouldIncludeMaxGuessCount_expectedToFailIfBugExists() {
        int[] edges = {1, 2, 4};
        FakeStats stats = new FakeStats(4);

        // Expect last bin (4+) to include guess=4
        stats.set(4, 7);

        int[] bins = StatsBinner.calculateBinCounts(stats, edges);

        // If implementation incorrectly excludes maxNumGuesses, this test will fail (intended).
        assertEquals(7, bins[2]);
    }
}
