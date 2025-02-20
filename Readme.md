# Trade Master

## Описание

Проект реализует торговлю акциями(купля-продажа). В проекте реализовано три микросервиса(о них подробнее ниже),
настроена JWT аутентификация и REST API для взаимодействия с БД. Также реализованы несколько триггеров, функций 
и процедур в PostgreSQL, на которые перенесена большая часть вычислений.

## Микросервисы

1. [mvcService](https://github.com/antidimon/TradeMaster/tree/main/mvcService): MVC сервис для отображения GUI пользователям, 
внутри реализована JWT аутентификация. Для GUI используется HTML+CSS+JS

2. [restService](https://github.com/antidimon/TradeMaster/tree/main/restService): Сервис реализует REST API и основную
бизнес логику проекта и все CRUD операции.

3. [stocksAdder](https://github.com/antidimon/TradeMaster/tree/main/stocksAdder): Сервис реализующий отправку тестовых данных
на REST сервис с информацией обо всех акциях.


## Технологии

* Java
* Spring Boot
* Spring REST
* Spring MVC
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* Flyway
* Docker
* HTML+CSS+JS
* Mockito

## Запуск

1. Склонировать репозиторий

    ```bash
    git clone https://github.com/antidimon/TradeMaster
    ```

2. Назначить в файлах .env необходимые параметры

3. Собрать контейнер в корне проекта

   ```bash
    docker-compose up -d --build
    ```
