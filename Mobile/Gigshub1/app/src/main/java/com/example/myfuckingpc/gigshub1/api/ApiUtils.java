package com.example.myfuckingpc.gigshub1.api;


public class ApiUtils {

    public static final String BASE_URL = "http://192.168.1.213:8080";

    public static UserClient getUserClient() {
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }
    public static CustomerClient getCustomerClient(String token) {
        String bearer_token = "Bearer "+ token;
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, bearer_token).create(CustomerClient.class);
    }

    public static CreateEventClient createEventClient(String token) {
        String bearer_token = "Bearer "+ token;
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, bearer_token).create(CreateEventClient.class);
    }
    public static UpdateEventClient updateEventClient(String token) {
        String bearer_token = "Bearer " + token;
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, bearer_token).create(UpdateEventClient.class);
    }
    public static EditProfileClient editProfileClient(String token) {
        String bearer_token = "Bearer " + token;
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, bearer_token).create(EditProfileClient.class);
    }

    public static EventClient eventClient() {
        return RetrofitClient.getClient(BASE_URL).create(EventClient.class);
    }
    public static CategoryClient categoryClient() {
        return RetrofitClient.getClient(BASE_URL).create(CategoryClient.class);
    }
    public static VerifyClient verifyClient() {
        return RetrofitClient.getClient(BASE_URL).create(VerifyClient.class);
    }
    public static AttendClient attend(String token) {
        String bearer_token = "Bearer "+ token;
        return RetrofitClient.createClientWithHeaderToken(BASE_URL, bearer_token).create(AttendClient.class);
    }
}
