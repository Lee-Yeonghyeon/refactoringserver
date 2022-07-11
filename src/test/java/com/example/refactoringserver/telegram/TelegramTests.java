package com.example.refactoringserver.telegram;

import org.junit.jupiter.api.Test;

public class TelegramTests {

    @Test
    public void testSendMessage() throws Exception {

        RefactoryBot bot = new RefactoryBot();
        bot.sendMessage("aaaaaa");
    }
}
