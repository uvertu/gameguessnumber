Репозиторий разделён на 2 части по принципу Front / Back:
frontend - JavaFX приложение (UI, локализация, отображение, работа с API)
backend - Spring Boot REST API с Swagger UI (игровая логика, лидерборд, локализация)

Быстрый старт

1) Запуск Backend

bash
./gradlew :backend:bootRun

Swagger UI:
http://localhost:8080/swagger-ui.html

2) Запуск Frontend

bash
./gradlew :frontend:run

Frontend ожидает, что backend доступен по адресу http://localhost:8080
Настройка: frontend/src/main/resources/app.properties


3 экрана: "Главное меню", "Игра", "Таблица лидеров"
Разделение данных: что берётся из API и что локально - docs/01_Screens.md
Swagger для backend - docs/02_Backend_API_and_Swagger.md
Поддержка языков (ru/en):
- Frontend: frontend/src/main/resources/com/game/gameguessnumber/i18n/
- Backend: backend/src/main/resources/i18n/


1. Код не прошел checkstyle - https://github.com/uvertu/gameguessnumber/actions/runs/19274561136
2. Код прошел checkstyle - https://github.com/uvertu/gameguessnumber/actions/runs/19275301794
3. MR больше 300 строк, stage падает - https://github.com/uvertu/gameguessnumber/actions/runs/19275840760
4. MR меньше 300 строк, stage проходит - https://github.com/uvertu/gameguessnumber/actions/runs/19275908268

Проверка размера MR - https://github.com/uvertu/gameguessnumber/actions/runs/19456871561/job/55672384982
Проверка checkstyle - https://github.com/uvertu/gameguessnumber/actions/runs/19456871561/job/55672384970
Информация о команде - https://github.com/uvertu/gameguessnumber/actions/runs/19456871561/job/55672384971
build - https://github.com/uvertu/gameguessnumber/actions/runs/19456871561/job/55672442842
test - https://github.com/uvertu/gameguessnumber/actions/runs/19456871561/job/55672493779
deploy - https://github.com/uvertu/gameguessnumber/actions/runs/19457003636/job/55672881613

Тесты не прошли - https://github.com/uvertu/gameguessnumber/actions/runs/20369189805/job/58531834909
Тесты прошли - https://github.com/uvertu/gameguessnumber/actions/runs/20369487216/job/58532488522
