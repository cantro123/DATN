package com.example.appdoctruyen.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.ThongTinCaNhan;
import com.example.appdoctruyen.model.chuyenmuc;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterchuyenmuc extends BaseAdapter {

    Button btnsua;

    private int layout;
    private List<chuyenmuc> chuyenmucList;

    Activity context;



    public adapterchuyenmuc(Activity context, int layout, List<chuyenmuc> chuyenmucList) {
        this.context = context;
        this.layout = layout;
        this.chuyenmucList = chuyenmucList;
    }

    @Override
    public int getCount() {

        return chuyenmucList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView =inflater.inflate(layout,null);

        ImageView img =(ImageView) convertView.findViewById(R.id.imgchuyenmuc);

        TextView txt =(TextView) convertView.findViewById(R.id.tvTenchuyenmuc);



        chuyenmuc cm= chuyenmucList.get(position);

        txt.setText(cm.getTenchuyenmuc());
        img.setImageResource(cm.getHinhanhchuyenmuc());

//        Picasso.get().load(cm.getHinhanhchuyenmuc()).placeholder(R.drawable.ic_tai_ve).error(R.drawable.ic_image).into(img);


        return convertView;
    }


}
