package com.example.refactoringserver.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class RefactoryBot extends TelegramLongPollingBot {

    private String BOT_NAME = "refactory_dev_bot";
    private String AUTH_KEY = "5305075075:AAFcOwWK3JXJaTiR1q6py478mg-vEXYDjjU";
    private String CHAT_ID = "5536370992";

    @Override
    public void onUpdateReceived(Update update) {
        log.info("update = " + update);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return AUTH_KEY;
    }

    public void sendMessage(String sendMessage) {
        SendMessage message  = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(sendMessage);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
