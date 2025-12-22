package com.game.gameguessnumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class GameIntegrationTest {

    static class GameSession {
        private int targetNumber;
        private int attempts;
        private boolean gameWon;

        public void startNewGame(int targetNumber) {
            this.targetNumber = targetNumber;
            this.attempts = 0;
            this.gameWon = false;
        }

        public GameResult makeGuess(int guess) {
            attempts++;

            if (guess == targetNumber) {
                gameWon = true;
                return new GameResult(true,
                        "🎉 Поздравляем! Вы угадали число " + targetNumber + " за " + attempts + " попыток!",
                        attempts);
            } else if (guess < targetNumber) {
                return new GameResult(false, "Мое число БОЛЬШЕ чем " + guess, attempts);
            } else {
                return new GameResult(false, "Мое число МЕНЬШЕ чем " + guess, attempts);
            }
        }

        public boolean isGameWon() {
            return gameWon;
        }

        public int getAttempts() {
            return attempts;
        }
    }

    static class GameResult {
        boolean success;
        String message;
        int attempts;

        public GameResult(boolean success, String message, int attempts) {
            this.success = success;
            this.message = message;
            this.attempts = attempts;
        }
    }

    private GameSession session;

    @BeforeEach
    void setUp() {
        session = new GameSession();
    }

    @Test
    void testCompleteGameFlow() {
        session.startNewGame(42);

        GameResult result1 = session.makeGuess(10);
        assertFalse(result1.success);
        assertEquals("Мое число БОЛЬШЕ чем 10", result1.message);
        assertEquals(1, result1.attempts);

        GameResult result2 = session.makeGuess(50);
        assertFalse(result2.success);
        assertEquals("Мое число МЕНЬШЕ чем 50", result2.message);
        assertEquals(2, result2.attempts);

        GameResult result3 = session.makeGuess(42);
        assertTrue(result3.success);
        assertTrue(result3.message.contains("🎉 Поздравляем!"));
        assertTrue(result3.message.contains("42"));
        assertTrue(result3.message.contains("3"));
        assertEquals(3, result3.attempts);

        assertTrue(session.isGameWon());
        assertEquals(3, session.getAttempts());
    }

    @Test
    void testGameWithMultipleWrongGuesses() {
        session.startNewGame(77);

        int[] guesses = {50, 60, 70, 80, 90, 77};
        int expectedAttempts = 0;

        for (int guess : guesses) {
            expectedAttempts++;
            GameResult result = session.makeGuess(guess);

            if (guess == 77) {
                assertTrue(result.success);
                assertEquals(expectedAttempts, result.attempts);
            } else {
                assertFalse(result.success);
                assertEquals(expectedAttempts, result.attempts);
            }
        }

        assertTrue(session.isGameWon());
        assertEquals(6, session.getAttempts());
    }

}
