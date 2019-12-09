package com.mobile.restclient;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView name, userName, email, phone, website;
    ListView userPosts;

    UserPostsListAdapter userPostsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name = findViewById(R.id.name);
        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        website = findViewById(R.id.website);

        // GRAB INTENT DATA
        Intent intent = getIntent();
        int mUserId = intent.getIntExtra("userId",0);
        String mName = intent.getStringExtra("name");
        String mUserName = intent.getStringExtra("username");
        String mEmail = intent.getStringExtra("email");
        String mPhone = intent.getStringExtra("phone");
        String mWebsite = intent.getStringExtra("website");
        System.out.println(name);

        name.setText(mName);
        userName.setText(mUserName);
        email.setText(mEmail);
        phone.setText(mPhone);
        website.setText(mWebsite);

        // USER POSTS LIST VIEW
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/posts/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<Post>> call = retrofitInterface.getUserPosts(mUserId);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    System.out.println("====== CODE : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                populateListView(posts);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("====================== USERS ERROR =================");
                System.out.println(t.getMessage());
            }
        });

    }

    private void populateListView(List<Post> userPostList) {
        userPosts = findViewById(R.id.userPosts);
        userPostsListAdapter = new UserPostsListAdapter(this, userPostList);
        userPosts.setAdapter(userPostsListAdapter);
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

        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        System.out.println(lat + " " + lng);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
