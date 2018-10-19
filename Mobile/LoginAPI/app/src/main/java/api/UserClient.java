package api;

import java.util.Map;

import model.LoginInfo;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserClient {

    @FormUrlEncoded
    @POST("token")
    Call<User> login(@FieldMap Map<String, String> fields);



}
