package com.na.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class QuestionMessage {

    @Id
    private String id;

    private String senderId;

    private String receiverId;

    private List<String> questions;

    private LocalDateTime senderTime;
    private LocalDateTime receiverTime;

    @Builder
    public QuestionMessage(String senderId,
                           String receiverId,
                           List<String> questions,
                           LocalDateTime senderTime,
                           LocalDateTime receiverTime){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.questions = questions;
        this.senderTime = senderTime;
        this.receiverTime = receiverTime;
    }


    @Override
    public String toString() {
        return "QuestionMessage{" +
                "id='" + id + '\'' +
                ", sender='" + senderId + '\'' +
                ", receiver='" + receiverId + '\'' +
                ", questions=" + questions +
                ", senderTime=" + senderTime +
                ", receiverTime=" + receiverTime +
                '}';
    }
}
