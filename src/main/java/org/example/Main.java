package org.example;

import org.example.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {
        /**
         * создадим экземпляр класса TelegramLongPollingBot и сразу проинициализуем его в соответствии с документацией.
         * Класс TelegramLongPollingBot имеет несколько констркуторов, но мы воспользуемся констркутором, который
         * принимает токен в виде строки.
         * Токен передаем в виде поля класса BotConfig, где константа TOKEN является статической
         */
        TelegramLongPollingBot myBot = new TelegramLongPollingBot(BotConfig.TOKEN) {
            @Override
            public void onUpdateReceived(Update update) { // переопределение метода onUpdateReceived
                if (update != null && update.hasMessage()) { // проверка что
                    /**
                     * бот работает по принципу: получил сообзение - выполнил команду.
                     * Наш бот будет преобразовывать текст сообщения и возвращать его пользователю.
                     * Чтобы работать с сообщением, необходимо получить его инстанс. для этого создаем экземпляр класса Message,
                     * и положим в него сообщение, которое возвращает метод getMessage класса Update,
                     * который в свою очередь уже приходит в наш метод onUpdateReceived
                     */
                    Message newMessage = update.getMessage();
                    long chatId = newMessage.getChatId(); // получаем id чата, чтобы бот вернул  сообщение в этот же чат
                    /**
                     * Создаем экземпляр нашего класса MessageGenerator с конструктором MessageGenerator(String messageText, long messageId)
                     */
                    MessageGenerator messageGenerator = new MessageGenerator(newMessage.getText(), newMessage.getMessageId());
                    /**
                     * Создаем экземпляр класса String, которому присвоим строку, которую возвращает метод getMessage нашего класса MessageGenerator.
                     * Эту строку конечно лучше переписать так String responseText = messageGenerator.getMessage();
                     */
                    String responseText = new String(messageGenerator.getMessage());

                    // вызов метода для отправления сообзения в наш чат
                    setNotification(chatId, responseText);
                }
            }

            @Override
            public String getBotUsername() { // переопределение метода getBotUsername
                return BotConfig.BOT_NAME;
            }

            /**
             * В целях декомпозиции вынесем логику отправления сообщений в отдельный метод setNotification
             */

            private void setNotification(long chatId, String responseText) {
                /**
                 * За отправление сообщение в Telegram отвечает класс SendMessage
                 * Создаем экземпляр класса SendMessage с конструктором SendMessage(@NonNull String chatId, @NonNull String text)
                 */
                SendMessage responseMessage = new SendMessage(String.valueOf(chatId), responseText);
                /**
                 * На экземпляре класса SendMessage вызовим метод enableMarkdownV2, этот метод отвечает за форматирование текста
                 */
                responseMessage.enableMarkdownV2(true);
                try {
                    // метод execute выполняет действие непосредственной отправки сообщения, принимает на вход объект responseMessage
                    execute(responseMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        try {
            // Создаем экземпляр класса TelegramBotsApi, для вызова регистрации бота
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(myBot); // регистрируем бота вызовом метода registerBot на экземпляре класса TelegramBotsApi
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
