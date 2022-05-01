package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;

public class FlowableActivity extends AppCompatActivity {

    private String TAG = "FlowableActivity======";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowable);
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
                testMap();
            }
        });
    }

    //自己创建被观察者
    private void testCreateObservable() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {

            }
        }, BackpressureStrategy.ERROR).subscribe(new FlowableSubscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "create onSubscribe: 方法被执行");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "create onNext：s = " + s);
                throw new RuntimeException("测试异常");
            }

            @Override
            public void onError(Throwable t) {
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
        Flowable.just("时间")
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription d) {
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
        Flowable.create(new FlowableOnSubscribe<String>() {

                    @Override
                    public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                        emitter.onNext("AFinalStone");
                    }
                }, BackpressureStrategy.ERROR).map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return 100;
                    }
                })
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription d) {
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

    //map方法测试
    private void test() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
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
                .subscribeWith(new ResourceObserver() {

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
