package api;

import model.UserInfomation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface CustomerClient {

    @GET("/api/customer/get")
    Call<UserInfomation> getUserInfomation(@Header("Authorization") String token, @Header("Content-Type") String content_type, @Header("Accept") String accept );
}
