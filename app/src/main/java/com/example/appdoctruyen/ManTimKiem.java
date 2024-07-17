package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.adapter.AdapterTruyen;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;

public class ManTimKiem extends AppCompatActivity {
    ListView listView;
    EditText edt;
    ArrayList<Truyen> TruyenArrayList;

    ArrayList<Truyen> arrayList;


    AdapterTruyen adapterTruyen;

    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_tim_kiem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.lvtimkiem);
        edt = findViewById(R.id.timkiem);

        initList();

        // bat click cho item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManTimKiem.this, ManNoiDung.class);
                String tent =arrayList.get(position).getTenTruyen();
                String noidungt = arrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                startActivity(intent);
            }
        });

        // edt search
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }


    //search
    private void filter(String text){
        // xoa du lieu mang

        arrayList.clear();

        ArrayList<Truyen> filteredList = new ArrayList<>();

        for (Truyen item : TruyenArrayList){
            if(item.getTenTruyen().toLowerCase().contains(text.toLowerCase())){
                // them item vao filteredList
                 filteredList.add(item);


                 //them vao mang

                arrayList.add(item);

            }
        }
        adapterTruyen.filterdList(filteredList);
    }

    //pt laays du lieu gan vaof lisview

    private void initList() {
        TruyenArrayList = new ArrayList<>();

        arrayList = new ArrayList<>();

        databasedoctruyen = new databasedoctruyen(this);

        Cursor cursor = databasedoctruyen.getData1();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
             String tentruyen = cursor.getString(1);
             String noidung = cursor.getString(2);
             String anh = cursor.getString(3);
             int id_tk = cursor.getInt(4);
//            String genreStr = cursor.getString(5);

             TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,""));

             arrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,""));

             adapterTruyen = new AdapterTruyen(getApplicationContext(),TruyenArrayList);
             listView.setAdapter(adapterTruyen);
        }
        cursor.moveToFirst();
        cursor.close();
    }
}