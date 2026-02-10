/**
 * Abstraction for providing stats rows (e.g., from a file).
 * Allows unit tests to inject fake row sources without I/O.
 */
public interface StatsRowSource {
    Iterable<String[]> readRows() throws Exception;
}
