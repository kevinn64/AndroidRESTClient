package com.mobile.restclient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserPostsListAdapter extends BaseAdapter {
    List<Post> posts;
    Context context;

    public UserPostsListAdapter(Context context, List<Post> posts){
        this.posts = posts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_userpostslist,parent,false);
        }
        TextView txtId = convertView.findViewById(R.id.id);
        TextView userId = convertView.findViewById(R.id.userId);
        TextView title = convertView.findViewById(R.id.title);
        TextView body = convertView.findViewById(R.id.body);

        final Post thisPost = posts.get(position);

        txtId.setText("id: " + String.valueOf(thisPost.getId()));
        userId.setText("userId: " + String.valueOf(thisPost.getUserId()));
        title.setText("title: " + thisPost.getTitle());
        body.setText("body: " + thisPost.getText());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
//                Toast.makeText(context, String.valueOf(thisPost.getId()), Toast.LENGTH_SHORT).show();
//                System.out.println(thisPost.getId());
//
//                Intent intent = new Intent(context, PostActivity.class);
//                intent.putExtra("postId", thisPost.getId());
//                intent.putExtra("userId", thisPost.getUserId());
//                intent.putExtra("postTitle", thisPost.getTitle());
//                intent.putExtra("blogBody", thisPost.getText());
//                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
