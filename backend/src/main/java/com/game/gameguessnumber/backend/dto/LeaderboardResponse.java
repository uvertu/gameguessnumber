package com.game.gameguessnumber.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "LeaderboardResponse")
public record LeaderboardResponse(
        @Schema(description = "Sorted by attempts asc, then duration asc")
        List<LeaderboardEntryDto> entries
) {
}
