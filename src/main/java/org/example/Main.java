package org.example;

import org.example.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        TelegramLongPollingBot myBot = new TelegramLongPollingBot(BotConfig.TOKEN) {
            @Override
            public void onUpdateReceived(Update update) {
                if (update != null && update.hasMessage()) {
                    Message newMessage = update.getMessage();
                    long chatId = newMessage.getChatId();
                    MessageGenerator messageGenerator = new MessageGenerator(newMessage.getText(), newMessage.getMessageId());
                    String responseText = messageGenerator.getMessage();

                    setNotification(chatId, responseText);
                }
            }

            @Override
            public String getBotUsername() {
                return BotConfig.BOT_NAME; // Возвращаем имя бота
            }

            private void setNotification(long chatId, String responseText) {
                SendMessage responseMessage = new SendMessage(String.valueOf(chatId), responseText);
                responseMessage.enableMarkdownV2(true);
                try {
                    execute(responseMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(myBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
