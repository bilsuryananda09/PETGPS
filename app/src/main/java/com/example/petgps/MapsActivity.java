package com.example.petgps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.petgps.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Double Lat,Long;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = database.getReference("location");
        Log.d("databasereference", String.valueOf(databaseReference));
        DatabaseReference dataGPS = databaseReference.child("raw_data");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        databaseReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Lat = dataSnapshot.child("latitude").getValue(Double.class);
                    Long = dataSnapshot.child("longitude").getValue(Double.class);
                }else{
                    Lat = -8.79390;
                    Long = 115.16003;
                }
                Log.d("latdanlong", String.valueOf(Lat));
//                if(!isAdded()) return;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        mMap = googleMap;
                        googleMap.clear();
                        // Add a marker in Sydney and move the camera
                        LatLng hewan = new LatLng(Lat, Long);
                        mMap.addMarker(new MarkerOptions().position(hewan).title("Posisi Hewan")
                                .icon(BitmapFromVector(getApplicationContext(),R.drawable.icon)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hewan,18));
                    }

                    private BitmapDescriptor BitmapFromVector(Context context, int vectorResid){
                        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResid);
                        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
                        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        vectorDrawable.draw(canvas);
                        return BitmapDescriptorFactory.fromBitmap(bitmap);
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(Lat, Long);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Posisi Hewan"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
//    }
}