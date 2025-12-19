package com.game.gameguessnumber;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerUITest {

    static class MessageLogic {
        String getVictoryMessage(int number, int attempts) {
            return "🎉 Поздравляем! Вы угадали число " + number + " за " + attempts + " попыток!";
        }

        String getHintMessage(int guess, String direction) {
            if ("больше".equals(direction)) {
                return "Мое число БОЛЬШЕ чем " + guess;
            } else {
                return "Мое число МЕНЬШЕ чем " + guess;
            }
        }
    }

    static class InputValidator {
        boolean isValidNumber(String input) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }

            try {
                int number = Integer.parseInt(input);
                return number >= 1 && number <= 100;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    @Test
    void testFXMLReferences() {

        assertNotNull(getClass().getResource("/com/game/gameguessnumber/game-view.fxml"),
                "game-view.fxml должен существовать в resources");

        assertNotNull(getClass().getResource("/com/game/gameguessnumber/hello-view.fxml"),
                "hello-view.fxml должен существовать в resources");

        Class<?> controllerClass = GameController.class;

        String[] requiredMethods = {"onCheckButtonClick", "onNewGameClick", "onBackToMenuClick"};
        for (String methodName : requiredMethods) {
            assertDoesNotThrow(() -> controllerClass.getDeclaredMethod(methodName),
                    "GameController должен иметь метод " + methodName);
        }
    }
}