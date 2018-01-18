package fr.isen.mollinari.androidtoolbox.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.isen.mollinari.androidtoolbox.R;

public class PermissionActivity extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_CODE = 11;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 22;
    private static final int PERMISSIONS_ACCESS_COARSE_LOCATION = 33;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        showContacts();
        showCurrentPosition();

    }

    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    private void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            final ListView listview = findViewById(R.id.listView);
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
            listview.setAdapter(adapter);
        }
    }

    private List<String> getContactNames() {
        final ArrayList<String> list = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,null,null, null);
        if (phones != null && phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                list.add("Nom : " + name);

            }
            phones.close();
        }
        return list;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION) {
                showCurrentPosition();
            }
            else if(requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                showContacts();
            }
        } else {
            Toast.makeText(this, "Permission refusée par l'utilisateur", Toast.LENGTH_SHORT).show();
        }
    }

    public void showCurrentPosition() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PermissionActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 1, this);
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    ((TextView) findViewById(R.id.tvDisplay)).setText(getString(R.string.permission_location, location.getLatitude(), location.getLongitude()));
                }
            }
        }
    }


    public void getPickFromGallerie (View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent .setType("image/*");
        startActivityForResult(intent , REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                if (imageUri != null) {
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    ((ImageView) findViewById(R.id.ivPhotoGalerie)).setImageBitmap(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        ((TextView) findViewById(R.id.tvDisplay)).setText(getString(R.string.permission_location, location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(this, "Gps est déactivé",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Gps est activé", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("PermissionActivity", "new status : " + status);

    }
}
