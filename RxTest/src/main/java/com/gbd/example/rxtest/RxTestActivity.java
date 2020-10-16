package com.gbd.example.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxTestActivity extends Activity {

    private TextView mainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);
        mainTextView = findViewById(R.id.rxtest_main_text);
        handler = new Handler(getMainLooper());
//        functionTest1();
        functionTest2();
    }

    private Handler handler;

    private void println(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mainTextView.append(msg);
                mainTextView.append("\n");
                int offset = mainTextView.getLineCount() * mainTextView.getLineHeight();
                if (offset > mainTextView.getHeight())
                    mainTextView.scrollTo(0, offset - mainTextView.getHeight());
            }
        });
    }

    private void functionTest1() {
        println("begin function test 1");
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                println("emit 1");
                e.onNext(1);
                println("emit2");
                e.onNext(2);
                println("emit 3");
                e.onNext(3);
                println("emit 4");
                e.onNext(4);
                println("emit on complete");
                e.onComplete();
                println("emit 5");
                e.onNext(5);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer value) {
                        println("on received :" + value);
                        if (value == 3) {
                            println("on dispose :");
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        println("on onError :" + e);
                    }

                    @Override
                    public void onComplete() {
                        println("on onComplete :");
                    }
                });
    }

    private void functionTest2()
    {
        println("begin function test 2");
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                println("emit 1");
                e.onNext(1);
                println("emit2");
                e.onNext(2);
                println("emit 3");
                e.onNext(3);
                println("emit 4");
                e.onNext(4);
                println("emit on complete");
                e.onComplete();
                println("emit 5");
                e.onNext(5);

            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "I'm " + integer;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        println("on received :" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        println("on onError :" + e);
                    }

                    @Override
                    public void onComplete() {
                        println("on onComplete :");
                    }
                });
    }
}