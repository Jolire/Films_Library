# Кинотека

## Описание проекта

Кинотека — это веб-приложение на основе Spring Boot, предназначенное для каталогизации фильмов. Оно предоставляет REST API для управления коллекцией фильмов, а также поддерживает потоковое воспроизведение видео.

## Функциональность

Получение списка фильмов

Получение информации о конкретном фильме

Добавление нового фильма

Редактирование информации о фильме

Удаление фильма из каталога

Поддержка встроенного видеоплеера для потокового воспроизведения

## Технологии

Java 17+

Spring Boot

Spring Web

Spring Data JPA

PostgreSQL (или H2 для тестирования)

Lombok

Jackson (для обработки JSON)

## Установка и запуск

### Требования:

Java 17+

Maven 3+

PostgreSQL (если используется в качестве базы данных)

## Запуск приложения:

### Клонировать репозиторий:

git clone <URL_репозитория>
cd kinoteka

### Настроить application.properties или application.yml в src/main/resources:

spring.datasource.url=jdbc:postgresql://localhost:5432/kinoteka
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

### Собрать и запустить приложение:

mvn clean install
mvn spring-boot:run

### API Эндпоинты

#### Фильмы

GET /api/movies — Получить список всех фильмов

GET /api/movies/{id} — Получить фильм по ID

POST /api/movies — Добавить новый фильм (в теле запроса JSON-объект)

PUT /api/movies/{id} — Обновить информацию о фильме

DELETE /api/movies/{id} — Удалить фильм

### Примеры запросов

#### Получение списка фильмов

curl -X GET http://localhost:8080/api/movies

#### Добавление фильма

curl -X POST http://localhost:8080/api/movies -H "Content-Type: application/json" -d '{"title": "Интерстеллар", "year": 2014, "genre": "Sci-Fi"}'

### Разработка и тестирование

Для локального тестирования можно использовать H2 в памяти, изменив application.properties:

spring.datasource.url=jdbc:h2:mem:kinoteka
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

### Лицензия

Этот проект распространяется под лицензией MIT.
