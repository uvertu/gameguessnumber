package com.game.gameguessnumber.api.dto;

import java.util.UUID;

public record StartGameResponse(UUID gameId, int min, int max, String message) {
}
