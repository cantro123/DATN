package com.example.appdoctruyen;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.adapter.AdapterTruyen;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class ManAdmin extends AppCompatActivity  {
    ListView listView;
    Button buttonThem;

    ArrayList<Truyen> TruyenArrayList;

    AdapterTruyen adapterTruyen;

    databasedoctruyen databsaedoctruyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });






        listView = findViewById(R.id.lvadmin);

        buttonThem = findViewById(R.id.bttonthemtruyen);

        intList();

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = getIntent();
                int id = intent1.getIntExtra("Id",0);

                Intent intent = new Intent(ManAdmin.this,Mandangtruyen.class);
                intent.putExtra("Id",id);

                startActivity(intent);

            }
        });
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
             DialogDelete(position);

               return false;
           }
       });

    }


    //pt hien thi cua so xoa
    private void DialogDelete( int position){

        // tao doi tuong dialog
        Dialog dialog= new Dialog(this);


        // dua layout vao dialog
        dialog.setContentView(R.layout.dialogdelete);

        // tat click ra ngoai laf dong, chi click no moi dong
        dialog.setCanceledOnTouchOutside(false);


        // ANH  XA
        Button btnYes = dialog.findViewById(R.id.btyes);
        Button btnNo = dialog.findViewById(R.id.btno);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idtruyen = TruyenArrayList.get(position).getID();

                // xoa du lieu
                databsaedoctruyen.Delete(idtruyen);

                //cap nhat lai
                Intent intent= new Intent(ManAdmin.this,ManAdmin.class);
                finish();
                startActivity(intent);
                Toast.makeText(ManAdmin.this,"Xóa Truyện thành công",Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thuc hien dong dialog
                dialog.cancel();
            }
        });

        dialog.show();


    }

// gan du lieu cho lisview
    private void intList() {
        TruyenArrayList = new ArrayList<>();

        databsaedoctruyen = new databasedoctruyen(this);
        Cursor cursor1 = databsaedoctruyen.getData1();

        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);
//            String theloai = cursor1.getString(5);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,""));

            adapterTruyen = new AdapterTruyen(getApplicationContext(),TruyenArrayList);
            listView.setAdapter(adapterTruyen);

        }
        cursor1.moveToFirst();
        cursor1.close();
    }




}