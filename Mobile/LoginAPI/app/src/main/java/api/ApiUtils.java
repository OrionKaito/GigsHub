package api;


public class ApiUtils {
    public static final String BASE_URL = "http://10.82.138.138:80";

    public static UserClient getUserClient() {
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }
}
