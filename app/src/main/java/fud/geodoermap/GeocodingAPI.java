package fud.geodoermap;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class GeocodingAPI {
	Context context;
	String address;
	String key="AIzaSyBaGnGbSke5z04QDox_qXpLCxU6sbqkAMg";
	String Url="https://maps.googleapis.com/maps/api/geocode/json?";


    private onStatusLisitener status = null;

    public interface onStatusLisitener {
        public void onStatus(boolean status);
    }

    public void setOnStatusLisitener(onStatusLisitener l){
        this.status = l;
    }

	public GeocodingAPI(Context context, String address){
		this.context= context;
		this.address=address;
	}
	
	public String getUrl(){
        String Url = "";
        Url+=this.Url;
        try {
            Url+="address="+URLEncoder.encode(address,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Url+="&language=zh-TW";
        Url+="&sensor=false";
        Url+="&key="+this.key;
        Log.i("Url", Url);
        return Url;
    }


	public void getGeocodingApiAddress(final TextView showText){
        Ion.with(context)
        .load(getUrl())
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                // do stuff with the result or error
                GeocodingAPIJsonDecode JsonDecode = new GeocodingAPIJsonDecode(context, result);
                String place=JsonDecode.getAddress();
                showText.setText(place);
//                Toast.makeText(context,place,Toast.LENGTH_SHORT).show();
                status.onStatus(true);
            }
        });
    }

    public void getGeocodingApiAddress(final GeoInfo geo,final TextView showText){
        Ion.with(context)
        .load(getUrl())
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                // do stuff with the result or error
                GeocodingAPIJsonDecode JsonDecode = new GeocodingAPIJsonDecode(context, result);
                String place = JsonDecode.getAddress();
                showText.setText(place);
                geo.setName(place);
                status.onStatus(true);
            }
        });
    }

    public void getGeocodingApiLatLng(){
        Ion.with(context)
        .load(getUrl())
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                // do stuff with the result or error
                GeocodingAPIJsonDecode JsonDecode = new GeocodingAPIJsonDecode(context, result);
                LatLng LatLng = JsonDecode.getLatLng();
                Toast.makeText(context, LatLng.latitude + "," + LatLng.longitude, Toast.LENGTH_SHORT).show();
                status.onStatus(true);
            }
        });
    }

	public void getGeocodingApiLatLng(final GoogleMap mMap, final GeoInfo geo){
        Ion.with(context)
        .load(getUrl())
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                // do stuff with the result or error
                GeocodingAPIJsonDecode JsonDecode = new GeocodingAPIJsonDecode(context, result);
                LatLng LatLng = JsonDecode.getLatLng();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng));
                geo.setLatlng(LatLng);
                status.onStatus(true);
            }
        });
	}
}