package org.example.impl;

import org.example.api.TextGenerator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//! STEP 2
public class TextGeneratorImpl implements TextGenerator { // ? Реализуем интерфейс TextGenerator

    /**
     * ? Отправление сообщения может выполняться только на экземпляре класса бота,
     * ? в нашем случае это TelegramLongPollingBot
     */
    private final TelegramLongPollingBot bot;

    /**
     * ? Создаем конструктор, который принимает экземпляр класса TelegramLongPollingBot (можно сказать один инстанс бота)
     */
    public TextGeneratorImpl(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    /**
     * ? Реализуем метод sendTextMessage
     */
    @Override
    public void sendTextMessage(long chatId, String responseText) {

        /**
         * ? При создании экземпляра SendMessage сразу передадим в конструктор поля
         */
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), responseText);

        /**
         * ? В соответствии с документацией производим выполнение действия
         * ? Выполнение может производиться только на экземпляре (инстансе) бота,
         * ? который мы будем получать из конструктора
         */
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
