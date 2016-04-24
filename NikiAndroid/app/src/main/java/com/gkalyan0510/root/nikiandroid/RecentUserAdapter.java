package com.gkalyan0510.root.nikiandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 3/4/16.
 */

public class RecentUserAdapter extends BaseAdapter {
    private ArrayList<contacts> listData;
    private LayoutInflater layoutInflater;
    Context context;
    public RecentUserAdapter(Context aContext, ArrayList<contacts> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        context=aContext;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chat_user_item, null);
            holder = new ViewHolder();
            holder.Name  = (TextView) convertView.findViewById(R.id.getname);
            holder.img  = (ImageView) convertView.findViewById(R.id.recent_item_image);
            holder.mail = (TextView) convertView.findViewById(R.id.getemail);
            holder.phone=(TextView)convertView.findViewById(R.id.getphone);
            holder.office=(TextView)convertView.findViewById(R.id.getofficeno);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position<0)
            return convertView;
        final contacts li = listData.get(position);
        if(li.name!=null&&!li.name.equals("")){
            holder.Name.setTextColor(Color.parseColor("#444444"));
        holder.Name.setText(li.name);}

        if(li.email!=null&&!li.email.equals("")){

            holder.mail.setTextColor(Color.parseColor("#444444"));
            holder.mail.setText(li.email);
        }
        if(li.phone!=null&&!li.phone.equals("")){
        holder.phone.setTextColor(Color.parseColor("#444444"));
        holder.phone.setText(li.phone);}
            if(li.officePhone!=null&&!li.officePhone.equals("")){
        holder.office.setTextColor(Color.parseColor("#444444"));
        holder.office.setText(li.officePhone);}

        /*convertView.findViewById(R.id.linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Toast.makeText(context, intlist[Arrays.asList(strlist).indexOf(name)]+"" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("Name", li.Name);
                bundle.putInt("reg_id", li.id);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
        convertView.findViewById(R.id.recent_item_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OthersProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Toast.makeText(context, intlist[Arrays.asList(strlist).indexOf(name)]+"" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("Name", li.Name);
                bundle.putInt("reg_id", li.id);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
*/

        return convertView;
    }

    static class ViewHolder {
        ImageView img;
        TextView Name;
        TextView str;
        TextView mail;
        TextView phone;
        TextView office;

    }
}