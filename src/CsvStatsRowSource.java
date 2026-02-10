import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads stats rows from a CSV file.
 * Isolates I/O from StatsFile processing logic.
 */
public class CsvStatsRowSource implements StatsRowSource {

    private final String filename;

    public CsvStatsRowSource(String filename) {
        this.filename = filename;
    }

    @Override
    public Iterable<String[]> readRows() throws IOException, CsvValidationException {
        List<String[]> rows = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filename))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                rows.add(values);
            }
        }

        return rows;
    }
}
