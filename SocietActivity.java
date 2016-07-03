package com.samplejsonparsing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.samplejsonparsing.adapter.SocietyAdapter;
import com.samplejsonparsing.connectionclasses.ConnectionDetector;
import com.samplejsonparsing.model.SocietyData;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nehru on 07-06-2016.
 */
public class SocietActivity extends Activity {
    private ListView lv_list;

    private ArrayList<SocietyData> societyDataArrayList;
    private SocietyData societyData;
    SocietyAdapter societyAdapter;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.society);
        lv_list = (ListView) findViewById(R.id.lv_list);
        cd = new ConnectionDetector(SocietActivity.this);

        societyDataArrayList = new ArrayList<>();
        getSocietyDetails();

    }

    private void getSocietyDetails() {
        if (cd.isConnectingToInternet()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            final String url = "http://www.elitedoctorsonline.com/api_mob/browser/society/society.aspx?lang=en&cou_id=211";
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
                        JSONArray jsonArray = jsonObject.getJSONArray("society_data");
                        if (!jsonArray.equals("")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                societyData = new SocietyData();
                                societyData.setSoc_image(object.getString("soc_image"));
                                Log.e("soc_image", object.getString("soc_image"));
                                societyData.setSoc_name(object.getString("soc_name"));
                                Log.e("soc_name", object.getString("soc_name"));
                                societyDataArrayList.add(societyData);
                            }
                            societyAdapter = new SocietyAdapter(SocietActivity.this, societyDataArrayList);
                            lv_list.setAdapter(societyAdapter);
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("failure", "method called");
                }

                public void onCancel() {
                    super.onCancel();
                }
            });
        } else {
            Log.e("No Internet", "Check your Internet Connection");
        }
    }
}