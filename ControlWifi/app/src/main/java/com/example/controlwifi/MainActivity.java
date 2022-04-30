package com.example.controlwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private ArrayList<Item> items;

    Button btnUp;
    Button btnDown;
    TextView textView;
    TextView tvPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiService.getInstance(getApplicationContext());
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    apiService.getTemperature()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<Temperature>() {
                                @Override
                                public void onSuccess(@NonNull Temperature temperature) {
                                    textView.setText(temperature.temperature);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }
                            });
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnUp = findViewById(R.id.btn_up);
        btnDown = findViewById(R.id.btn_down);
        textView = findViewById(R.id.text_view);
        tvPercent = findViewById(R.id.tv_percent);
        apiService.send("10")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Item>() {
                    @Override
                    public void onSuccess(@NonNull Item item) {
                        Log.d("DEBUG1", "onSuccess: " + item.status);
                        tvPercent.setText("" + 10);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG0", e.getMessage());
                    }
                });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug0", "onClick: up");
                apiService.up()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Item>() {
                            @Override
                            public void onSuccess(@NonNull Item item) {
                                Log.d("DEBUG1", "onSuccess: " + item.status);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG0", e.getMessage());
                            }
                        });
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug0", "onClick: down");
                apiService.down()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Item>() {
                            @Override
                            public void onSuccess(@NonNull Item item) {
                                Log.d("DEBUG1", "onSuccess: " + item.status);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG0", e.getMessage());
                            }
                        });
            }
        });
    }
}