package com.example.malik.bbvaapp.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.Manifest;

import com.example.malik.bbvaapp.R;
import com.example.malik.bbvaapp.fragments.DetailsFragment;
import com.example.malik.bbvaapp.fragments.LocationListFragment;
import com.example.malik.bbvaapp.model.BankLocation;
import com.example.malik.bbvaapp.net.GetLocationTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,LocationListFragment.OnListViewSelectedListener {
    private LinearLayout layoutListView;
    private LinearLayout.LayoutParams lpListView;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    LatLng latLng;
    Marker mCurrLocationMarker;
    private CoordinatorLayout coordinatorLayout;
    private String TAG = HomeActivity.class.getSimpleName();
   // private ProgressDialog pDialog;
    private ListView listView;

    private static String url ="https://maps.googleapis.com/maps/api/place/textsearch/json?query=BBVA+Compass&location=42.5571574,-87.8361534&radius=10000&key=AIzaSyCii41i77gwaslCjKryxqJnC5bQRkwYweE";
            ArrayList<BankLocation> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkLocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        listView = (ListView)findViewById(R.id.listview);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        GetLocationTask task = new GetLocationTask(this,mMap,listView);
        task.execute(url);
        //onClickListener for markers
       /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if(arg0.getTitle().equals("Marker")) // if marker source is clicked
                   // Toast.makeText(HomeActivity.this, "click works", Toast.LENGTH_SHORT).show();// display toast
                {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Location details", Snackbar.LENGTH_LONG)
                            .setAction("GO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(HomeActivity.this, "click works", Toast.LENGTH_SHORT).show();
                                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.maps_frame, new DetailsFragment());
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                            });
                    snackbar.show();
                }


                return true;
            }

        });*/
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        /*if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //onclick of menu item

        //end of on click part
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.list_view:
                //Toast.makeText(HomeActivity.this, "in",Toast.LENGTH_LONG).show();
               // mMap.getUiSettings().setZoomControlsEnabled(false);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.mapFragment));
                ft.commit();

                layoutListView = (LinearLayout) findViewById(R.id.fragListView);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        100.0f
                );
               layoutListView.setLayoutParams(param);
                return true;
            case R.id.map_view:
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListViewSelected() {
       // LocationListFragment locationListFragment = (LocationListFragment) getSupportFragmentManager()
         //       .findFragmentById(R.id.locationListFragment);
        LocationListFragment fragment = new LocationListFragment();
        Bundle bundle = new Bundle();
        //bundle.putCharArray("latlongvalue", );
        fragment.setArguments(bundle);
    }
}
