# Backend: API + Swagger

Backend реализован как Spring Boot приложение (модуль `backend`). Swagger UI доступен через `springdoc-openapi`.

## Как запустить

```bash
./gradlew :backend:bootRun
```

По умолчанию backend слушает порт **8080**.

## Swagger UI

Открыть в браузере:
- `http://localhost:8080/swagger-ui.html`

API спецификация (OpenAPI JSON):
- `http://localhost:8080/v3/api-docs`

## Эндпоинты

### 1) Старт игры

`POST /api/v1/games`

Заголовки:
- `Accept-Language: ru | en` (необязательно; по умолчанию ru)

Ответ 200:
- `gameId` (UUID)
- `min`, `max`
- `message` (локализованная строка)

### 2) Проверка числа

`POST /api/v1/games/{gameId}/guesses`

Тело запроса:

```json
{ "guess": 42 }
```

Ответ 200:
- `status`: `TOO_LOW | TOO_HIGH | CORRECT | OUT_OF_RANGE | GAME_NOT_FOUND | GAME_ALREADY_FINISHED`
- `attempts`
- `message` (локализованная строка)
- `targetNumber`, `durationMs` — только если игра завершена (`CORRECT` или `GAME_ALREADY_FINISHED`)

### 3) Таблица лидеров

`GET /api/v1/leaderboard?limit=10`

Ответ 200:
- `entries[]`: `rank, attempts, durationMs, playedAt`

## Локализация

Backend использует `AcceptHeaderLocaleResolver` и `ResourceBundleMessageSource`:
- файлы: `backend/src/main/resources/i18n/messages_ru.properties`, `messages_en.properties`
- по умолчанию: `ru`
