package anhbvph43899.fpoly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import anhbvph43899.fpoly.duanmau.DAO.ThuThuDAO;
import anhbvph43899.fpoly.duanmau.R;

public class ManHinhDangNhap extends AppCompatActivity {
    CheckBox chkLuuMatKhau;
    ThuThuDAO thuThuDAO;
    EditText edtTaiKhoan, edtMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
        thuThuDAO = new ThuThuDAO(this);
        ImageButton btnDangNhap = findViewById(R.id.btnDangNhap);
        chkLuuMatKhau = findViewById(R.id.chkLuuMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtTaiKhoan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tk = s.toString().trim();
                if (tk.isEmpty()) {
                    edtTaiKhoan.setError("Vui lòng nhập tài khoản");
                } else {
                    edtTaiKhoan.setError("");
                }
            }
        });
        edtMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String mk = s.toString().trim();
                if (mk.isEmpty()) {
                    edtMatKhau.setError("Vui lòng nhập mật khẩu");
                } else {
                    edtMatKhau.setError("");
                    btnDangNhap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String username = edtTaiKhoan.getText().toString();
                            String password = edtMatKhau.getText().toString();
                            if (thuThuDAO.checklogin( username, password)) {
                                Toast.makeText(ManHinhDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                luuMatKhau( username, password, chkLuuMatKhau.isChecked());
                                Intent intent = new Intent(ManHinhDangNhap.this, GiaoDienMain.class);
                                intent.putExtra("maTT", username);
                                startActivity(intent);
                            } else {
                                edtTaiKhoan.setError(" ");
                                edtMatKhau.setError("Tài khoản hoặc mật khẩu không chính xác");
                            }
                        }
                    });
                }
            }
        });
    }
    public void luuMatKhau(String user, String pass, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putString("User", user);
            editor.putString("Pass", pass);
            editor.putBoolean("Luu", status);
        }
        editor.commit();
    }
    public void chkMatKhau() {
        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan.txt", MODE_PRIVATE);
        boolean chk = sharedPreferences.getBoolean("Luu", false);
        if (chk) {
            edtTaiKhoan.setText(sharedPreferences.getString("User", ""));
            edtMatKhau.setText(sharedPreferences.getString("Pass", ""));
        }
    }
}