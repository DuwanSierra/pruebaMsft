package com.jeffdev.prueba.response;

import com.jeffdev.prueba.exception.CustomException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseWebResponse<T> {
    private CustomException errorCode;
    private T data;

    public static BaseWebResponse successNoData() {
        return BaseWebResponse.builder()
                .build();
    }

    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
                .data(data)
                .build();
    }

    public static BaseWebResponse error(CustomException errorCode) {
        return BaseWebResponse.builder()
                .errorCode(errorCode)
                .build();
    }
}
