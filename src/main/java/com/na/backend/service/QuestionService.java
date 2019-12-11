package com.na.backend.service;

import com.na.backend.dto.*;
import com.na.backend.entity.Question;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.UserEntity;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.HeartRepository;
import com.na.backend.repository.QuestionRepository;
import com.na.backend.repository.QustionnairRepository;
import com.na.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    //질문지하나보기
    private final QustionnairRepository  qustionnairRepository;

    private final HeartRepository heartRepository;

    private final UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionMapper questionMapper,
                           QustionnairRepository qustionnairRepository, HeartRepository heartRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.qustionnairRepository = qustionnairRepository;

        this.heartRepository = heartRepository;
        this.userRepository = userRepository;
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

    /*
    밑에 getBookmarkList 부분
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
    */
    public QuestionnaireDto getQuestionnaire(Integer questionnaireId) {

        List<Questionnaire> list =  qustionnairRepository.findAll();
       return  QuestionnaireDto.builder().sender(list.get(questionnaireId).getSender()).receiver(list.get(questionnaireId).getReceiver()).questions(list.get(questionnaireId).getQuestions()).build();

    }

    public void insertAnswer(QuestionnaireDto questionnaireDto) {

       Questionnaire questionnaire=  Questionnaire.builder()
               .sender(questionnaireDto.getSender())
               .receiver(questionnaireDto.getReceiver())
               .questions(questionnaireDto.getQuestions()).build();

        qustionnairRepository.save(questionnaire);

    }

    public void addBookmark(String token,String questionId) {

        // 유저를 토큰으로먼저 찾는다
        UserEntity user = userRepository.findByToken(token);
        // 그 유저의 즐찾 배열을 찾는다
        List<String> bookmarks = user.getBookmark();
        // 그 배열에 질문을 추가한다
        bookmarks.add(questionId);
        //유저정보 업데이트
        userRepository.save(user);

    }


    public void removeBookmark(String token, String questionId) {

        // 유저를 토큰으로먼저 찾는다
        UserEntity user = userRepository.findByToken(token);
        // 그 유저의 즐찾 배열을 찾는다
        List<String> bookmarks = user.getBookmark();
        // 그 배열에 질문을 삭제
        bookmarks.remove(questionId);
        //유저정보 업데이트
        userRepository.save(user);

    }

    public List<BookmarkedQuestionDto> getBookmarkList(String token) {

        // 북마크 질문 dto 리스트
        List<BookmarkedQuestionDto> list =null;
        // 유저를 토큰으로 먼저 찾는다
        UserEntity user = userRepository.findByToken(token);
        // 그 유저의 즐찾 배열을 찾는다
        List<String> bookmarks = user.getBookmark();

        // 그 즐찾 배열에 있는 id 값으로 질문 content 가져오기
        for(int i=0;i<bookmarks.size();i++){
            String content = questionRepository.findById(bookmarks.get(i)).get().getContent();
            //dto에 옮겨 담기
            BookmarkedQuestionDto bookmarkedQuestionDto = new BookmarkedQuestionDto(content);
            //dto를 리스트에 추가
            list.add(bookmarkedQuestionDto);

        }

        return list;

    }
}
