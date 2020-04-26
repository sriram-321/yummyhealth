package com.example.yummyhealth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyhealth.Model.OrderItem;
import com.example.yummyhealth.ViewHolder.OrderItemsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiderMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private OrderItemsAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference orderref;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public LatLng pos;
    private FusedLocationProviderClient fusedLocationClient;

    public double distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_main);

        toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                getLastKnownLocation();

        }
         else {
            // Permission has already been granted
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
         getLastKnownLocation();

        firebaseAuth = FirebaseAuth.getInstance();
        orderref = FirebaseDatabase.getInstance().getReference("Orders");
        setUpRecyclerView();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void getLastKnownLocation() {
        fusedLocationClient.getLastLocation().addOnCompleteListener((new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    pos = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.d("Latitude =",Double.toString(pos.latitude));
                }
            }
        }));
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.rider_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        FirebaseRecyclerOptions<OrderItem> options = new FirebaseRecyclerOptions.Builder<OrderItem>()
                .setQuery(orderref,OrderItem.class)
                .build();
        adapter = new OrderItemsAdapter(options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OrderItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(double latitude, double longitude, String phone, String cust_uid, String status,String amount)
            {
                final int R = 6371; // Radius of the earth

                double latDistance = Math.toRadians(latitude - pos.latitude);
                double lonDistance = Math.toRadians(longitude - pos.longitude);
                double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(pos.latitude)) * Math.cos(Math.toRadians(latitude))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double dist = R * c;

                dist = Math.pow(dist, 2);
                distance = Math.sqrt(dist);
                Log.d("Coordinates = ",Double.toString(latitude)+","+Double.toString(longitude));
                Log.d("Rider = ", Double.toString(pos.latitude)+","+Double.toString(pos.longitude));
                Log.d("Distance = ", Double.toString(distance));

                Intent intent = new Intent(RiderMainActivity.this,RiderMapsActivity.class);

                intent.putExtra("RiderLat",Double.toString(pos.latitude));
                intent.putExtra("RiderLong",Double.toString(pos.longitude));

                intent.putExtra("OrderLat",Double.toString(latitude));
                intent.putExtra("OrderLong",Double.toString(longitude));

                intent.putExtra("Distance",Double.toString(distance));

                intent.putExtra("Phone_Number", phone);
                intent.putExtra("Customer_UID", cust_uid);
                intent.putExtra("Status", status);
                intent.putExtra("Amount",amount);

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.profile_nav:
                Intent intent = new Intent(RiderMainActivity.this,  ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RiderMainActivity.this, LoginActivity.class));
                finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
