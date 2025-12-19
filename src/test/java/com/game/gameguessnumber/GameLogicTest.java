package com.game.gameguessnumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {
    static class SimpleGame {
        private int targetNumber;
        private int attempts;

        public void startNewGame(int targetNumber) {
            this.targetNumber = targetNumber;
            this.attempts = 0;
        }

        public String checkGuess(int guess) {
            attempts++;

            if (guess < 1 || guess > 100) {
                return "Ошибка: число должно быть от 1 до 100";
            }

            if (guess == targetNumber) {
                return "Поздравляем! Вы угадали число " + targetNumber + " за " + attempts + " попыток!";
            } else if (guess < targetNumber) {
                return "Больше";
            } else {
                return "Меньше";
            }
        }

        public int getAttempts() {
            return attempts;
        }
    }

    private SimpleGame game;

    @BeforeEach
    void setUp() {
        game = new SimpleGame();
    }

    @Test
    void testCorrectGuess() {
        // Подготовка
        game.startNewGame(42);

        // Действие
        String result = game.checkGuess(42);

        // Проверка
        assertEquals("Поздравляем! Вы угадали число 42 за 1 попыток!", result);
        assertEquals(1, game.getAttempts());
    }
}