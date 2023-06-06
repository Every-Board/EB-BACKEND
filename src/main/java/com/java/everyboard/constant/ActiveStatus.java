package com.java.everyboard.constant;

import com.java.everyboard.exception.BusinessLogicException;
import com.java.everyboard.exception.ExceptionCode;
import lombok.Getter;

import java.util.Arrays;

public enum ActiveStatus {
    ACTIVE("활성계정"),
    INACTIVE("비활성계정"),
    DORMANT("휴면계정");

    @Getter
    private String activeStatus;

    ActiveStatus(String activeStatus) { this.activeStatus = activeStatus; }

    public static ActiveStatus verifiedActiveStatus(String data) {
        return Arrays.stream(values())
                .filter(status -> data.trim().toUpperCase().equals(status.toString()))
                .findFirst()
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_VALUES));
    }
}
