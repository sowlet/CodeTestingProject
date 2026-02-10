import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class HumanGuessesGameTest {

    @Test
    void makeGuessIncrementsGuessCount() {
        Random rng = new Random(0); // deterministic
        HumanGuessesGame game = new HumanGuessesGame(rng);

        game.makeGuess(1);
        game.makeGuess(2);

        assertEquals(2, game.getNumGuesses());
    }

    @Test
    void correctGuessShouldSetGameDone_expectedToFailDueToBug() {
        Random rng = new Random(0); // deterministic target
        HumanGuessesGame game = new HumanGuessesGame(rng);

        int target = game.getTargetForTesting();
        GuessResult result = game.makeGuess(target);

        assertEquals(GuessResult.CORRECT, result);

        // This SHOULD be true if the game were correct…
        // But because of the bug (gameIsDone never set), this will fail.
        assertTrue(game.isDone());
    }
}
