/**
 * Non-UI game logic for when the computer guesses the human's number.
 * Keeps the same behavior as the UI-driven version (including quirks).
 */
public class ComputerGuessesGame {

    private int numGuesses;
    private int lastGuess;

    // correct number is <= upperBound, >= lowerBound
    private int upperBound;
    private int lowerBound;

    public ComputerGuessesGame() {
        reset();
    }

    /**
     * Resets the game state to initial values.
     */
    public void reset() {
        numGuesses = 0;
        upperBound = HumanGuessesGame.UPPER_BOUND;
        lowerBound = 1;
        lastGuess = 0;
    }

    /**
     * Applies a "lower" response from the human.
     */
    public void applyLower() {
        upperBound = Math.min(upperBound, lastGuess);
    }

    /**
     * Applies a "higher" response from the human.
     */
    public void applyHigher() {
        lowerBound = Math.max(lowerBound, lastGuess + 1);
    }

    /**
     * Advances to the next guess and returns it.
     * Note: This preserves the original behavior: numGuesses increments
     * only when moving to the next guess via higher/lower, not on initial guess.
     */
    public int nextGuess() {
        lastGuess = calculateNextGuess();
        numGuesses += 1;
        return lastGuess;
    }

    /**
     * Returns the initial guess without incrementing the guess count.
     * This matches the UI's componentShown behavior.
     */
    public int initialGuess() {
        lastGuess = calculateNextGuess();
        return lastGuess;
    }

    /**
     * Binary search midpoint calculation.
     */
    private int calculateNextGuess() {
        return (lowerBound + upperBound + 1) / 2;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public int getLastGuess() {
        return lastGuess;
    }
}
