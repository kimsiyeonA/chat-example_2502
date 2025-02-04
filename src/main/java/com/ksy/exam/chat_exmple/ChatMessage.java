package com.ksy.exam.chat_exmple;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ChatMessage {
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime createdDate;
    private String authorName;
    private String content;

    public ChatMessage(String authorName, String content) {
        this(ChatMessageIdGenerator.getNextId(), LocalDateTime.now(), authorName, content); // 다른 생성자에게 넘겨줌 //생성자 콜
    }
}

class ChatMessageIdGenerator{

    public static long id = 0; // 클래스 내에서 하나만 생기기 위해서

    public static long getNextId() {
        return ++id;
    }
}
