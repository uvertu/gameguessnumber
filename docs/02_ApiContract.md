# API контракт (Back)

Backend реализован как Spring Boot REST API, документируется через **Swagger UI**.

## Swagger
- UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Эндпоинты

### 1) Start Game
`POST /api/v1/games`

**Headers**
- `Accept-Language: ru` (или `en`)

**Response 200**
```json
{
  "gameId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "min": 1,
  "max": 100,
  "message": "Я загадал число от 1 до 100. Попробуйте угадать!"
}
```

### 2) Make Guess
`POST /api/v1/games/{gameId}/guesses`

**Request**
```json
{ "guess": 42 }
```

**Response 200**
```json
{
  "gameId": "...",
  "status": "TOO_LOW",
  "attempts": 1,
  "message": "Моё число БОЛЬШЕ чем 42",
  "targetNumber": null,
  "durationMs": null
}
```

### 3) Leaderboard
`GET /api/v1/leaderboard?limit=10`

**Response 200**
```json
{
  "entries": [
    {
      "rank": 1,
      "attempts": 3,
      "durationMs": 8500,
      "playedAt": "2025-12-21T18:00:00Z"
    }
  ]
}
```
