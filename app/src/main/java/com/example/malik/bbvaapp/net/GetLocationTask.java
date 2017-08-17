package com.example.malik.bbvaapp.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.malik.bbvaapp.R;
import com.example.malik.bbvaapp.activities.HomeActivity;
import com.example.malik.bbvaapp.adapters.BanksListAdapter;
import com.example.malik.bbvaapp.fragments.DetailsFragment;
import com.example.malik.bbvaapp.model.BankLocation;
import com.example.malik.bbvaapp.model.JsonResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.malik.bbvaapp.R.id.coordinatorLayout;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
     * Async task class to get json by making HTTP call
     */
   public class GetLocationTask  extends AsyncTask<String, Void, ArrayList<BankLocation>> {
Context mContext;
    GoogleMap googleMap;
    ListView mListView;
   // ListView listView;
    public GetLocationTask(Context context, GoogleMap mMap, ListView listView){
        mContext = context;
        googleMap = mMap;
        mListView = listView;
    }
    JsonResult jsonResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonResult = new JsonResult();
        }
    String jsonStr = "";
        @Override
        protected ArrayList<BankLocation> doInBackground(String... arg0) {
            HttpHandler handler = new HttpHandler();

            // Making a request to url and getting response
             jsonStr = handler.makeServiceCall(arg0[0]);

            Log.e(TAG, "Response from url: " + jsonStr);
            jsonResult.parseJson(jsonStr);
            return    jsonResult.getListBBVALocation();

      }

        @Override
        protected void onPostExecute(ArrayList<BankLocation> locations) {
            super.onPostExecute(locations);
            BankLocation locationz = locations.get(1);
            LatLng latlon = new LatLng(locationz.getLatitude(),locationz.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlon));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    latlon).zoom(10).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            for(int i=0;i<locations.size();i++){
                BankLocation location = locations.get(i);
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),location.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_atm))
                        .title(location.getFormattedAddress());

                googleMap.addMarker(marker);

            }

            BanksListAdapter adapter = new BanksListAdapter(
                    jsonResult.getListBBVALocation(),mContext);
           mListView.setAdapter(adapter);
        }

    }