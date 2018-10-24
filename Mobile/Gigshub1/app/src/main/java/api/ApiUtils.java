package api;


public class ApiUtils {
    public static final String BASE_URL = "http://192.168.1.213:8080";

    public static UserClient getUserClient() {
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }
    public static CustomerClient getCustomerClient() {
        return RetrofitClient.getClient(BASE_URL).create(CustomerClient.class);
    }

}
