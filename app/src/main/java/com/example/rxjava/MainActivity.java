package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity======";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_create_observable_on_subscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCreateObservable();
            }
        });
        findViewById(R.id.btn_just).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testJust();
            }
        });
        findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testJust();
            }
        });
    }

    //自己创建被观察者
    private void testCreateObservable() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("AFinalStone");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "create onSubscribe: 方法被执行");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "create onNext：s = " + s);
                throw new RuntimeException("测试异常");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "create onError: 方法被执行");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "create onComplete: 方法被执行");
            }
        });
    }

    //Just方法测试
    private void testJust() {
        Observable.just("时间")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Just onSubscribe: 方法被执行");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "Just onNext：s = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Just onError: 方法被执行");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Just onComplete: 方法被执行");
                    }
                });
    }

    //map方法测试
    private void testMap() {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("AFinalStone");
                    }
                }).map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return 100;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "create onSubscribe: 方法被执行");
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.d(TAG, "create onNext：s = " + s);
                        throw new RuntimeException("测试异常");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "create onError: 方法被执行");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "create onComplete: 方法被执行");
                    }
                });
    }
}