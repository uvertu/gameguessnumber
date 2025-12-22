package com.game.gameguessnumber.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse")
public record ErrorResponse(
        @Schema(example = "VALIDATION_ERROR")
        String code,
        @Schema(example = "guess must not be null")
        String message
) {
}
