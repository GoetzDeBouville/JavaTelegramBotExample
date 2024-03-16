package org.example.impl;

import org.example.api.ImageGenerator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//! STEP 2
public class ImageGeneratorImpl implements ImageGenerator { // ? Реализуем интерфейс ImageGenerator

    /**
     * ? Отправление сообщения может выполняться только на экземпляре класса бота,
     * ? в нашем случае это TelegramLongPollingBot
     */
    private final TelegramLongPollingBot bot;

    /**
     * ? Создаем конструктор который принимает экземпляр класса TelegramLongPollingBot (можно сказать один инстанс бота)
     */
    public ImageGeneratorImpl(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    /**
     * ? Реализуем метод sendAnimation
     */
    @Override
    public void sendAnimation(long chatId, String responseText) {
        /**
         * ? Для отправления анимаций понадится экземпляр класса SendAnimation
         */
        SendAnimation sendAnimation = new SendAnimation();
        /**
         * ? Анимация добавляется в билдер с помощью метода setAnimation(@NonNull InputFile animation)
         * ? следовательно, требуется создать экземпляр InputFile и передать в его конструктор
         * ? ссылку на анимацию .gif
         */
        InputFile file = new InputFile("https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExYXh5ZG1jeTRoODVrc2ppMXB5Y2QyZ3BjOHZmamtnM3E5aWh0am01ayZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/Y1H8BA1cJJACtDhZgR/giphy.gif");
        /**
         * ? Билдим сообщение в соответствии с документацией API
         */
        sendAnimation.setChatId(String.valueOf(chatId));
        sendAnimation.setAnimation(file);
        sendAnimation.setCaption(responseText);

        /**
         * ? В соответствии с документацией производим выполнение действия
         * ? Выполнение может производиться только на экземпляре (инстансе) бота,
         * ? который мы будем получать из конструктора
         */
        try {
            bot.execute(sendAnimation);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
