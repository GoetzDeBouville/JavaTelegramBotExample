package org.example;

import org.example.api.ImageGenerator;
import org.example.api.TextGenerator;
import org.example.config.BotConfig;
import org.example.impl.ImageGeneratorImpl;
import org.example.impl.TextGeneratorImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//! STEP 3
public class Main {
    /**
     * ? В проекте используется класс из прошлой реализации бота, поэтому описываю только нововведения
     * ! Старый код с комментариям можно посмотреть в ветке comments
     */
    public static void main(String[] args) {

        TelegramLongPollingBot myBot = new TelegramLongPollingBot(BotConfig.TOKEN) {

            /**
             * ? Создаем экзмепляры ImageGenerator и TextGenerator
             * ? Т.к. для отправления сообщений нам требуется инстанс конкретного бота,
             * ? будем создавать объекты с использованием кунстрокторов классов реализаций
             * ? т.е. создаем объект ImageGeneratorImpl и присваиваем его переменной imageGenerator,
             * ? this передается в конструктор ImageGeneratorImpl в качестве аргумента,
             * ? что обычно используется для передачи текущего объекта, в данном случае, объекта бота (его инстанса).
             */
            final ImageGenerator imageGenerator = new ImageGeneratorImpl(this);
            final TextGenerator textGenerator = new TextGeneratorImpl(this); // ? Аналогично и для объекта TextGenerator
            @Override
            public void onUpdateReceived(Update update) {
                if (update != null && update.hasMessage()) {
                    Message newMessage = update.getMessage();

                    /**
                     * ? Создадим экземпляр класса User, он понадобится для получения никнэйма юзера
                     */
                    User user = newMessage.getFrom();
                    String username = user.getUserName(); // ? получаем никнэйм

                    long chatId = newMessage.getChatId();

                    /**
                     * ? Отправляем сообщения в зависимости от наличия никнэйма
                     * ? Для отправления сообщения вызываем метод на экземпляре textGenerator
                     * ? если кликнуть с зажатым Ctrl (command для mac), то мы попадем на метод в интерфейсе
                     * ? таким образом реализация у нас скрыта от пользователя метода
                     */
                    if (username == null) {
                        textGenerator.sendTextMessage(chatId, "Привет!");
                    } else {
                        textGenerator.sendTextMessage(chatId, "Привет, " + username + "!");
                    }

                    try {
                        Thread.sleep(1000); // ? Это обычная обработка отложенного выполнения, в данном случяае мы приостанавливаем поток на 1000 милисекунд
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    // ? Еще одно текстовое сообщение
                    textGenerator.sendTextMessage(chatId, "Почти любой мой код на Java выглядит так:");
                    // ? И наконец с помощью экземпляра ImageGenerator отправляем анимацию вызовом метода sendAnimation на экземпляре ImageGenerator
                    imageGenerator.sendAnimation(chatId, "😂");
                }
            }

            @Override
            public String getBotUsername() {
                return BotConfig.BOT_NAME;
            }
        };


        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
