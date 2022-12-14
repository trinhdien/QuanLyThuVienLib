package dientcph27512.fpoly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.Fragment.DoanhThuFragment;
import dientcph27512.fpoly.duanmau.Fragment.DoiMatKhauFragment;
import dientcph27512.fpoly.duanmau.Fragment.NguoiDungFragment;
import dientcph27512.fpoly.duanmau.Fragment.QuanLiLoaiSachFragment;
import dientcph27512.fpoly.duanmau.Fragment.QuanLiNhanVienFragment;
import dientcph27512.fpoly.duanmau.Fragment.QuanLiPhieuMuonFragment;
import dientcph27512.fpoly.duanmau.Fragment.QuanLiSachFragment;
import dientcph27512.fpoly.duanmau.Fragment.Top10Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerlayout;
    private FrameLayout frameLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView name;
    private String thuthu;
    private String maTT;
    public String getThuthu() {
        return thuthu;
    }

    public String getMaTT() {
        return maTT;
    }

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        View header = navigationView.inflateHeaderView(R.layout.header_navigationview);
        name = header.findViewById(R.id.account);
        Intent intent = getIntent();
        thuthu = intent.getStringExtra("user");
        maTT = intent.getStringExtra("id");
        name.setText("Xin Ch??o: " + thuthu);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, 0, 0);
        toggle.syncState();
        replace(new QuanLiPhieuMuonFragment());
        setTitle("Qu???n L?? Phi???u M?????n");
        navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(true);
        if(!thuthu.equals("Tr???nh C??ng ??i???n")){
            navigationView.getMenu().findItem(R.id.themUser).setVisible(false);
        }
        Log.d("zzzz", "onCreate: " + intent.getStringExtra("user").equals("Tr???nh C??ng ??i???n"));
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quanliphieumuon:
                replace(new QuanLiPhieuMuonFragment());
                setTitle("Qu???n L?? Phi???u M?????n");
                break;
            case R.id.quanliloaisach:
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                setTitle("Qu???n L?? Lo???i S??ch");
                replace(new QuanLiLoaiSachFragment());
                break;
            case R.id.quanlisach:
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                setTitle("Qu???n L?? S??ch");
                replace(new QuanLiSachFragment());
                break;
            case R.id.quanlithanhvien:
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                setTitle("Qu???n L?? Th??nh Vi??n");
                replace(new QuanLiNhanVienFragment());
                break;
            case R.id.top10:
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                setTitle("Top 10");
                replace(new Top10Fragment());
                break;
            case R.id.doanhthu:
                setTitle("Doanh Thu");
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                replace(new DoanhThuFragment());
                break;
            case R.id.doimk:
                setTitle("?????i M???t Kh???u");
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                replace(new DoiMatKhauFragment());
                break;
            case R.id.dangxuat:
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.themUser:
                setTitle("Th??m Ng?????i D??ng");
                navigationView.getMenu().findItem(R.id.quanliphieumuon).setChecked(false);
                replace(new NguoiDungFragment());
                break;

        }
        drawerlayout.close();
        return true;
    }

    public void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerlayout.isOpen() == true){
            drawerlayout.close();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("????ng Xu???t");
            builder.setMessage("B???n C?? Mu???n ????ng Xu???t");
            builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }
}