package com.game.gameguessnumber.backend.model;

import java.time.Instant;

public class ScoreEntry {
    private final int attempts;
    private final long durationMs;
    private final Instant playedAt;

    public ScoreEntry(int attempts, long durationMs, Instant playedAt) {
        this.attempts = attempts;
        this.durationMs = durationMs;
        this.playedAt = playedAt;
    }

    public int getAttempts() {
        return attempts;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }
}
