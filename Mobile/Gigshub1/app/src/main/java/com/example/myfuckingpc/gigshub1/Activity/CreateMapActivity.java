package com.example.myfuckingpc.gigshub1.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class CreateMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude, longitude;
    private LatLng myPosition, destination;
    private LatLngBounds.Builder builder;
    public static final String EXTRA_DATA2 = "EXTRA_DATA_1";
    public static final String EXTRA_DATA1 = "EXTRA_DATA_2";
    private List<Address> list;
    private EditText et_search;
    private int count_time = 0;
    private Handler handler;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);

        mapFragment.getMapAsync(this);
        et_search = findViewById(R.id.et_search_address);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // Getting latitude of the current location
            latitude = location.getLatitude();

            // Getting longitude of the current location
            longitude = location.getLongitude();

            // Creating a LatLng object for the current location

            myPosition = new LatLng(latitude, longitude);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 15);
            mMap.moveCamera(cameraUpdate);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                destination = latLng;
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Hello world"));
                String address = getAddressFromLatLong(getApplicationContext(), latLng.latitude, latLng.longitude).getAddressLine(0);
                et_search.setText(address);
            }
        });
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    count_time = 0;
                    address = et_search.getText().toString();
                    destination = getLocationFromAddress(getApplicationContext(), address);
                    return true;
                }
                return false;
            }
        });
    }

    public void clickToReturnCreate(View view) {
        if (destination != null) {
            final Intent data = new Intent();
            // Lấy string address
            data.putExtra("Address", et_search.getText().toString());
            // Lấy kinh độ vĩ độ
            data.putExtra("Lat", destination.latitude);
            data.putExtra("Lng", destination.longitude);

            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    public Address getAddressFromLatLong(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context);
        list = null;
        try {
            list = geocoder.getFromLocation(lat, lng, 10);
            return list.get(0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public LatLng getLocationFromAddress(Context context, String inputtedAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(resLatLng, 15);
        mMap.moveCamera(cameraUpdate);
        mMap.addMarker(new MarkerOptions()
                .position(resLatLng)
                .title("Hello world"));

        return resLatLng;
    }
}
