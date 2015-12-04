package io.leftshift.sample.network;

import io.leftshift.sample.parser.CurrentWeatherResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by gandharva on 24/11/15.
 */
public interface WeatherApi {
    @GET("/weather")
    Observable<CurrentWeatherResponse> getWeather(@Query("q") String city
            , @Query("appid") String appId);

}
