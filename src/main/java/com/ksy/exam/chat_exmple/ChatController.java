package com.ksy.exam.chat_exmple;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat") //여기에 있는 모든 요청은 이 문구로부터 시작한다
@Slf4j
public class ChatController {

    @GetMapping("/room")
    public String showRoom() {
        return "chat/room";
    }

    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

//    @AllArgsConstructor
//    @Getter
//    public static class WriteChatMessageRequest
//    { // 클래스로서 getter setter를 쓰는 것을 아래에서 생략이 가능
//        @JsonProperty("authorName")
//        private final String authorName;
//        @JsonProperty("content")
//        private final String content;
//    }
//    위와 동일한 표현
    public record writeChatMessageRequest ( String authorName,  String content){ //
    }

    public record writeChatMessageResponse(long id) { //

    }

    @PostMapping ("/writeMessage")
    @ResponseBody
    public ReData<writeChatMessageResponse> writeMessage(@RequestBody  writeChatMessageRequest req) { // @RequestBody writeChatMessageRequest req 당장 들어가는 타입이 확실하지 않을 때 씀
        //ChatMessage message = new ChatMessage("김철수","안녕");
        ChatMessage message = new ChatMessage(req.authorName(),req.content());
//        ChatMessage message = new ChatMessage(req.getAuthorName(),req.getContent());
        chatMessages.add(message);
        return new ReData<>("S-1","메세지가 작성됨",new writeChatMessageResponse(message.getId()));
    }

    public record messagesRequest(Long fromId) {
    }
    public record messagesResponse(List<ChatMessage> messages, long count) {
    }

    @GetMapping ("/messages")
    @ResponseBody
    public ReData<messagesResponse> messages(messagesRequest req) { // 당장 들어가는 타입이 확실하지 않을 때 씀

        List<ChatMessage> messages = chatMessages;

        log.debug("req: {}",req);
        //@Slf4j를 붙여야 사용할 수 있다. 리턴하지 않고도 IDE 단에서 확인할 수 있는 내용으로 찍고싶을 때

        // 번호가 같이 입력되었다면?
        // 보여지는 숫자의 길이부터 보여줘
        if(req.fromId != null){
            // 해당 번호의 채팅 메세지가 전체 리스트의 몇번째 인텍스인지 없다면 -1
            // 숫자에 부합하는 번호부터 보여주기
            int index = IntStream.range(0, chatMessages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId).findFirst()
                    .orElse(-1); // 아예 없는 인덱스 -1를 줌

            if(index == -1){ // 리스트 자르거
                messages = messages.subList(index+1,messages.size());
            }
        }
        //return new ReData<>("S-1","메세지가 작성됨",chatMessages);
        return new ReData<>(
                "S-1",
                "메세지 리스트",
                new messagesResponse(chatMessages, chatMessages.size())
        );
    }
}
