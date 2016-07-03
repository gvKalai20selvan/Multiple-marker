package com.samplejsonparsing;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.samplejsonparsing.adapter.CustomAdapterForListView;
import com.samplejsonparsing.connectionclasses.ConnectionDetector;
import com.samplejsonparsing.model.CountryData;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ListView ll_country_list;
    private TextView tv_heading;
    ConnectionDetector cd;
    private ArrayList<CountryData> countryDataArrayList;
    private ArrayList<CountryData> countryDataArrayList1;
    public CountryData countryData;
    CustomAdapterForListView customAdapterForListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        tv_heading = (TextView) findViewById(R.id.tv_heading);
        ll_country_list = (ListView) findViewById(R.id.lv_country_list);
        ll_country_list.setVisibility(View.VISIBLE);
        cd = new ConnectionDetector(MainActivity.this);

        countryDataArrayList = new ArrayList<>();
        countryDataArrayList1 = new ArrayList<>();
        getCountryDetails();
        tv_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_heading.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                JSONFragment hello = new JSONFragment();
                fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
                fragmentTransaction.commit();
            }
        });
    }
    public void getCountryDetails() {
        if (cd.isConnectingToInternet()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            final String url = "http://www.elitedoctorsonline.com/api_mob/browser/society/country.aspx?";
            RequestParams params = new RequestParams();
            params.put("lang", "en");
            asyncHttpClient.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("Success", "Method called");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("country_data");
                        if (!jsonArray.equals("")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                countryData = new CountryData();
                                countryData.setCou_id(object.getString("cou_id"));
                                Log.e("cou_id", object.getString("cou_id"));
                                countryData.setCou_name(object.getString("cou_name"));
                                Log.e("cou_name", object.getString("cou_name"));
                                countryData.setCou_image(object.getString("cou_image"));
                                Log.e("cou_image", object.getString("cou_image"));
                            }
                            countryDataArrayList.add(countryData);
                            customAdapterForListView = new CustomAdapterForListView(MainActivity.this, countryDataArrayList);
                            ll_country_list.setAdapter(customAdapterForListView);
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("Failure", "Method called");
                }
                @Override
                public void onCancel() {
                    super.onCancel();
                }
            });
        } else {
            Log.e("No Internet", "Check your Internet Connection");
        }
    }
    private void getDetailsFromArrayList() {
        if (countryDataArrayList.size() != 0) {
            Log.e("Size-->", String.valueOf(countryDataArrayList.size()));
            for (int i = 0; i < countryDataArrayList.size(); i++) {
                Log.e("id-->" + i, countryDataArrayList.get(i).getCou_id());
            }
        }
    }
}
