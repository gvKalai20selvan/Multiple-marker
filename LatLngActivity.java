package proveb.gk.com.multiplemarker;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class LatLngActivity extends FragmentActivity {
    String latitude, langitude;
    GoogleMap googleMap;
    int i=0;
    Geocoder geocoder;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latlng);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        // Showing status
        geocoder=new Geocoder(getApplicationContext());
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
            // Enabling MyLocation Layer of Google Map
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
            getDetails();
        }
        // Setting a click event handler for the map
       googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
           @Override
           public void onMapClick(LatLng latLng) {
               // Creating a marker
               MarkerOptions markerOptions = new MarkerOptions();
//                // Setting the position for the marker
               markerOptions.position(latLng);
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
               markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                // Clears the previously touched position
               googleMap.clear();
//                // Animating to the touched position
               googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                // Placing a marker on the touched position
               googleMap.addMarker(markerOptions);
           }
       });
    }

    private void drawMarker(LatLng point) {
        i++;
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
        // Setting latitude and longitude for the marker   
       /* Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(point)
                .title("San Francisco")
                .snippet("Population: 776733"));*/
//        markerOptions.position(point).title(String.valueOf(point)).icon(getResources().getDrawable(R.mipmap.ic_launcher));
//        markerOptions.position(point).snippet(String.valueOf(point.latitude) + "," + String.valueOf(point.longitude));
        // Adding marker on the Google Map
//        int lat=Integer.parseInt(point.latitude);
//        int lng=Integer.parseInt(point.longitude);
        String myaddr="";
        try {
            String lat = point.latitude + "";
            String lng = point.longitude + "";
            double dlat = point.latitude;
            double dlong = point.longitude;
//            List<Address> address = geocoder.getFromLocation(dlat, dlong, 1);
//            String temp_address=geocoder.getFromLocation(dlat, dlong, 1)+"";
//            JSONObject jobject=new JSONObject(temp_address);
//            JSONArray jarray =new JSONArray(temp_address);
//            jarray.get(0);
//            JSONArray rsp = new JSONArray(address.get("Address");

//            JSONArray arr=rsp.getJSONArray(0);
//                JSONArray matches = (JSONArray) rsp.getJSONArray(0);
//                JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
//                formattedAddress = (String) data.get("formatted_address");
            myaddr = geocoder.getFromLocation(dlat,dlong,1)+"";


            System.out.println("keys... JSON:" + myaddr);
            googleMap.addMarker(new MarkerOptions().position(point).title("" + myaddr).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.vadi3)));

//            googleMap.addMarker(new MarkerOptions().position(point).title("LAT(" + point.latitude + ") LONG(" + point.longitude + ")").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
        }catch (Exception er){er.printStackTrace();}
        }

    public void getDetails() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final String url = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
        asyncHttpClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {


                    System.out.println("keys.. JSON:"+response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("geonames");
                    if (!jsonArray.equals("")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.length() != 0) {
                                object.getString("lat");
                                object.getString("lng");
                                latitude = object.getString("lat");
                                langitude = object.getString("lng");
                                Log.e("latitude", latitude);
                                Log.e("langitude", langitude);

                                drawMarker(new LatLng(Double.parseDouble(latitude), Double.parseDouble(langitude)));
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
}


