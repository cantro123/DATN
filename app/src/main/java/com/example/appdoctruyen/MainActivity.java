package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appdoctruyen.adapter.AdapterTruyen;
import com.example.appdoctruyen.adapter.adapterchuyenmuc;
import com.example.appdoctruyen.adapter.adapterthongtin;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.TaiKhoan;
import com.example.appdoctruyen.model.Truyen;
import com.example.appdoctruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;

    ListView listView, listViewview, listViewThongTin;
    DrawerLayout drawerLayout;
    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArrayList;

    AdapterTruyen adapterTruyen;
    ArrayList<chuyenmuc> chuyenmucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;

    databasedoctruyen databasedoctruyen;


    adapterthongtin adapterthongtin;
    adapterchuyenmuc adapterchuyenmuc;
    Spinner spinnerGenre;
    ArrayAdapter<String> genreAdapter;
    List<String> genres;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Khởi tạo Spinner
        spinnerGenre = findViewById(R.id.spinnerGenre);
         genres = Arrays.asList("All", "Tiểu thuyết"); // Thêm các thể loại của bạn ở đây
        genreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        TruyenArrayList = new ArrayList<>();
        adapterTruyen = new AdapterTruyen(this, TruyenArrayList);
        ListView listViewview = findViewById(R.id.listviewNew);
        listViewview.setAdapter(adapterTruyen);


        databasedoctruyen = new databasedoctruyen(this);



        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq", 0);
        int idd = intentpq.getIntExtra("idd", 0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");


        Anhxa();
        ActionViewFlipper();
        ActionBar();
        loadAllStories();



        // Thiết lập sự kiện khi chọn thể loại
        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = genres.get(position);
                if (selectedGenre.equals("All")) {
                    loadAllStories();
                } else {

                        loadStoriesByGenre(selectedGenre);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì cả
            }
        });

        listViewview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ManNoiDung.class);

                String tent = TruyenArrayList.get(position).getTenTruyen();
                String noidungt = TruyenArrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                intent.putExtra("noidung", noidungt);

                startActivity(intent);

            }
        });


        // bắt click cho list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //dang bai
                if (position == 0){
                    if (i == 2 ){

                        Intent intent = new Intent(MainActivity.this,ManAdmin.class);
                        // gui id tai khoan qua man admin
                        intent.putExtra("Id",idd);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Bạn không có quyền đăng bài",Toast.LENGTH_SHORT).show();
                        Log.e("Đăng bài : ", "Bạn không có quyền");
                    }
                }
                //neu nhan vo thong tin chuyen qua man hinh thong tin
                else if (position == 1 ){
                    Intent intent = new Intent(MainActivity.this,ManThongTin.class);
                    startActivity(intent );
                }

                //dang xuat
                else if (position==2){
                    finish();
                }
                // Cập nhật thông tin cá nhân
                else if (position == 3) { // Đảm bảo rằng vị trí của mục này đúng
                    Intent intent = new Intent(MainActivity.this, ThongTinCaNhan.class);
                    intent.putExtra("username", tentaikhoan);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadAllStories() {
        TruyenArrayList.clear();
        Cursor cursor = databasedoctruyen.getData1(); // Lấy tất cả truyện

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);
            String theloai = cursor.getString(5);
            TruyenArrayList.add(new Truyen(id, tentruyen, noidung, anh, id_tk,theloai));
        }
        cursor.close();
        adapterTruyen.notifyDataSetChanged();

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }


    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://i.truyenvua.com/ebook/190x247/dao-hai-tac_1552224567.jpg?gt=hdfgdfg&mobile=2");
        mangquangcao.add("https://nxbhcm.com.vn/Image/Biasach/toanchuccaothut1.jpg");
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSBZh2UpzLj8efScUh-KXZcfKbIaJeF4P7uQ&s");
        mangquangcao.add("https://popsimg.akamaized.net/api/v2/containers/file2/cms_thumbnails/tocrushcaumatroi_thumb_640x960_1_-0fa146aa5978-1680778788597-jGoUuUgn.jpg?v=0&maxW=320&format=jpg");


        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //thêm ảnh từ imageview vào ViewFlipper
            viewFlipper.addView(imageView);

        }
//Thiết lập tự động chạy cho viewflipper
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silde_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silde_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);

    }


    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewview = findViewById(R.id.listviewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArrayList = new ArrayList<>();
        Cursor cursor1 = databasedoctruyen.getData1();
        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);
            String theloai =cursor1.getString(5);
            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk,theloai));

            adapterTruyen = new AdapterTruyen(getApplicationContext(), TruyenArrayList);
            listViewview.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();



        // thông tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));

        adapterthongtin = new adapterthongtin(this,R.layout.navigation,taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        // chuyen muc
        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng Bài", R.drawable.ic_add));
        chuyenmucArrayList.add(new chuyenmuc("Thông tin",R.drawable.thongtin));
        chuyenmucArrayList.add(new chuyenmuc(" Đăng xuất",R.drawable.icon_dangxuat));
        chuyenmucArrayList.add(new chuyenmuc("Cập nhật thông tin",R.drawable.ic_mat));

        adapterchuyenmuc = new adapterchuyenmuc(this,R.layout.chuyenmuc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.menu1) {
            Intent intent = new Intent(MainActivity.this, ManTimKiem.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void loadStoriesByGenre(String Theloai) {

        TruyenArrayList.clear();
        Cursor cursor = databasedoctruyen.getData3(Theloai); // Lấy truyện theo thể loại
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tentruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);
            String theloai = cursor.getString(5);
            TruyenArrayList.add(new Truyen(id, tentruyen, noidung, anh, id_tk, theloai));
        }
        cursor.close();
        adapterTruyen.notifyDataSetChanged();
    }

}

