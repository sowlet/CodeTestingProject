import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * File-backed implementation of GameStats
 *
 * Returns the number of games *within the last 30 days* where the person took a given number of guesses
 */
public class StatsFile extends GameStats {
    public static final String FILENAME = "guess-the-number-stats.csv";

    // maps number of guesses -> number of games within the past 30 days
    private final SortedMap<Integer, Integer> statsMap;

    /**
     * Default constructor uses the real CSV file and the current time.
     */
    public StatsFile() {
        this(new CsvStatsRowSource(FILENAME), LocalDateTime.now());
    }

    /**
     * Injectable constructor for unit tests (no file required).
     */
    public StatsFile(StatsRowSource rowSource, LocalDateTime now) {
        statsMap = new TreeMap<>();
        LocalDateTime limit = now.minusDays(30);

        try {
            for (String[] values : rowSource.readRows()) {
                // values should have the date and the number of guesses as the two fields
                processStatsRow(values, limit);
            }
        } catch (CsvValidationException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        } catch (IOException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        } catch (Exception e) {
            // Keep behavior conservative: ignore unexpected row-source failures here.
            // Unit tests can directly test processStatsRow for formatting exceptions.
        }
    }

    /**
     * Processes a single row from the stats file.
     * Leaves formatting exceptions unhandled, as in the starter code.
     */
    void processStatsRow(String[] values, LocalDateTime limit) {
        try {
            LocalDateTime timestamp = LocalDateTime.parse(values[0]);
            int numGuesses = Integer.parseInt(values[1]);

            if (timestamp.isAfter(limit)) {
                statsMap.put(numGuesses, 1 + statsMap.getOrDefault(numGuesses, 0));
            }
        }
        catch(NumberFormatException nfe){
            // NOTE: In a full implementation, we would log this error and possibly alert the user
            throw nfe;
        }
        catch(DateTimeParseException dtpe){
            // NOTE: In a full implementation, we would log this error and possibly alert the user
            throw dtpe;
        }
    }

    @Override
    public int numGames(int numGuesses) {
        return statsMap.getOrDefault(numGuesses, 0);
    }

    @Override
    public int maxNumGuesses(){
        return (statsMap.isEmpty() ? 0 : statsMap.lastKey());
    }

    /**
     * Writes a game result to the stats file if the human was playing.
     * NOTE: Not required to unit test.
     */
    public static void writeGameResult(GameResult result) {
        if(result.humanWasPlaying){
            try(CSVWriter writer = new CSVWriter(new FileWriter(FILENAME, true))) {
                String [] record = new String[2];
                record[0] = LocalDateTime.now().toString();
                record[1] = Integer.toString(result.numGuesses);
                writer.writeNext(record);
            } catch (IOException e) {
                // NOTE: In a full implementation, we would log this error and possibly alert the user
                // NOTE: For this project, you do not need unit tests for handling this exception.
            }
        }
    }
}
