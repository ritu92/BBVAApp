package com.example.malik.bbvaapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.malik.bbvaapp.R;
import com.example.malik.bbvaapp.model.BankLocation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by malik on 8/8/2017.
 */

public class LocationListFragment extends Fragment implements AdapterView.OnItemClickListener {
    OnListViewSelectedListener mCallback;
   public LocationListFragment(){}
    private String TAG = LocationListFragment.class.getName();
    private ListView lv;
    // URL to get contacts JSON
    private static String URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=42.5571574,-87.8361534&radius=10000&key=AIzaSyCii41i77gwaslCjKryxqJnC5bQRkwYweE";


    ArrayList<HashMap<String, String>> locationList;
    public interface OnListViewSelectedListener{
        public void onListViewSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnListViewSelectedListener) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException((activity).toString()
                    +"doesn't implement OnListViewSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        lv = (ListView) view.findViewById(R.id.list_view);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        BankLocation bankLocation =
                (BankLocation) adapterView.getItemAtPosition(position);
        Toast.makeText(getActivity(),"LAT-LON"+ bankLocation.getLatitude()+"-"+ bankLocation.getLongitude(),Toast.LENGTH_SHORT).show();
        /*String uri = "http://maps.google.com/maps?saddr=" + getActivity(). + "," + bankLocation.getLongitude() + "&daddr=" + bankLocation.getLatitude() + "," + bankLocation.getLongitude();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);*/
    }
}
