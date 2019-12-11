package com.mobile.restclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {

    List<Comment> comments;
    Context context;

    public CommentListAdapter(Context context, List<Comment> comments){
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_list,parent,false);
        }
        TextView email = convertView.findViewById(R.id.id);
        TextView commentTitle = convertView.findViewById(R.id.commentTitle);
        TextView commentBody = convertView.findViewById(R.id.commentBody);

        final Comment thisComment = comments.get(position);

        email.setText("email: " + thisComment.getEmail());
        commentTitle.setText("title: " + thisComment.getName());
        commentBody.setText("body: " + thisComment.getBody());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(context, String.valueOf(thisComment.getId()), Toast.LENGTH_SHORT).show();
                System.out.println(thisComment.getId());
            }
        });

        return convertView;
    }
}
