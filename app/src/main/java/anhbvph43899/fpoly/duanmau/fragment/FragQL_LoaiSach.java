package anhbvph43899.fpoly.duanmau.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.LoaiSachDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.adapter.LoaiSachAdapter;
import anhbvph43899.fpoly.duanmau.model.LoaiSach;


public class FragQL_LoaiSach extends Fragment {
    RecyclerView rcvLoaiSach;
    FloatingActionButton fltbtnThem;
    private ArrayList<LoaiSach> list = new ArrayList<>();
    LoaiSachDAO loaiSachDAO;
    LoaiSachAdapter loaiSachAdapter;
    EditText txtTenLoai;
    public FragQL_LoaiSach() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_q_l__loai_sach, container, false);
        rcvLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        fltbtnThem = view.findViewById(R.id.fltbtnThem);
        //
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = loaiSachDAO.selectAll();
        loaiSachAdapter = new LoaiSachAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvLoaiSach.setLayoutManager(linearLayoutManager);
        rcvLoaiSach.setAdapter(loaiSachAdapter);
        loaiSachAdapter.notifyDataSetChanged();
        fltbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog_Them();
            }
        });
        return view;
    }
    public void openDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.item_themloaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenLoai = view.findViewById(R.id.txtTenLoai);
        view.findViewById(R.id.btnThem_LS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTenLoai.getText().toString();

                if(ten.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(loaiSachDAO.insert(new LoaiSach(ten))) {
                        list.clear();
                        list.addAll(loaiSachDAO.selectAll());
                        dialog.dismiss();
                        loaiSachAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}