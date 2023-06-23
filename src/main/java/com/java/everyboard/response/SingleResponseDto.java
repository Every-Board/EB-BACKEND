package com.java.everyboard.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> {
    private T data;

    @Override
    public String toString() {
        return "SingleResponseDto";
    }
}