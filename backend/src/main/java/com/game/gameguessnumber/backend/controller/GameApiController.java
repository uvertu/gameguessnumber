package com.game.gameguessnumber.backend.controller;

import com.game.gameguessnumber.backend.dto.*;
import com.game.gameguessnumber.backend.model.ScoreEntry;
import com.game.gameguessnumber.backend.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Game Guess Number API")
public class GameApiController {

    private final GameService gameService;
    private final MessageSource messageSource;

    public GameApiController(GameService gameService, MessageSource messageSource) {
        this.gameService = gameService;
        this.messageSource = messageSource;
    }

    @PostMapping(path = "/games")
    @Operation(summary = "Start a new game")
    public StartGameResponse startGame(Locale locale) {
        var session = gameService.startNewGame();
        String startMessage = messageSource.getMessage(
                "start.message",
                new Object[] { GameService.MIN_NUMBER, GameService.MAX_NUMBER },
                locale
        );

        return new StartGameResponse(session.getId(), GameService.MIN_NUMBER, GameService.MAX_NUMBER, startMessage);
    }

    @PostMapping(path = "/games/{gameId}/guesses", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Make a guess")
    public GuessResponse makeGuess(
            @PathVariable UUID gameId,
            @Valid @RequestBody GuessRequest request,
            Locale locale
    ) {
        int guess = request.guess();
        var result = gameService.guess(gameId, guess, locale);

        return new GuessResponse(
                gameId,
                result.status(),
                result.attempts(),
                result.message(),
                result.targetNumber(),
                result.durationMs()
        );
    }

    @GetMapping(path = "/leaderboard")
    @Operation(summary = "Get leaderboard")
    public LeaderboardResponse getLeaderboard(@RequestParam(defaultValue = "10") int limit) {
        List<ScoreEntry> top = gameService.getTopScores(limit);

        List<LeaderboardEntryDto> dto = java.util.stream.IntStream
                .range(0, top.size())
                .mapToObj(i -> new LeaderboardEntryDto(i + 1, top.get(i).getAttempts(), top.get(i).getDurationMs(), top.get(i).getPlayedAt()))
                .toList();

        return new LeaderboardResponse(dto);
    }
}
