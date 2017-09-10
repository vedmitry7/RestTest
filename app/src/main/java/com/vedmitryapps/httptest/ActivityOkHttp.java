package com.vedmitryapps.httptest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ActivityOkHttp extends AppCompatActivity {

    private String TAG = ActivityOkHttp.class.getSimpleName();

    private Button getb;
    private Button postb;
    private Button getqueryb;

    private TextView respone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getb = (Button) findViewById(R.id.get_b);
        getqueryb = (Button) findViewById(R.id.get_query_b);
        postb = (Button) findViewById(R.id.post_b);

        respone = (TextView) findViewById(R.id.respone_t);
    }

    public void getHttp(View v) {
        //instantiate async task which call service using OkHttp in the background
        // and execute it passing required parameter to it
        //get http request using okhttp

        new ActivityOkHttp.OkHttpAync().execute(this, "get", "");


    }

    public void getQueryHttp(View v) {
        //same async task is used to call different services
        //request type is sent as parameter to async task to identify which service to call
        //get http request with query string using okhttp
        String userData = ((TextView) findViewById(R.id.post_tv)).getText().toString();
        new ActivityOkHttp.OkHttpAync().execute(this, "getquery", userData);
    }

    public void postHttp(View v) {
        //post http request using okhttp
        String userData = ((TextView) findViewById(R.id.post_tv)).getText().toString();
        new ActivityOkHttp.OkHttpAync().execute(this, "post", userData);
    }

    private class OkHttpAync extends AsyncTask<Object, Void, Object> {

        private String TAG = ActivityOkHttp.OkHttpAync.class.getSimpleName();
        private Context contx;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            contx = (Context) params[0];
            String requestType = (String) params[1];
            String requestParam = (String) params[2];

            Log.e(TAG, "processing http request in async task");

            if ("get".equals(requestType)) {
                Log.e(TAG, "processing get http request using OkHttp");
                return getHttpResponse();
            } else if ("getquery".equals(requestType)) {
                Log.e(TAG, "processing get http request with query parameters using OkHttp");
                return getQueryHttpResponse(requestParam);
            } else if ("post".equals(requestType)) {
                Log.e(TAG, "processing post http request using OkHttp");
                return postHttpResponse(requestParam);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            if (result != null) {
                Log.e(TAG, "populate UI after response from service using OkHttp client");
                respone.setText((String) result);
            }
        }
    }

    public Object getHttpResponse() {
        OkHttpClient httpClient = new OkHttpClient();

        String url = "http://www.zoftino.com/api/storeOffers";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "error in getting response get request okhttp");
        }
        return null;
    }

    public Object getQueryHttpResponse(String requestParam) {
        OkHttpClient httpClient = new OkHttpClient();
        String url = "http://www.zoftino.com/api/saveFavorite";

        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        httpBuider.addQueryParameter("coupon", requestParam);

        Request request = new Request.Builder().url(httpBuider.build()).build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "error in getting response get request with query string okhttp");
        }
        return null;
    }

    public Object postHttpResponse(String requestParam) {
        OkHttpClient httpClient = new OkHttpClient();
        String url = "http://www.zoftino.com/api/saveFavorite";

        RequestBody formBody = new FormBody.Builder()
                .add("coupon", requestParam)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.e(TAG, "Got response from server using OkHttp ");
                return response.body().string();
            }

        } catch (IOException e) {
            Log.e(TAG, "error in getting response post request okhttp");
        }
        return null;

    }
    public void getAsyncCall(){
        OkHttpClient httpClient = new OkHttpClient();
        String url = "http://www.zoftino.com/api/storeOffers";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("CLIENT", "AD")
                .addHeader("USERID", "343")
                .build();

        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e(TAG, "error in getting response using async okhttp call");
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }

                Log.i(TAG,responseBody.string());
            }
        });
    }
    public Object postJson(){

        final MediaType mediaType
                = MediaType.parse("application/json");

        OkHttpClient httpClient = new OkHttpClient();
        String url = "http://www.zoftino.com/api/saveFavorite";

        String jsonStr = "{\"coupon\":\"upto 20% off\", \"selectedDate\" : \"20/11/2016\"}";

        //post json using okhttp
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, jsonStr))
                .build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.e(TAG, "Got response from server for JSON post using OkHttp ");
                return response.body().string();
            }

        } catch (IOException e) {
            Log.e(TAG, "error in getting response for json post request okhttp");
        }
        return null;
    }
    public void multipart(){
        OkHttpClient httpClient = new OkHttpClient();
        //creates multipart http requests
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("purpose", "xyz store coupons")
                .addFormDataPart("xml", "xyz_store_coupons.xml",
                        RequestBody.create(MediaType.parse("application/xml"), new File("website/static/xyz_store_coupons.xml")))
                .build();

        Request request = new Request.Builder()
                .url("https://dummy....")
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.e(TAG, "Got response from server for multipart request using OkHttp ");
            }

        } catch (IOException e) {
            Log.e(TAG, "error in getting response for multipart request okhttp");
        }
    }
}