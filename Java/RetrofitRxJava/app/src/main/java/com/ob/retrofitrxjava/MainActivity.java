package com.ob.retrofitrxjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ob.retrofitrxjava.databinding.ActivityMainBinding;
import com.ob.retrofitrxjava.model.CityModel;
import com.ob.retrofitrxjava.model.LoginRequestModel;
import com.ob.retrofitrxjava.model.LoginResponseModel;
import com.ob.retrofitrxjava.service.ServiceAPI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Retrofit retrofit;
    private String mainURL = "http://192.168.1.7:3000/";
    private CompositeDisposable compositeDisposable;
    private ServiceAPI serviceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        compositeDisposable = new CompositeDisposable();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(600, TimeUnit.SECONDS)
            .build();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
            //.client(okHttpClient)
            .baseUrl(mainURL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        serviceAPI = retrofit.create(ServiceAPI.class);

        //getCities1();
        //getCities2();
        //getCities3();
        //login();
        loginWithURL();
    }

    private void getCities1() {
        compositeDisposable.add(serviceAPI.getCitiesObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        );
    }

    private void getCities2() {
        compositeDisposable.add(serviceAPI.getCitiesObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<CityModel>()
            {
                @Override
                public void onNext(@NonNull CityModel cityModel) {
                    handleResponse(cityModel);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            }));
    }

    private void getCities3() {
      compositeDisposable.add(serviceAPI.getCitiesSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        );
    }

    private void login() {
        LoginRequestModel loginRequestModel = new LoginRequestModel("onurbilke","123456");

        compositeDisposable.add(serviceAPI.login(loginRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<LoginResponseModel>()
            {
                @Override
                public void onNext(@NonNull LoginResponseModel loginResponseModel) {
                    try {
                        int code = loginResponseModel.getCode();
                        String message = loginResponseModel.getMessage();

                        System.out.println("*************************");
                        System.out.println(code);
                        System.out.println(message);
                        System.out.println("*************************");
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            }));
    }

    private void loginWithURL() {
        LoginRequestModel loginRequestModel = new LoginRequestModel("onurbilke","123456");

        compositeDisposable.add(serviceAPI.loginWithURL("api/login", loginRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<LoginResponseModel>()
            {
                @Override
                public void onNext(@NonNull LoginResponseModel loginResponseModel) {
                    try {
                        int code = loginResponseModel.getCode();
                        String message = loginResponseModel.getMessage();

                        System.out.println("*************************");
                        System.out.println(code);
                        System.out.println(message);
                        System.out.println("*************************");
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            }));
    }

    private void handleResponse(CityModel cityModel) {
        int code = cityModel.getCode();
        CityModel.Cities cities = cityModel.getData();
        ArrayList<CityModel.City> cityList = (ArrayList<CityModel.City>) cities.getCities();

        for(CityModel.City city : cityList) {
            System.out.println("*************************");
            System.out.println(city.getId());
            System.out.println(city.getName());
            System.out.println(city.getLat());
            System.out.println(city.getLon());
            System.out.println("*************************");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}