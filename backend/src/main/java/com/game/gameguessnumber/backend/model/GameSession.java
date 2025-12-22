package com.game.gameguessnumber.backend.model;

import java.time.Instant;
import java.util.UUID;

public class GameSession {
    private final UUID id;
    private final int targetNumber;
    private int attempts;
    private final Instant startedAt;
    private Instant finishedAt;

    public GameSession(UUID id, int targetNumber, Instant startedAt) {
        this.id = id;
        this.targetNumber = targetNumber;
        this.startedAt = startedAt;
    }

    public UUID getId() {
        return id;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        attempts++;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public boolean isFinished() {
        return finishedAt != null;
    }

    public void finish(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }
}
