package com.oldbie.apflux.network;

import com.oldbie.apflux.model.ResponseMark;
import com.oldbie.apflux.model.ResponseNews;
import com.oldbie.apflux.model.ResponseTimeTable;
import com.oldbie.apflux.model.ServerResponse;
import com.oldbie.apflux.model.TimeTable;
import com.oldbie.apflux.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkAPI {

    @POST("api_login.php")
    @FormUrlEncoded
    Call<ServerResponse> checkLogin(@Field("user") String user,
                                    @Field("pass") String pass);

    @POST("api_get_time_table.php")
    @FormUrlEncoded
    Call<ResponseTimeTable> getAllData(@Field("sid") String id);

    @POST("api_get_mark.php")
    @FormUrlEncoded
    Call<ResponseMark> getMark(@Field("sid") String id);

    @GET("api_get_news.php")
    Call<ResponseNews> getNewData();

}
