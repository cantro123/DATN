package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.HashMap;
import java.util.Map;

public class   Mandangtruyen extends AppCompatActivity {


    EditText edtTenTruyen,edtNoiDung,edtAnh;
    Spinner spinner;
    Button btnDangBai;

    databasedoctruyen databasedoctruyen;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mandangtruyen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtAnh = findViewById(R.id.dbimg);
        edtTenTruyen = findViewById(R.id.dbtentruyen);
        edtNoiDung =  findViewById(R.id.dbnoidung);
        spinner = findViewById(R.id.dbtheloai);
        btnDangBai = findViewById(R.id.dbdangbai);

        databasedoctruyen = new databasedoctruyen(this);
        // Tạo danh sách các thể loại
//        Map<Integer, String> genres = new HashMap<Integer, String>();
//        genres.put(1, "Tiểu Thuyết");
        String[] genres = { "Tiểu Thuyết"};
        // Tạo ArrayAdapter cho Spinner
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Thiết lập Adapter cho Spinner
        spinner.setAdapter(genreAdapter);




        // button dang bai
        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentruyen =edtTenTruyen.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                String  img = edtAnh.getText().toString();
                String theloai=spinner.getSelectedItem().toString();// lâấy thể loại từ spinner

                Truyen truyen = CreatTruyen();


                if(tentruyen.equals("") || noidung.equals("") || img.equals("")){
                    Toast.makeText(Mandangtruyen.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    Log.e("ERR :", "Nhập đầy đủ thông tin ");

                }
                // neu nhap day du se them du lieu
                else {
                    databasedoctruyen.AddTruyen(truyen);
                    // chuyen qua manadmin va cap nhat
                    Intent intent = new Intent(Mandangtruyen.this,ManAdmin.class);
                    finish();
                    startActivity(intent);

                }
            }
        });



    }
    // pt tao truyen
    private Truyen CreatTruyen(){
     String tentruyen =edtTenTruyen.getText().toString();
     String noidung = edtNoiDung.getText().toString();
     String  img = edtAnh.getText().toString();
     String theloai ="1";

        Intent intent = getIntent();

        int id = intent.getIntExtra( "Id",0);

        Truyen truyen = new Truyen(tentruyen,noidung,img,id,theloai);

        return truyen;
    }
    private Truyen CreatTruyen(String tentruyen, String noidung, String img, String theloai) {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        return new Truyen(tentruyen, noidung, img, id, theloai);
    }
}