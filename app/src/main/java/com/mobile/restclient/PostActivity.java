package com.mobile.restclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    TextView nameOfUser, postTitle, blogBody;
    ListView listViewComments;
    CommentListAdapter commentListAdapter;
    Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        nameOfUser = findViewById(R.id.nameOfUser);
        postTitle = findViewById(R.id.postTitle);
        blogBody = findViewById(R.id.blogBody);
        listViewComments = findViewById(R.id.listViewComments);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createComment();
            }
        });

        // GRAB INTENT DATA
        Intent intent = getIntent();
        int id = intent.getIntExtra("userId",0);
        String title = intent.getStringExtra("postTitle");
        String body = intent.getStringExtra("blogBody");
        int pId = intent.getIntExtra("postId",0);

        System.out.println(id);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/users/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<User> call = retrofitInterface.getUser(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    System.out.println("====== CODE : " + response.code());
                    return;
                }
                User user = response.body();
                nameOfUser.setText("Name: " + user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("====================== USERS ERROR =================");
                System.out.println(t.getMessage());
            }
        });

        postTitle.setText("Post Title: " + title);
        blogBody.setText("Blog Body: " + body);


        // COMMENT LIST VIEW
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/comments/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface2 = retrofit2.create(RetrofitInterface.class);

        Call<List<Comment>> call2 = retrofitInterface2.getPostComments(pId);

        call2.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    System.out.println("====== CODE : " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
//                for(Post post: comments){
//                    String content = "";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "USER ID: " + post.getUserId() + "\n";
//                    content += "TITLE : " + post.getTitle() + "\n";
//                    content += "TEXT : " + post.getText() + "\n\n";
//
//                    System.out.println(content);
//                }

                populateListView(comments);
//                String jsonResponse = response.body().toString();
//                try {
//                    writeListView(jsonResponse);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                System.out.println("====================== COMMENT ERROR =================");
                System.out.println(t.getMessage());
            }
        });

        nameOfUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO TO USER'S PAGE
                System.out.println("CLICKED USER NAME");
                Intent intent = getIntent();
                int id = intent.getIntExtra("userId",0);
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/users/")
                        .addConverterFactory(GsonConverterFactory.create()).build();

                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

                Call<User> call = retrofitInterface.getUser(id);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(!response.isSuccessful()){
                            System.out.println("====== CODE : " + response.code());
                            return;
                        }
                        User user = response.body();
                        //nameOfUser.setText("Name: " + user.getAddress().getGeo().getLng());
                        Intent intent = new Intent(PostActivity.this, UserActivity.class);
                        intent.putExtra("userId", user.getId());
                        intent.putExtra("name",user.getName());
                        intent.putExtra("username",user.getUserName());
                        intent.putExtra("email",user.getEmail());
                        intent.putExtra("phone",user.getPhone());
                        intent.putExtra("website",user.getWebsite());
                        intent.putExtra("lat",user.getAddress().getGeo().getLat());
                        intent.putExtra("lng",user.getAddress().getGeo().getLng());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("====================== USERS ERROR =================");
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

    }

    private void populateListView(List<Comment> commentList) {
        listViewComments = findViewById(R.id.listViewComments);
        commentListAdapter = new CommentListAdapter(this, commentList);
        listViewComments.setAdapter(commentListAdapter);
    }

    private void createComment(){
        Comment comment = new Comment(10,10,"yo","yo@email.com","yes");

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/comments/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Comment> call = retrofitInterface.createComment(comment);

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(!response.isSuccessful()){
                    System.out.println("====== CODE : " + response.code());
                    return;
                }
                System.out.println("RESPONSE CODE:" + response.code());
                String message = "Response code: " + response.code() + ". Post succesfully added";
                Toast.makeText(PostActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                System.out.println("====================== COMMENT POST ERROR =================");
                System.out.println(t.getMessage());
                Toast.makeText(PostActivity.this, "Error code: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
