package com.na.backend.service;

import com.na.backend.dto.BookmarkedQuestionDto;
import com.na.backend.dto.NewQuestionDto;
import com.na.backend.dto.QuestionDto;
import com.na.backend.dto.QuestionnaireDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.UserEntity;
import com.na.backend.repository.HeartRepository;
import com.na.backend.repository.QuestionRepository;
import com.na.backend.repository.QuestionnairRepository;
import com.na.backend.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final HeartRepository heartRepository;

    //질문지 , 유저 Repository
    private final UserRepository userRepository;
    private final QuestionnairRepository questionnairRepository;

    public QuestionService(QuestionRepository questionRepository,
                           HeartRepository heartRepository,
                           UserRepository userRepository,
                           QuestionnairRepository questionnairRepository) {
        this.questionRepository = questionRepository;

        this.heartRepository = heartRepository;

        this.questionnairRepository = questionnairRepository;
        this.userRepository = userRepository;

    }

    public List<QuestionDto> getRecommendationQuestionList() {
        List<Question> result = questionRepository.findByCategoryAndType("father", "multi").get();
        List<QuestionDto> compactResult = new ArrayList<>();
        for( Question q : result ) {
            System.out.println(q);
            compactResult.add(QuestionDto.builder()
                    .content(q.getContent())
                    .choices(q.getChoices())
                    .build());
        }
        return compactResult;
    }

    public Question insertNewQuestion(NewQuestionDto newQuestionDto) {
        return questionRepository.insert(newQuestionDto);
    }

    /*
    8번 작성
    public List<QuestionDto> getHeartedQuestionList(String userId) {
        // mongodb로 하려면..
        //user = db.users.findOne( { _id: ObjectID(‘23423481972398127’) } );
        //user_likes = db.question.find({ _id : { $in : user.likes } }).toArray();

        List<String> questionList = heartRepository.findQuestionIdsById(userId);
        List<Question> result = questionRepository.findQuestionsByIdIn(questionList).get();
        List<QuestionDto> compactResult = new ArrayList<>();
        for( Question q : result ) {
            System.out.println(q);
            compactResult.add(QuestionDto.builder()
                    .content(q.getContent())
                    .choices(q.getChoices())
                    .build());
        }

        return compactResult;
    }
     */


    //6
    public QuestionnaireDto getQuestionnaire(String questionnaireId) {

        //질문지 번호로 해당 질문 받아오기
        Questionnaire questionnaire= questionnairRepository.findById(questionnaireId).get();

        return  QuestionnaireDto.builder()
                .sender(questionnaire.getSender())
                .receiver(questionnaire.getReceiver())
                .questions(questionnaire.getQuestions())
                .build();


    }

    //7
    public void insertAnswer(QuestionnaireDto questionnaireDto) {

        Questionnaire questionnaire=  Questionnaire.builder()
                .sender(questionnaireDto.getSender())
                .receiver(questionnaireDto.getReceiver())
                .questions(questionnaireDto.getQuestions()).build();

        questionnairRepository.save(questionnaire);

    }



    //8
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

    //9
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

    //10
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





}