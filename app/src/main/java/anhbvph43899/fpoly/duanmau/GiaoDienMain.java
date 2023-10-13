package anhbvph43899.fpoly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.ThuThuDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.fragment.FragQL_LoaiSach;
import anhbvph43899.fpoly.duanmau.fragment.FragQL_PhieuMuon;
import anhbvph43899.fpoly.duanmau.fragment.FragQL_Sach;
import anhbvph43899.fpoly.duanmau.fragment.FragQL_ThanhVien;
import anhbvph43899.fpoly.duanmau.fragment.Fragment_DoanhThu;
import anhbvph43899.fpoly.duanmau.fragment.Fragment_Top10;
import anhbvph43899.fpoly.duanmau.model.ThuThu;

public class GiaoDienMain extends AppCompatActivity {
DrawerLayout drawerLayout;
Toolbar toolbar;
String maTT, matKhauMoi;
NavigationView navigationView;
ArrayList<ThuThu> list = new ArrayList<>();
ThuThuDAO thuThuDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        thuThuDAO = new ThuThuDAO(this);
        getSupportActionBar().setTitle("Quan Ly Phieu Muon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menunav);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.item_qlpm) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new FragQL_PhieuMuon();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_qlls) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new FragQL_LoaiSach();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_qls) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new FragQL_Sach();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_qltv) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new FragQL_ThanhVien();
                    replaceFrg(fragment);
                } else if (item.getItemId() == R.id.item_top10) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_Top10();
                    replaceFrg(fragment);
                }else if (item.getItemId() == R.id.item_doanhthu) {
                    toolbar.setTitle(item.getTitle());
                    fragment = new Fragment_DoanhThu();
                    replaceFrg(fragment);
            }else if (item.getItemId() == R.id.item_themthuthu) {
                OpenDialog_ThemTT();
                }else if (item.getItemId() == R.id.item_doimatkhau) {
                    OpenDialog_DoiMK();
                }else if (item.getItemId() == R.id.item_dangxuat) {
                    OpenDialog_DangXuat();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        FragQL_PhieuMuon fragQL_phieuMuon = new FragQL_PhieuMuon();
        replaceFrg(fragQL_phieuMuon);
    }

    public void replaceFrg(Fragment frg) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout, frg).commit();
    }
    public void OpenDialog_DangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("WARNING");
        builder.setMessage("Bạn có muốn đăng xuất không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GiaoDienMain.this, ManHinhDangNhap.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    public void OpenDialog_ThemTT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.item_themthuthu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText txtMaTT = view.findViewById(R.id.txtMaTT);
        EditText txtTenTT = view.findViewById(R.id.txtTenTT);
        EditText txtMatKhau = view.findViewById(R.id.txtMatKhau);
        EditText txtMatKhau_2 = view.findViewById(R.id.txtMatKhau_2);

        view.findViewById(R.id.btnThem_TT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTT = txtMaTT.getText().toString();
                String tenTT = txtTenTT.getText().toString();
                String matKhau = txtMatKhau.getText().toString();
                String matKhau2 = txtMatKhau_2.getText().toString();

                if (maTT.isEmpty() || tenTT.isEmpty() || matKhau.isEmpty() || matKhau2.isEmpty()) {
                    Toast.makeText(GiaoDienMain.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (thuThuDAO.checkMaTT(maTT)) {
                        Toast.makeText(GiaoDienMain.this, "Mã thủ thư đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else if (!matKhau.equals(matKhau2)) {
                        Toast.makeText(GiaoDienMain.this, "Mật khẩu không khớp nhau", Toast.LENGTH_SHORT).show();
                    } else if (thuThuDAO.insert(new ThuThu(maTT, tenTT, matKhau, 1))) {
                        thuThuDAO.selectAll();
                        dialog.dismiss();
                        Toast.makeText(GiaoDienMain.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GiaoDienMain.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void OpenDialog_DoiMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.item_doimk, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText txtMatKhauCu = view.findViewById(R.id.txtMatKhauCu);
        EditText txtMatKhauMoi = view.findViewById(R.id.txtMatKhauMoi);
        EditText txtMatKhauXacNhan = view.findViewById(R.id.txtMatKhauXacNhan);


        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maTT = getIntent().getStringExtra("maTT");
                String matKhauCu = txtMatKhauCu.getText().toString().trim();
                matKhauMoi = txtMatKhauMoi.getText().toString().trim();
                String matKhauXacNhan = txtMatKhauXacNhan.getText().toString().trim();

                if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || matKhauXacNhan.isEmpty()) {
                    Toast.makeText(GiaoDienMain.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (thuThuDAO.checklogin( maTT, matKhauCu)) {
                        if (matKhauMoi.equals(matKhauXacNhan)) {
                            openDialog_XacNhan();
                        } else {
                            Toast.makeText(GiaoDienMain.this, "Mật khẩu mới không khớp nhau", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GiaoDienMain.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void openDialog_XacNhan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Warning");
        builder.setMessage("Bạn có chắc chắn muốn đổi mật khẩu ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (thuThuDAO.doiMatKhau( maTT, matKhauMoi)) {
                    thuThuDAO.selectAll();
                    dialog.dismiss();
                    Toast.makeText(GiaoDienMain.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GiaoDienMain.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}