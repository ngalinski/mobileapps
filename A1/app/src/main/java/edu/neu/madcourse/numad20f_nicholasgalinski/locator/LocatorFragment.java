// referenced https://developer.android.com/training/permissions/requesting
// and https://developer.android.com/training/location/permissions
// and https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/

package edu.neu.madcourse.numad20f_nicholasgalinski.locator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

import edu.neu.madcourse.numad20f_nicholasgalinski.R;

public class LocatorFragment extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_locator);

        checkLocationPermission();
    }

    // referenced links above
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
            locationAssistant();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                    MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    // referenced links above
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length == 3
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                    locationAssistant();
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.button_again).setVisibility(View.VISIBLE);
                }
                return;
            }
        }
    }

    // function where all the action happens
    private void locationAssistant() {
        // referenced https://stuff.mit.edu/afs/sipb/project/android/docs/training/basics/location/locationmanager.html
        LocationManager myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String currentProvider;
        List<String> providers = myLocationManager.getProviders(true);

        // use GPS first, then use mobile network
        // https://stackoverflow.com/questions/6775257/android-location-providers-gps-or-network-provider
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            currentProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            currentProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "No active location providers. " +
                    "Please enable location providers.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            findViewById(R.id.button_again).setVisibility(View.VISIBLE);
            return;
        }

        // to check for permisisons
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
        Location myLocation = myLocationManager.getLastKnownLocation(currentProvider);
        myLocationManager.requestLocationUpdates(currentProvider,
                0, 0, myListener);
        if (myLocation != null) {
            display(myLocation);
        }
        findViewById(R.id.button_again).setVisibility(View.INVISIBLE);
    }

    public void displayLocationButton(View view) {
        checkLocationPermission();
    }

    private void display(Location location) {
        TextView cur_lat = findViewById(R.id.cur_lat);
        cur_lat.setText(String.valueOf(location.getLatitude()));
        TextView cur_lng = findViewById(R.id.cur_lng);
        cur_lng.setText(String.valueOf(location.getLongitude()));
    }

    LocationListener myListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            display(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}