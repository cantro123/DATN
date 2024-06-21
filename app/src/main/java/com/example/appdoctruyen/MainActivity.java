package com.example.appdoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appdoctruyen.adapter.AdapterTruyen;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;

    ListView listView,listViewview, listViewThongTin;
    DrawerLayout drawerLayout;
    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArrayList;

    AdapterTruyen adapterTruyen;

    databasedoctruyen databasedoctruyen;





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

        databasedoctruyen = new databasedoctruyen(this);


        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra( "phang",0);
         int idd = intentpq.getIntExtra( "idd",0);
         email = intentpq.getStringExtra("email");
         tentaikhoan =intentpq.getStringExtra("tentaikhoan");




        Anhxa();
        ActionViewFlipper();
        ActionBar();

    listViewview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, ManNoiDung.class );

            String tent = TruyenArrayList.get(position).getTenTruyen();
            String noidungt = TruyenArrayList.get(position).getNoiDung();
            intent.putExtra("tentruyen", tent);
            intent.putExtra("noidung",noidungt);

            startActivity(intent);

        }
    });
    }
    private void ActionBar(){
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


        for (int i=0; i<mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //thêm ảnh từ imageview vào ViewFlipper
            viewFlipper.addView(imageView);

        }
//Thiết lập tự động chạy cho viewflipper
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.silde_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.silde_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);

    }


    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper= findViewById(R.id.viewflipper);
        listViewview = findViewById(R.id.listviewNew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArrayList = new ArrayList<>();
        Cursor cursor1 = databasedoctruyen.getData1();
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen= cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen = new AdapterTruyen(getApplicationContext(),TruyenArrayList);
             listViewview.setAdapter(adapterTruyen );
        }
        cursor1.moveToFirst();
        cursor1.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();
        if (itemId == R.id.menu1){
            Intent intent = new Intent(MainActivity.this, ManTimKiem.class);
            startActivity(intent);
        }





        return super.onOptionsItemSelected(item);
    }
}