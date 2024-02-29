package org.example;

import java.util.Random;

public class MessageGenerator { // Создаем класс с несколькими конструкторами
    static String messageText = "this message id is "; // поле c дефолтным значением

    MessageGenerator(long messageId) { // первый конструктор принимает только id сообщения
        messageText += String.valueOf(messageId).concat(getSmileSet()); // преобразуем поле с помощью конкатенации
    }

    MessageGenerator(String messageText, long messageId) { // второй конструктор принимает два аргуменита
        if (messageId % 2 == 0) { // проверяем messageId на чётность
            /**
             * Создаем экземпляр класса StringBuilder. Этот класс очень популярный и мы будем его часто использовать и в Java и в Kotlin
             */
            StringBuilder modifiedText = new StringBuilder();
            for (int i = 0; i < messageText.length(); i++) {
                if ((i + 1) % 2 != 0) {
                    modifiedText.append(messageText.charAt(i)); // на объекте StringBuilder вызываем метод для конкатенации к имеющемуся charSequence новых char
                }
            }
            this.messageText = modifiedText.toString(); // обращаемся к messageText текущего конструктора и присваиваем ему новое значение
        } else {
            StringBuilder modifiedText = new StringBuilder(); // см. выше
            for (int i = 0; i < messageText.length(); i++) {
                if ((i + 1) % 2 == 0) {
                    modifiedText.append(messageText.charAt(i));
                }
            }
            this.messageText = modifiedText.toString();
        }
        this.messageText += messageId + getSmileSet(); // к преобразованной строке messageText конкатенируем messageId и возврат из метода getSmileSet()
    }

    public static String getMessage() {
        return messageText;
    }

    private static char getRandomSmile() { // метод просто возвращает рандомный смайл из строки
        /**
         * в этой строке демонстрация создания объекта (экземпляра класса String).
         * String это класс с перегрузкой конструктора.
         * Конечно же можно было переписать эту строку так: String smiles = "✅😁❤️🌟🎉🍕🎈";
         */
        String smiles = new String("✅😁❤️🌟🎉🍕🎈");
        /**
         * Для реализации рандомайзера обратимся к классу Random, для этого созданим его экземпляр и на его экземпляре вызовим метод nextInt.
         * Кстати, класс Random тоже имеет перегрузку конструктора:  public Random(long seed)
         */
        Random random = new Random(120L);
        /**
         * с помощью экземпляра класса Random сгенерируем одно целое число.
         * метод nextInt принмиает длину строки smiles и возвращает рандомное число от 0 до smiles.length()
         */
        int randomIndex = random.nextInt(smiles.length());
        /**
         * С помощью метода charAt класса String мы получаем рандомный char элемент
         */
        return smiles.charAt(randomIndex);
    }

    private static String getSmileSet() { // этот метож вернет нам набор смайлов
        Random repetitionNum = new Random(); // см. про класс Random выше
        StringBuilder smileSet = new StringBuilder(); // см. про класс Random выше
        for (int i = 0; i <= repetitionNum.nextInt(10); i++) { // генерируем набор смайлов в цикле
            smileSet.append(getRandomSmile());
        }
        return smileSet.toString();
    }
}
