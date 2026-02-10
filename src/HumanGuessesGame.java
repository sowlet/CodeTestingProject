import java.util.Random;

/**
 * A game where a human guesses a number between 1 and UPPER_BOUND
 * Tracks the target, the number of guesses made, and if the number has been guessed
 *
 * NOTE: You can refactor and edit this file if needed
 */
public class HumanGuessesGame {
    public final static int UPPER_BOUND = 1000;

    private final int target;
    private int numGuesses;
    private boolean gameIsDone; // true iff makeGuess has been called with the target value

    /**
     * Default constructor.
     * Preserves original behavior by using a new Random().
     */
    public HumanGuessesGame() {
        this(new Random());
    }

    /**
     * Dependency-injection constructor for deterministic testing.
     */
    public HumanGuessesGame(Random rng){
        this.target = rng.nextInt(UPPER_BOUND) + 1;
        numGuesses = 0;
        gameIsDone = false;  // bug intentionally preserved
    }

    GuessResult makeGuess(int value){
        numGuesses += 1;

        if(value < target){
            return GuessResult.LOW;
        }
        if(value > target){
            return GuessResult.HIGH;
        }

        // BUG: gameIsDone should be set here, but is not.
        return GuessResult.CORRECT;
    }

    int getNumGuesses(){
        return numGuesses;
    }

    boolean isDone(){
        return gameIsDone; // bug preserved
    }

    // Helper for deterministic tests
    int getTargetForTesting() {
        return target;
    }
}
