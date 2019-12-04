package com.na.backend.repository;

import com.na.backend.dto.NewQuestionDto;
import com.na.backend.dto.ReceiverQuestionMessageDto;
import com.na.backend.dto.SenderQuestionMessageDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.QuestionMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionMessageRepository extends MongoRepository<QuestionMessage, String> {

    Optional<List<QuestionMessage>> findBySenderId(String senderId);
    Optional<List<QuestionMessage>> findByReceiverId(String senderId);
    QuestionMessage insert(QuestionMessage questionMessage);
    //QuestionMessage update(ReceiverQuestionMessageDto receiverQuestionMessageDto);
    //Optional<List<Question>> findByIds(List<String> ids);
}
