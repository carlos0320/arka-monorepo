package com.arka.notificationmcsv.domain.model;

public record EmailSendResult(boolean success, String providerMessageId, String error) {
}
