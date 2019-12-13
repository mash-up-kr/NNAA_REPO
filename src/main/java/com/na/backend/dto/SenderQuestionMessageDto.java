package com.na.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SenderQuestionMessageDto {

    private Integer sender;
    private Integer receiver;
    private LocalDateTime senderTime;
    private List<String> questions;


}