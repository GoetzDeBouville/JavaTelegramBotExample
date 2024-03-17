package org.example.api;

//! STEP 1
public interface TextGenerator { //? Создаем интерфейс для генерации текстового сообщения
    /**
     *? Интерфейс это контракт, то есть он содерджит методы, но не может содержать реализации
     *? Создадим метод который будет отправлять текстовое сообщение в чате
     *? Чтобы отправить любое сообщение в телеграм нам нужно знать id чата, который
     *? будет передан в билдер сообщения
     *? Текст сообщения будем передавать в параметр responseText
     *? который будем так же передавать в билдер сообщения
     */
    void sendTextMessage(long chatId, String responseText);
}