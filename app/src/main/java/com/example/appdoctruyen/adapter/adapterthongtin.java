package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.TaiKhoan;

import java.util.List;

public class adapterthongtin extends BaseAdapter {
    private Context context;
    private int layout;
    private List<TaiKhoan> taiKhoanlist;

    public adapterthongtin(Context context, int layout, List<TaiKhoan> taiKhoanlist) {
        this.context = context;
        this.layout = layout;
        this.taiKhoanlist = taiKhoanlist;
    }

    @Override
    public int getCount() {

        return taiKhoanlist.size();
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
        convertView= inflater.inflate(layout,null);
        TextView txtTenTaiKhoan = (TextView) convertView.findViewById   (R.id.Textname);
        TextView txtEmail = (TextView) convertView.findViewById(R.id.textgmail);

        TaiKhoan taiKhoan = taiKhoanlist.get(position);
        txtTenTaiKhoan.setText(taiKhoan.getmTenTaiKhoan());
        txtEmail.setText(taiKhoan.getmEmail());

        return convertView;
    }
}
