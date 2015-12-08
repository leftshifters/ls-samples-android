package io.leftshift.sample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
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

public class TabFragment extends Fragment {

    private Subscription subscription;
    private WeatherApi api;
    private int sectionNumber;

    @Bind(R.id.section_label)
    TextView label;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public TabFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabFragment newInstance(int sectionNumber) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        api = createWeatherApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rx_tabbed, container, false);
        ButterKnife.bind(this, rootView);

        label.setText(getString(R.string.section_format, sectionNumber));

        getWeather();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((subscription != null) && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @OnClick(R.id.get_weather)
    public void getWeather() {
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
                            if (sectionNumber == 1) {
                                label.setText("City " + currentWeatherResponse.name);
                            } else {
                                label.setText("Max temp: " +currentWeatherResponse.main.temperature);
                            }
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