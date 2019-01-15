package com.example.samplexmlparsingusingretrofit.sdk;


public class ResponseSingleton {
    private static ResponseSingleton instance;




    public static ResponseSingleton getInstance() {
        if (instance == null) {
            instance = new ResponseSingleton();
        }
        return instance;
    }

    private ResponseSingleton() {
    }


}
