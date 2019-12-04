package com.na.backend.service;

import com.na.backend.dto.NewQuestionDto;
import com.na.backend.dto.QuestionDto;
import com.na.backend.entity.Question;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.HeartRepository;
import com.na.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    private final HeartRepository heartRepository;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionMapper questionMapper,
                           HeartRepository heartRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;

        this.heartRepository = heartRepository;
    }

    public List<QuestionDto> getRecommendationQuestionList() {
        List<Question> result = questionRepository.findByCategoryAndType("father", "multi").get();
        List<QuestionDto> compactResult = new ArrayList<>();
        for( Question q : result ) {
            System.out.println(q);
            compactResult.add(questionMapper.toQuestionDto(q));
        }

        return compactResult;
    }

    public Question insertNewQuestion(NewQuestionDto newQuestionDto) {
        return questionRepository.insert(newQuestionDto);
    }

    // postgresql 로..
    public List<QuestionDto> getHeartedQuestionList(String userId) {
        // mongodb로 하려면..
        //user = db.users.findOne( { _id: ObjectID(‘23423481972398127’) } );
        //user_likes = db.question.find({ _id : { $in : user.likes } }).toArray();

        List<String> questionList = heartRepository.findQuestionIdsById(userId);
        List<Question> result = questionRepository.findByIds(questionList).get();
        List<QuestionDto> compactResult = new ArrayList<>();
        for( Question q : result ) {
            System.out.println(q);
            compactResult.add(questionMapper.toQuestionDto(q));
        }

        return compactResult;
    }
}
