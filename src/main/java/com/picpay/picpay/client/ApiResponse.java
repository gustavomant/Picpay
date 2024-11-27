package com.picpay.picpay.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String status;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private boolean authorization;
    }
}
