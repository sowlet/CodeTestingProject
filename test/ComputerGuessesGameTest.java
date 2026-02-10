import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComputerGuessesGameTest {

    @Test
    void initialGuessDoesNotIncrementCount() {
        ComputerGuessesGame game = new ComputerGuessesGame();
        game.reset();

        int guess = game.initialGuess();
        assertTrue(guess >= 1);
        assertEquals(0, game.getNumGuesses());
    }

    @Test
    void nextGuessIncrementsCount() {
        ComputerGuessesGame game = new ComputerGuessesGame();
        game.reset();
        game.initialGuess();

        int g2 = game.nextGuess();
        assertTrue(g2 >= 1);
        assertEquals(1, game.getNumGuesses());
    }

    @Test
    void higherThenLowerProducesValidRangeGuess() {
        ComputerGuessesGame game = new ComputerGuessesGame();
        game.reset();

        int first = game.initialGuess();
        game.applyHigher();
        int second = game.nextGuess();

        assertTrue(second >= first);

        game.applyLower();
        int third = game.nextGuess();
        assertTrue(third <= second);
    }
}
