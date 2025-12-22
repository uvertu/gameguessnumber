package com.game.gameguessnumber.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "GuessResponse")
public record GuessResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID gameId,
        GuessStatus status,
        @Schema(example = "1")
        int attempts,
        @Schema(example = "My number is HIGHER than 10")
        String message,
        @Schema(description = "Present only when the game is finished", example = "42")
        Integer targetNumber,
        @Schema(description = "Present only when the game is finished", example = "12345")
        Long durationMs
) {
}
