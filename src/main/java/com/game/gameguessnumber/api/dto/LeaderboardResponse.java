package com.game.gameguessnumber.api.dto;

import java.util.List;

public record LeaderboardResponse(List<LeaderboardEntry> entries) {
}
