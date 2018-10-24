package api;

import java.util.Map;

import model.User;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserClient {

    @FormUrlEncoded
    @POST("/token")
    Call<User> login(@FieldMap Map<String, String> fields);




}
