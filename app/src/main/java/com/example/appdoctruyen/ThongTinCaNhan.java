package com.example.appdoctruyen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.TaiKhoan;

public class ThongTinCaNhan extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnUpdate;
    private databasedoctruyen databasedoctruyen;
    private TaiKhoan currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_ca_nhan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etUsername = findViewById(R.id.edtUserName);
        etEmail = findViewById(R.id.edtUserEmail);
        etPassword = findViewById(R.id.edtUserPassword);
        btnUpdate = findViewById(R.id.btnUpdateUser);

        databasedoctruyen = new databasedoctruyen(this);
        // Lấy thông tin người dùng hiện tại từ Intent

        Intent intent = getIntent();
        currentUser = new TaiKhoan(intent.getStringExtra("username"), intent.getStringExtra("email"));

        // Hiển thị thông tin người dùng hiện tại
        etUsername.setText(currentUser.getmTenTaiKhoan());
        etEmail.setText(currentUser.getmEmail());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();            }
        });
    }
    private void updateUserInfo() {
        String newUsername = etUsername.getText().toString();
        String newEmail = etEmail.getText().toString();
        String newPassword = etPassword.getText().toString();

        SQLiteDatabase db = databasedoctruyen.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", newUsername);
        values.put("email", newEmail);
        if (!newPassword.isEmpty()) {
            values.put("password", newPassword);
        }

        long result = db.update("TABLE_TAIKHOAN", values, "username=?", new String[]{currentUser.getmTenTaiKhoan()});

        if (result != -1) {
            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            // Trở về MainActivity
            Intent intent = new Intent(ThongTinCaNhan.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    }

