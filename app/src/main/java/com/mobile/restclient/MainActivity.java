package com.mobile.restclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    final String BASE_URL = "https://jsonplaceholder.typicode.com/posts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<Post>> call = retrofitInterface.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    System.out.println("====== CODE : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post: posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "USER ID: " + post.getUserId() + "\n";
                    content += "TITLE : " + post.getTitle() + "\n";
                    content += "TEXT : " + post.getText() + "\n\n";

                    System.out.println(content);
                }

                String jsonResponse = response.body().toString();
                try {
                    writeListView(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("====================== ERROR =================");
                System.out.println(t.getMessage());
            }
        });
    }


    private void writeListView(String response) throws JSONException {

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj != null){

                List<Post> postList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    Post posts = new Post();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    posts.setId(dataobj.getInt("id"));
//                    modelListView.setImgURL(dataobj.getString("imgURL"));
//                    modelListView.setName(dataobj.getString("name"));
//                    modelListView.setCountry(dataobj.getString("country"));
//                    modelListView.setCity(dataobj.getString("city"));

                    postList.add(posts);

                }

                MyListAdapter myListAdapter = new MyListAdapter(this, postList);
                listView.setAdapter(myListAdapter);

            }else {
                Toast.makeText(MainActivity.this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
