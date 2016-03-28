package com.refat.restweather.app.retrofit;

import com.refat.restweather.app.retrofit.Model.ForecastModel;
import com.refat.restweather.app.retrofit.Model.Main;
import com.refat.restweather.app.retrofit.Model.Model;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by refat on 27/12/2015.
 */
public interface RestApi {


    @GET("/data/2.5/forecast/daily")
    Call<ForecastModel> getWeatherForCoords (@Query("lat") double lat,
                                            @Query("lon") double lon,
                                            @Query("appid") String appid,
                                            @Query("units") String units,
                                            @Query("cnt") int cnt


                                            );


    @GET("/data/2.5/forecast/daily")
    Call<ForecastModel> getWeatherForecastByCity(@Query("q") String city,
                                                 @Query("appid") String appid,
                                                 @Query("units") String units,
                                                 @Query("cnt") int cnt );

}
