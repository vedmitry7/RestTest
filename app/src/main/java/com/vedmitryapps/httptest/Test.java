package com.vedmitryapps.httptest;


import io.reactivex.Observable;

public class Test {

    public static void main(String[] args) {

        Observable<String> myObservable = (Observable<String>) Observable.just("Hello, world!")
                .subscribe(s -> System.out.println(s));

    }
}