package com.example.myfuckingpc.gigshub1;


public class ApiUtils {

    public static final String BASE_URL = "http://192.168.1.213:8080";


    public static FileUploadService createEventClient(String token) {
        String bearer_token = "Bearer "+ token;
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, bearer_token).create(FileUploadService.class);
    }

}
