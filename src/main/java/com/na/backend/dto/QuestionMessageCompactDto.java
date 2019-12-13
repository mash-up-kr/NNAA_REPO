package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QuestionMessageCompactDto {

    // 질문자가 보낸 질문들 보기
    // 요청 상태 컬럼 추가 sender_status
    // case 1. 누구님에게 30개의 문제를 보냈어요. 2019.12.04 09:00에
    // case 2. 누구님에게 보낼 10개의 문제를 작성중입니다. 2019.12.04 09:00에 수정


    private String name;
    private Integer currentCount;
    private Integer allCount;
    private LocalDateTime time;
    private String status;

    @Builder
    public QuestionMessageCompactDto(String name, Integer currentCount, Integer allCount, LocalDateTime time, String status) {
        this.name = name;
        this.currentCount = currentCount;
        this.allCount = allCount;
        this.time = time;
        this.status = status;
    }

    // 응답자가 응답한 질문들 보기, 받은 질문들 보기
    // 응답 상태 컬럼 추가 receiver_status
    // case 1. 누구님으로부터 20개의 문제를 받았어요 2019.12.04 09:00에
    // case 2. 누구님으로부터 온 20개의 문제 중 2개 질문에 답했습니다. 2019.12.04 09:00에
    // case 3. 누구님으로부터 온 20개의 문제에 답변을 완료했습니다. 2019.12.04 09:00에



}
