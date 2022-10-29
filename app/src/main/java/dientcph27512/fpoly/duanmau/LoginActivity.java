package dientcph27512.fpoly.duanmau;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DAO.ThuThuDAO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private EditText user;
    private EditText pass;
    private Button dangNhap;
    private LinearLayout linearLayout2;
    private ImageView showpass;
    private CheckBox save;
    private ThuThuDAO thuThuDAO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        manHinChao();
        showPass();
        savePass();
        read();
        btnlogin();
    }

    public void anhXa() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        dangNhap = (Button) findViewById(R.id.dang_nhap);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        showpass = (ImageView) findViewById(R.id.showpass);
        save = findViewById(R.id.luu);
    }

    public void manHinChao() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout2.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

    public void btnlogin() {
        dangNhap.setOnClickListener(v -> {
            thuThuDAO = new ThuThuDAO(LoginActivity.this);
            if (thuThuDAO.checkLogin(user.getText().toString(), pass.getText().toString()) > 0) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user",thuThuDAO.getID(user.getText().toString()).getHoTen());
                intent.putExtra("id",thuThuDAO.getID(user.getText().toString()).getMaTT());
                startActivity(intent);
                finish();
            }
        });
    }

    public void showPass() {
        showpass.setOnClickListener(v -> {
            if (pass.getInputType() == 129) {
                pass.setInputType(1);
            } else {
                pass.setInputType(129);
            }
        });
    }

    public void save() {
        if (save.isChecked() == true) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String userName = user.getText().toString();
            String password = pass.getText().toString();
            editor.putString("user", userName);
            editor.putString("pass", password);
            editor.commit();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", null);
            editor.putString("pass", null);
            editor.commit();
        }
    }

    public void read() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String userName = sharedPreferences.getString("user", null);
        String password = sharedPreferences.getString("pass", null);
        if (userName != null && password != null) {
            user.setText(userName);
            pass.setText(password);
            save.setChecked(true);
        }
    }

    public void savePass() {
        save.setOnClickListener(v -> {
            save();
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát");
        builder.setMessage("Bạn Có Muốn Thoát");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}