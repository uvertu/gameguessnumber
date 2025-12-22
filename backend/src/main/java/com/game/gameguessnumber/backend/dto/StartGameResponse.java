package com.game.gameguessnumber.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "StartGameResponse")
public record StartGameResponse(
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID gameId,
        @Schema(example = "1")
        int min,
        @Schema(example = "100")
        int max,
        @Schema(example = "I have guessed a number from 1 to 100. Try to guess!")
        String message
) {
}
