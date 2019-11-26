package com.mobile.restclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

    List<Post> list;
    Context context;

    public MyListAdapter(Context context, List<Post> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list, null, true);

            holder.id = (TextView) convertView.findViewById(R.id.id);
//            holder.tvcountry = (TextView) convertView.findViewById(R.id.country);
//            holder.tvcity = (TextView) convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

//        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.id.setText("ID: "+ list.get(position).getId());
//        holder.tvcountry.setText("Country: "+dataModelArrayList.get(position).getCountry());
//        holder.tvcity.setText("City: "+dataModelArrayList.get(position).getCity());

        System.out.println("IT REACHES END OF THIS METHOD");
        return convertView;
    }

    public static class ViewHolder{

        public TextView id;
        TextView textView;


//        public ViewHolder(View itemView) {
//            this.itemView = itemView;
//            textView = itemView.findViewById(R.id.itemView);
//        }

    }
}
