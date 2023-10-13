package anhbvph43899.fpoly.duanmau.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.ThanhVienDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.adapter.ThanhVienAdapter;
import anhbvph43899.fpoly.duanmau.model.ThanhVien;


public class FragQL_ThanhVien extends Fragment {
    RecyclerView rcvThanhVien;
    FloatingActionButton fltbtnThem;
    ArrayList<ThanhVien> list = new ArrayList<>();
    ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter thanhVienAdapter;
    TextView txtTenTV, txtNamSinh;
    public FragQL_ThanhVien() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_q_l__thanh_vien, container, false);
        //
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        fltbtnThem = view.findViewById(R.id.fltbtnThem);
        thanhVienDAO = new ThanhVienDAO(getContext());
        list = thanhVienDAO.selectAll();
        thanhVienAdapter = new ThanhVienAdapter(getContext(), list);
        //
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvThanhVien.setLayoutManager(gridLayoutManager);
        rcvThanhVien.setAdapter(thanhVienAdapter);
        thanhVienAdapter.notifyDataSetChanged();
        fltbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog_Them();
            }
        });
        return view;
    }
    public void OpenDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.item_themthanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenTV = view.findViewById(R.id.txtTenTV);
        txtNamSinh = view.findViewById(R.id.txtNamSinh);
        view.findViewById(R.id.btnThem_TV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTV = txtTenTV.getText().toString();
                String namSinh = txtNamSinh.getText().toString();

                if(tenTV.isEmpty() || namSinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(thanhVienDAO.insert(new ThanhVien(tenTV, namSinh))) {
                        list.clear();
                        list.addAll(thanhVienDAO.selectAll());
                        dialog.dismiss();
                        thanhVienAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}