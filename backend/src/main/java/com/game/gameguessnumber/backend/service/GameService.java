package com.game.gameguessnumber.backend.service;

import com.game.gameguessnumber.backend.dto.GuessStatus;
import com.game.gameguessnumber.backend.model.GameSession;
import com.game.gameguessnumber.backend.model.ScoreEntry;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 100;

    private final MessageSource messageSource;
    private final Random random = new Random();

    private final ConcurrentHashMap<UUID, GameSession> sessions = new ConcurrentHashMap<>();
    private final List<ScoreEntry> leaderboard = new ArrayList<>();

    public GameService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public GameSession startNewGame() {
        UUID gameId = UUID.randomUUID();
        int target = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
        GameSession session = new GameSession(gameId, target, Instant.now());
        sessions.put(gameId, session);
        return session;
    }

    public GuessResult guess(UUID gameId, int guess, Locale locale) {
        GameSession session = sessions.get(gameId);
        if (session == null) {
            return new GuessResult(GuessStatus.GAME_NOT_FOUND, 0, message(locale, "error.gameNotFound"), null, null);
        }

        if (session.isFinished()) {
            return new GuessResult(
                    GuessStatus.GAME_ALREADY_FINISHED,
                    session.getAttempts(),
                    message(locale, "error.gameAlreadyFinished"),
                    session.getTargetNumber(),
                    durationMs(session)
            );
        }

        if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
            return new GuessResult(
                    GuessStatus.OUT_OF_RANGE,
                    session.getAttempts(),
                    message(locale, "error.outOfRange", MIN_NUMBER, MAX_NUMBER),
                    null,
                    null
            );
        }

        session.incrementAttempts();

        if (guess == session.getTargetNumber()) {
            session.finish(Instant.now());
            long durationMs = durationMs(session);
            addToLeaderboard(session.getAttempts(), durationMs, session.getFinishedAt());

            return new GuessResult(
                    GuessStatus.CORRECT,
                    session.getAttempts(),
                    message(locale, "guess.correct", session.getTargetNumber(), session.getAttempts()),
                    session.getTargetNumber(),
                    durationMs
            );
        }

        if (guess < session.getTargetNumber()) {
            return new GuessResult(
                    GuessStatus.TOO_LOW,
                    session.getAttempts(),
                    message(locale, "guess.tooLow", guess),
                    null,
                    null
            );
        }

        return new GuessResult(
                GuessStatus.TOO_HIGH,
                session.getAttempts(),
                message(locale, "guess.tooHigh", guess),
                null,
                null
        );
    }

    public List<ScoreEntry> getTopScores(int limit) {
        List<ScoreEntry> copy;
        synchronized (leaderboard) {
            copy = new ArrayList<>(leaderboard);
        }
        copy.sort(Comparator
                .comparingInt(ScoreEntry::getAttempts)
                .thenComparingLong(ScoreEntry::getDurationMs));

        if (limit <= 0 || copy.isEmpty()) {
            return List.of();
        }
        return copy.subList(0, Math.min(limit, copy.size()));
    }

    private void addToLeaderboard(int attempts, long durationMs, Instant playedAt) {
        synchronized (leaderboard) {
            leaderboard.add(new ScoreEntry(attempts, durationMs, playedAt));
        }
    }

    private long durationMs(GameSession session) {
        if (!session.isFinished()) {
            return 0;
        }
        return Duration.between(session.getStartedAt(), session.getFinishedAt()).toMillis();
    }

    private String message(Locale locale, String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

    public record GuessResult(
            GuessStatus status,
            int attempts,
            String message,
            Integer targetNumber,
            Long durationMs
    ) {
    }
}
