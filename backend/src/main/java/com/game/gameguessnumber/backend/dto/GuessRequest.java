package com.game.gameguessnumber.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Schema(name = "GuessRequest")
public record GuessRequest(
        @NotNull
        @Schema(example = "42")
        Integer guess
) {
}
