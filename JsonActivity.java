package com.samplejsonparsing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.samplejsonparsing.connectionclasses.ConnectionDetector;
import com.samplejsonparsing.model.SliderData;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nehru on 28-05-2016.
 */
public class JsonActivity extends AppCompatActivity {
    private TextView soc_id, soc_name, soc_pres, soc_manager;
    private ListView slider;
    ConnectionDetector cd;
    private ImageView image1, image2;
    private ArrayList<SliderData> sliderDataArrayList;
    public SliderData sliderData;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_json);
//        slider=(ListView)findViewById(R.id.lv_slider_data);
        image1 = (ImageView) findViewById(R.id.imv_cou_image);
        image2 = (ImageView) findViewById(R.id.imv_soc_image);
        soc_id = (TextView) findViewById(R.id.tv_soc_id);
        soc_name = (TextView) findViewById(R.id.tv_soc_name);
        soc_pres = (TextView) findViewById(R.id.tv_soc_pres);
        soc_manager = (TextView) findViewById(R.id.tv_soc_manager);
        cd = new ConnectionDetector(JsonActivity.this);
        sliderDataArrayList = new ArrayList<>();
        frame = (FrameLayout) findViewById(R.id.fl_id);
        getSliderData();

    }

    public void getSliderData() {
        if (cd.isConnectingToInternet()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            final String url = "http://www.elitedoctorsonline.com/api_mob/browser/society/slider.aspx?";
            RequestParams params = new RequestParams();
            params.put("lang", "en");
            asyncHttpClient.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                }

                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    Log.e("Success", "Method called");
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("slider_data");
                        if (jsonObject.getString("Response").equalsIgnoreCase("200")) {
                            Log.e("Message", jsonObject.getString("Message"));
                            if (!jsonArray.equals("")) {
                                for (int i = 0; i <= jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    sliderData = new SliderData();
                                    sliderData.setSoc_id(object.getString("soc_id"));
                                    Log.e("soc_id", object.getString("soc_id"));
                                    soc_id.setText("soc_id:" + object.getString("soc_id"));
                                    sliderData.setSoc_name(object.getString("soc_name"));
                                    Log.e("soc_name", object.getString("soc_name"));
                                    soc_name.setText("soc_name" + object.getString("soc_name"));
                                    sliderData.setSoc_pres(object.getString("soc_pres"));
                                    Log.e("soc_pres", object.getString("soc_pres"));
                                    soc_pres.setText("soc_pres" + object.getString("soc_pres"));
                                    sliderData.setSoc_website(object.getString("soc_website"));
                                    Log.e("soc_website", object.getString("soc_website"));
                                    sliderData.setSoc_manager(object.getString("soc_manager"));
                                    Log.e("soc_manager", object.getString("soc_manager"));
                                    soc_manager.setText("soc_manager" + object.getString("soc_manager"));
                                    sliderData.setSoc_phone1(object.getString("soc_phone1"));
                                    Log.e("soc_phone1", object.getString("soc_phone1"));
                                    sliderData.setSoc_ext1(object.getString("soc_ext1"));
                                    Log.e("soc_ext1", object.getString("soc_ext1"));
                                    sliderData.setSoc_fax(object.getString("soc_fax"));
                                    Log.e("soc_fax", object.getString("soc_fax"));

                                    sliderData.setSoc_image(object.getString("soc_image"));
                                    Log.e("soc_image", object.getString("soc_image"));
                                    Picasso.with(JsonActivity.this).load(object.getString("soc_image")).into(image1);
                                    sliderData.setCou_image(object.getString("cou_image"));
                                    Log.e("cou_image", object.getString("cou_image"));
                                    Glide.with(JsonActivity.this).load(object.getString("cou_image")).into(image2);
                                    sliderData.setCit_code(object.getString("cit_code"));
                                    Log.e("cit_code", object.getString("cit_code"));
                                    sliderData.setCit_name(object.getString("cit_name"));
                                    Log.e("cit_name", object.getString("cit_name"));
                                    sliderData.setCou_code(object.getString("cou_code"));
                                    Log.e("cou_code", object.getString("cou_code"));
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("Failure", "Method called");

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
