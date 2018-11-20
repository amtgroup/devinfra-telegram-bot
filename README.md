# DEVINFRA Telegram Bot
Пример реализации бота для Telegram для нужд инфраструктуры разработки. На данный момент поддерживается отправка стандартных уведомлений от JIRA и BitBucket.

# Сборка системы
```bash
mvn clean install
mvn dockerfile:build
mvn dockerfile:push
```
