package io.leftshift.sample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.leftshift.sample.R;
import io.leftshift.sample.network.WeatherApi;
import io.leftshift.sample.parser.CurrentWeatherResponse;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private Subscription subscription;
    private WeatherApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        api = createWeatherApi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((subscription != null) && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @OnClick(R.id.make_call)
    public void getWeather(View view) {
        Observable<CurrentWeatherResponse> responseObservable = api
                .getWeather("jakarta", "2de143494c0b295cca9337e1e96b00e0");
        subscription = responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentWeatherResponse>() {
                    @Override
                    public void onCompleted() {
                        if ((subscription != null) && !subscription.isUnsubscribed()) {
                            subscription.unsubscribe();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error Occured");
                    }

                    @Override
                    public void onNext(CurrentWeatherResponse currentWeatherResponse) {
                        if ( null != currentWeatherResponse) {
                            Timber.i("City " + currentWeatherResponse.name);
                            Timber.i("Max temp " + currentWeatherResponse.main.temperature
                                    + " Kelvin");
                        }
                    }
                });
    }

    private WeatherApi createWeatherApi() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(
                "http://api.openweathermap.org/data/2.5");

        return builder.build().create(WeatherApi.class);
    }
}
