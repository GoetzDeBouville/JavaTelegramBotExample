## Пример java кода, работа с классами и объектами на примере Telegram бота

Проект демонстрирует работу с объктами и перегрузкой конструкторов на примере абстракций API Telegram  и класса с бизнес логикой.

Для теста работы бота следуем инструкции:
1. Клонируем проект командой `git@github.com:GoetzDeBouville/JavaTelegramBotExample.git`
2. В папке /src/main/java/org/example/config/ создаем класс BotConfig следующего содержания:
```java
public class BotConfig {
    public static final String BOT_NAME = "botname";
    public static final String TOKEN = "my_token";
}
```
где botname и my_token это данные вашего бота.

3. Запускаем проект в консоли и получаем такой результат:
<img src="./screencast/screencast.gif" width="800" height="559"> 
