package com.na.backend.service;

import com.na.backend.dto.QuestionMessageCompactDto;
import com.na.backend.dto.SenderQuestionMessageDto;
import com.na.backend.entity.QuestionMessage;
import com.na.backend.repository.QuestionMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionMessageService {

    private final QuestionMessageRepository questionMessageRepository;
    // private final UserRepository userRepository; // id로 사용자 이름 알아오기위

    public QuestionMessageService(QuestionMessageRepository questionMessageRepository) {
        this.questionMessageRepository = questionMessageRepository;
    }


    public QuestionMessage insertQuestionMessage(String receiverId, SenderQuestionMessageDto sendQuestionMessageDto) {
        // 내 아이디 알아내기
        String senderId = "";
        return questionMessageRepository.insert(QuestionMessage.builder()
                                                            .senderId(senderId)
                                                            .receiverId(receiverId)
                                                            .senderTime(LocalDateTime.now())
                                                            .receiverTime(null)
                                                            .questions(sendQuestionMessageDto.getQuestions())
                                                            .build());
    }

    public List<QuestionMessageCompactDto> getSenderMessages() {
        // 내 아이디 알아내기
        String myId = "";

        List<QuestionMessage> result = questionMessageRepository.findBySenderId(myId).get();
        List<QuestionMessageCompactDto> compactResult = new ArrayList<>();
        for ( QuestionMessage questionMessage : result ) {
            compactResult.add(QuestionMessageCompactDto.builder()
                                                       .time(questionMessage.getSenderTime())
                                                       .currentCount(0)
                                                       .name(questionMessage.getReceiverId())
                                                       .allCount(questionMessage.getQuestions().size())
                                                       .status("")
                                                       .build());
        }
        return compactResult;
    }

    public List<QuestionMessageCompactDto> getReceiverMessages() {
        // 내 아이디 알아내기
        String myId = "";

        List<QuestionMessage> result = questionMessageRepository.findByReceiverId(myId).get();
        List<QuestionMessageCompactDto> compactResult = new ArrayList<>();
        for ( QuestionMessage questionMessage : result ) {
            compactResult.add(QuestionMessageCompactDto.builder()
                    .time(questionMessage.getReceiverTime())
                    .currentCount(0)
                    .name(questionMessage.getReceiverId())
                    .allCount(questionMessage.getQuestions().size())
                    .status("")
                    .build());
        }
        return compactResult;
    }

    // TODO: private String getUserName() => userRepository 에 만들기
}
