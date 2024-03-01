package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MessageGenerator {
    static String messageText = "this message id is ";

    MessageGenerator(long messageId) {
        messageText += String.valueOf(messageId).concat(getSmileSet());
    }

    MessageGenerator(String messageText, long messageId) {
        if (messageId % 2 == 0) {
            StringBuilder modifiedText = new StringBuilder();
            for (int i = 0; i < messageText.length(); i++) {
                if ((i + 1) % 2 != 0) {
                    modifiedText.append(messageText.charAt(i));
                }
            }
            this.messageText = modifiedText.toString();
        } else {
            StringBuilder modifiedText = new StringBuilder();
            for (int i = 0; i < messageText.length(); i++) {
                if ((i + 1) % 2 == 0) {
                    modifiedText.append(messageText.charAt(i));
                }
            }
            this.messageText = modifiedText.toString();
        }
        this.messageText += messageId + getSmileSet();
    }

    public static String getMessage() {
        return messageText;
    }

    private static String getRandomSmile() {
        ArrayList<String> smiles = new ArrayList<>(Arrays.asList("✅", "😁", "❤", "🌟", "🎉", "🍕", "🎈", "🔥", "❌", "🥳", "🏄🏻‍♂️", "🤖"));
        Random random = new Random();
        int randomIndex = random.nextInt(smiles.size());

        return smiles.get(randomIndex);
    }

    private static String getSmileSet() {
        Random repetitionNum = new Random();
        StringBuilder smileSet = new StringBuilder();

        for (int i = 0; i <= repetitionNum.nextInt(10); i++) {
            smileSet.append(getRandomSmile());
        }

        return smileSet.toString();
    }
}
