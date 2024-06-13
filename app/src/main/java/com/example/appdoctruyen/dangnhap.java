package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdoctruyen.database.databasedoctruyen;

public class dangnhap extends AppCompatActivity {


    EditText edtTaiKhoan,edtMatKhau;
    Button btndangnhap, btnDangky;

    databasedoctruyen databasedoctruyen ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangnhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();

        databasedoctruyen = new databasedoctruyen(this);
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangnhap.this,Dangky.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                Cursor cursor = databasedoctruyen.getData();

                while (cursor.moveToNext()){
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau =cursor.getString(2);

                    if (datatentaikhoan.equals(tentaikhoan)&& datamatkhau.equals(matkhau)){
                        int phanquyen =cursor.getInt(4);
                        int idd =cursor.getInt(0);
                        String email = cursor.getString(3);
                        String tkk = cursor.getString(1);


                        Intent intent = new Intent(dangnhap.this,MainActivity.class);

                        intent.putExtra("phanq",phanquyen);
                        intent.putExtra("idd",idd);
                        intent.putExtra("email",email);
                        intent.putExtra("tentaikhoan",tkk);

                        startActivity(intent);

                    }

                }
                cursor.moveToFirst();
                cursor.close();


            }
        });
    }

    private void AnhXa() {
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangky = findViewById(R.id.dangky);
        btndangnhap = findViewById(R.id.dangnhap);

    }
}