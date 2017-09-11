package com.vedmitryapps.httptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MockyActivity extends AppCompatActivity {

    private Button getb;
    private Button postb;
    private Button getqueryb;

    private TextView respone;

    private OkHttpClient okHttpClient;
    private Request request;
    private String url = "http://www.mocky.io/v2/59ad89fa2d0000030b9b7e46";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   /*     getb = (Button) findViewById(R.id.get_b);
        getqueryb = (Button) findViewById(R.id.get_query_b);
        postb = (Button) findViewById(R.id.post_b);

        respone = (TextView) findViewById(R.id.respone_t);*/

        getb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okHttpClient = new OkHttpClient();
                request = new Request.Builder()
                        .url(url)
                        .build();

                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("TAG", e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("TAG", response.body().string());
                        }
                    });
            }
        });

    }


}
