package com.refat.restweather.app.retrofit;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.refat.restweather.app.R;
import com.refat.restweather.app.retrofit.Model.ForecastModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.picasso.Picasso;
import retrofit.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by refat on 26/12/2015.
 */
public class RetroMainFragment extends Fragment {

    public static String TAG = RetroMainFragment.class.getSimpleName();
    private SharedPreference sharedPreference;
    private double pref_lat, pref_lon;
    private TextView tv_temp, tv_city, tv_desc;
    private ImageView iv_icon;
    private Button bt_graph;
    private ImageButton ib_float;
    private RecyclerView mRecyclerView;
    private String url,appid,unit;
    private ArrayList<RvData> rvList;

    Communicator mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sharedPreference.save(getActivity(), "Newyork");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.i("RetroMainFragment:", "In onCreateView()");
        url = "http://api.openweathermap.org";
        appid="c50bd4a48e42564d74baec4a6d6a8e71";
        unit="metric";
        //http://api.openweathermap.org/data/2.5/forecast/daily?lat=28.61&lon=77.20&cnt=7&mode=json&appid=c50bd4a48e42564d74baec4a6d6a8e71&units=metric
        View view = inflater.inflate(R.layout.retro_main_fragment, container, false);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);

        tv_temp = (TextView) view.findViewById(R.id.tv_temp);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        ib_float = (ImageButton) view.findViewById(R.id.ib_float);
        bt_graph = (Button) view.findViewById(R.id.bt_graph);
        //animate button


        //change fonts
        Typeface type = Typeface.createFromAsset(getResources().getAssets(), "fonts/LobsterTwo-Bold.otf");
        tv_temp.setTypeface(type);
        sharedPreference = new SharedPreference();


        rvList = new ArrayList<RvData>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bt_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.sendForecastData(rvList);
                // new BarChartFragment().show(getFragmentManager(), "bar_chart_frag");

            }
        });
        ib_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AutoPlaceActivity.class);
                startActivity(intent);
                //getForecastReportByCoord();
               // getForecastReportByCity();

            }
        });





        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mCallback = (Communicator) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    public void getForecastReportByCoord() {

        pref_lat = sharedPreference.getLat(getActivity());
        pref_lon = sharedPreference.getLon(getActivity());

        Log.i(TAG, "getForecastReport()");
        if (pref_lat==0 && pref_lon==0){

            pref_lat=34.966671;
            pref_lon=138.933334;
        }

        rvList = new ArrayList<RvData>();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        // add your other interceptors �
        // add logging as last interceptor
        httpClient.interceptors().add(logging);  // <-- this is the important line!
        Log.i("Forecast", "We are in Forecast function.");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        RestApi restApi = retrofit.create(RestApi.class);
        //Toast.makeText(getContext(), "In Forecast:"+location, Toast.LENGTH_LONG).show();

        // Call<ForecastModel> callForecast = restApi.getWeatherForecast(location);
        Call<ForecastModel> callForecast = restApi.getWeatherForCoords(pref_lat,pref_lon,appid,unit,8);
        Log.i("getForcastLocation:", pref_lat + "," + pref_lon);
        callForecast.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Response<ForecastModel> response, Retrofit retrofit) {

                if (response != null) {

                    Log.i("Response Size:", String.valueOf(response.body().getList().size()));


                    for (int i = 0; i < response.body().getList().size(); i++) {

                        String dtTxt = response.body().getList().get(i).getDt().toString();
                        //Log.i("Forecast", "DateInt:"+dtTxt+"->"+convertIntToDate(dtTxt));

                        String temperature = response.body().getList().get(i).getTemp().getDay().toString();
                        Log.i("Forecast", "Temp:"+temperature);
                        temperature = String.format("%.1f", Double.valueOf(temperature));
                        String desc = response.body().getList().get(i).getWeather().get(0).getDescription();
                        desc = charUpperCase(desc);
                        String icon = response.body().getList().get(i).getWeather().get(0).getIcon();
                        icon = "http://openweathermap.org/img/w/" + icon + ".png";
                        Log.i("Forecast", convertIntToDate(dtTxt) + "->" + temperature);
                        rvList.add(new RvData(icon, temperature, desc, convertIntToDate(dtTxt)));
                    }
                    // Log.i("rvList Size:", String.valueOf(rvList.size()));
                    // Toast.makeText(getContext(), "rvList Size" + String.valueOf(rvList.size()), Toast.LENGTH_LONG).show();
                    //set current weather
                    Picasso.with(getContext()).load(rvList.get(0).getIcon())
                            .resize(150, 200)
                            .placeholder(R.drawable.na)
                            .error(R.drawable.na)
                            .into(iv_icon);

                    tv_desc.setText(rvList.get(0).getDesc());
                    tv_temp.setText(rvList.get(0).getTemp() + "\u00b0" + "C");
                    tv_city.setText(response.body().getCity().getName()+","+response.body().getCity().getCountry());

                    mRecyclerView.setAdapter(new RvAdapter(getContext(), rvList));
                    //animateGraphButton();


                } else {
                    Toast.makeText(getContext(), "There is no response from the server.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Forecast Retrofit failed", Toast.LENGTH_LONG).show();
                Log.i(TAG, "ERROR:" + t.toString());

            }
        });


    }

    /*
    public void getForecastReportByCity() {

        rvList = new ArrayList<RvData>();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        // add your other interceptors �
        // add logging as last interceptor
        httpClient.interceptors().add(logging);  // <-- this is the important line!
        Log.i("Forecast", "We are in Forecast function.");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        RestApi restApi = retrofit.create(RestApi.class);
        String city=sharedPreference.getCity(getActivity());//"London,uk";
        if (city.length()<=0){
            city="Dieburg,DE";
        }

        Call<ForecastModel> callForecast = restApi.getWeatherForecastByCity(city,appid,
                unit,8 );

        callForecast.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Response<ForecastModel> response, Retrofit retrofit) {

                if (response != null) {

                    Log.i(TAG,"Response Size:"+ String.valueOf(response.body().getList().size()));


                    for (int i = 0; i < response.body().getList().size(); i++) {

                        String dtTxt = response.body().getList().get(i).getDt().toString();
                        //Log.i("Forecast", "DateInt:"+dtTxt+"->"+convertIntToDate(dtTxt));

                        String temperature = response.body().getList().get(i).getTemp().getDay().toString();
                        Log.i("Forecast", "Temp:"+temperature);
                        temperature = String.format("%.1f", Double.valueOf(temperature));
                        String desc = response.body().getList().get(i).getWeather().get(0).getDescription();
                        desc = charUpperCase(desc);
                        String icon = response.body().getList().get(i).getWeather().get(0).getIcon();
                        icon = "http://openweathermap.org/img/w/" + icon + ".png";
                        Log.i("Forecast", convertIntToDate(dtTxt) + "->" + temperature);
                        rvList.add(new RvData(icon, temperature, desc, convertIntToDate(dtTxt)));
                    }
                    // Log.i("rvList Size:", String.valueOf(rvList.size()));
                    // Toast.makeText(getContext(), "rvList Size" + String.valueOf(rvList.size()), Toast.LENGTH_LONG).show();
                    //set current weather
                    Picasso.with(getContext()).load(rvList.get(0).getIcon())
                            .resize(150, 200)
                            .placeholder(R.drawable.na)
                            .error(R.drawable.na)
                            .into(iv_icon);

                    tv_desc.setText(rvList.get(0).getDesc());
                    tv_temp.setText(rvList.get(0).getTemp() + "\u00b0" + "C");
                    tv_city.setText(response.body().getCity().getName()+","+response.body().getCity().getCountry());

                    mRecyclerView.setAdapter(new RvAdapter(getContext(), rvList));
                    //animateGraphButton();


                } else {
                    Toast.makeText(getContext(), "There is no response from the server.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Forecast Retrofit failed", Toast.LENGTH_LONG).show();
                Log.i(TAG, "ERROR:" + t.toString());

            }
        });
        return;

    }*/

    public String charUpperCase(String desc) {
        desc = String.valueOf(desc.charAt(0)).toUpperCase() + desc.substring(1, desc.length());
        return desc;
    }

    public String convertIntToDate(String dtTxt) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM");
        Date date = new java.util.Date(Long.parseLong(dtTxt) * 1000);
        // String dt = dateFormat.format(date);


        return dateFormat.format(date);
    }




    @Override
    public void onResume() {
        super.onResume();
        animateGraphButton();
        //getForecastReportByCity();
        getForecastReportByCoord();

        /*
        ObjectAnimator anim = ObjectAnimator.ofFloat(iv_icon, "alpha", 0f, 1f);
        anim.setDuration(2000);
        anim.start();*/
         Log.i(TAG,"I'm in onResume()");

    }

    public void animateGraphButton() {
        // bt_graph.animate().alpha(5);
        bt_graph.animate().setDuration(2000);
        bt_graph.animate().rotationY(360);
        ObjectAnimator anim = ObjectAnimator.ofFloat(iv_icon, "alpha", 0f, 1f);
        anim.setDuration(2000);
        anim.start();

    }
    public class RvData implements Parcelable {
        String icon;
        String temp;
        String desc;
        String date;

        public RvData(String icon, String temp, String desc, String date) {
            this.icon = icon;
            this.temp = temp;
            this.desc = desc;
            this.date = date;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        // Parcelling part
        public RvData(Parcel in) {
            String[] data = new String[4];

            in.readStringArray(data);
            this.icon = data[0];
            this.temp = data[1];
            this.desc = data[2];
            this.date = data[3];
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringArray(new String[]{
                    this.icon,
                    this.temp,
                    this.desc,
                    this.date
            });

        }

        public class RvDataCreator implements Parcelable.Creator<RvData> {
            public RvData createFromParcel(Parcel source) {
                return new RvData(source);
            }

            public RvData[] newArray(int size) {
                return new RvData[size];
            }
        }
    }
    public interface Communicator {
        public void sendForecastData(ArrayList<RvData> rvList);
    }
}

