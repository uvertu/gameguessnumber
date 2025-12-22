# Как запустить проект

## Требования
- JDK 22 (как в исходном проекте)

## Запуск Backend (Spring Boot)
Из корня репозитория:

```bash
./gradlew :backend:bootRun
```

Проверка:
- `http://localhost:8080/swagger-ui.html`

## Запуск Frontend (JavaFX)
Во втором терминале:

```bash
./gradlew :frontend:run
```

## Как проверить i18n
- В главном меню переключить язык (Русский/English) и убедиться, что тексты меняются без перезапуска приложения.
