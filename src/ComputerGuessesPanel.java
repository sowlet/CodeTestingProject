import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * UI screen for when the computer is guessing a number
 *
 * Displays the computer's guesses and processes human's answers.
 * Delegates guessing logic to ComputerGuessesGame to keep UI simple.
 *
 * TODO: refactor this class
 */
public class ComputerGuessesPanel extends JPanel {

    private final ComputerGuessesGame game;

    public ComputerGuessesPanel(JPanel cardsPanel, Consumer<GameResult> gameFinishedCallback){
        this.game = new ComputerGuessesGame();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel guessMessage = new JLabel("I guess ___.");
        guessMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(guessMessage);
        guessMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel prompt = new JLabel("Your number is...");
        this.add(prompt);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        JButton lowerBtn = new JButton("Lower");
        lowerBtn.addActionListener(e -> {
            game.applyLower();
            processNextGuess(guessMessage);
        });
        this.add(lowerBtn);
        lowerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        JButton correctBtn = new JButton("Equal");
        correctBtn.addActionListener(e -> {
            guessMessage.setText("I guess ___.");

            // Send the result of the finished game to the callback
            GameResult result = new GameResult(false, game.getLastGuess(), game.getNumGuesses());
            gameFinishedCallback.accept(result);

            CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
            cardLayout.show(cardsPanel, ScreenID.GAME_OVER.name());
        });
        this.add(correctBtn);
        correctBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        JButton higherBtn = new JButton("Higher");
        higherBtn.addActionListener(e -> {
            game.applyHigher();
            processNextGuess(guessMessage);
        });
        this.add(higherBtn);
        higherBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                // Reset non-UI game state when this screen is shown
                game.reset();
                int firstGuess = game.initialGuess();
                guessMessage.setText("I guess " + firstGuess + ".");
            }
        });
    }

    /**
     * Process the next guess after clicking the Higher or Lower buttons.
     */
    private void processNextGuess(JLabel guessMessage) {
        int next = game.nextGuess();
        guessMessage.setText("I guess " + next + ".");
    }
}
