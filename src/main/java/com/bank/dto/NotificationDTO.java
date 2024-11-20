package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private int notificationId;
    private int customerId;
    private String message; // 알림 내용
    private String createdAt; // 알림 생성 시간
}