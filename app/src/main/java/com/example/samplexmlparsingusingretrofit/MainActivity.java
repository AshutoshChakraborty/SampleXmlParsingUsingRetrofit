package com.example.samplexmlparsingusingretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.samplexmlparsingusingretrofit.sdk.InterceptorHTTPClientCreator;
import com.example.samplexmlparsingusingretrofit.sdk.RetrofitSdk;
import com.example.samplexmlparsingusingretrofit.sdk.Service;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InterceptorHTTPClientCreator.createInterceptorHTTPClient(this);
        Service service = new RetrofitSdk.Builder().build(this).getService();

    }

}
