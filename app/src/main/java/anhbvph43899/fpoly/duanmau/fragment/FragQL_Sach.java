package anhbvph43899.fpoly.duanmau.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.LoaiSachDAO;
import anhbvph43899.fpoly.duanmau.DAO.SachDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.adapter.SachAdapter;
import anhbvph43899.fpoly.duanmau.model.LoaiSach;
import anhbvph43899.fpoly.duanmau.model.Sach;


public class FragQL_Sach extends Fragment {
    RecyclerView rcvSach;
    FloatingActionButton fltbtnThem;
    ArrayList<Sach> list = new ArrayList<>();
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    SachAdapter sachAdapter;
    EditText txtTenSach, txtGiaThue;
    Spinner spnLoaiSach;
    int index;
    public FragQL_Sach() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_q_l__sach, container, false);
        rcvSach = view.findViewById(R.id.rcvSach);
        fltbtnThem = view.findViewById(R.id.fltbtnThem);
        sachDAO = new SachDAO(getContext());
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = sachDAO.selectAll();
        sachAdapter = new SachAdapter(getContext(), list);
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSach.setLayoutManager(linearLayoutManager);
        rcvSach.setAdapter(sachAdapter);
        sachAdapter.notifyDataSetChanged();
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
        View view = getLayoutInflater().inflate(R.layout.item_themsach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenSach = view.findViewById(R.id.txtTenSach);
        txtGiaThue = view.findViewById(R.id.txtGiaThue);
        spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        ArrayList<LoaiSach> listLS = new ArrayList<>();
        listLS = loaiSachDAO.selectAll();
        ArrayList<String> loaiSachArr = new ArrayList<>();
        for (LoaiSach x: listLS) {
            loaiSachArr.add(x.getTenLoai());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, loaiSachArr);
        spnLoaiSach.setAdapter(adapter);
        spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = loaiSachDAO.getMaLoai(loaiSachArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view.findViewById(R.id.btnThem_S).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = txtTenSach.getText().toString();
                String giaThue = txtGiaThue.getText().toString();

                if(tenSach.isEmpty() || giaThue.isEmpty() || loaiSachArr.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(giaThue.matches("\\d+") == false) {
                        Toast.makeText(getContext(), "Giá tiền sai định dạng", Toast.LENGTH_SHORT).show();
                    } else if(Integer.valueOf(giaThue) < 0) {
                        Toast.makeText(getContext(), "Giá tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                    else if(sachDAO.insert(new Sach(tenSach, Integer.parseInt(giaThue), index))) {
                        list.clear();
                        list.addAll(sachDAO.selectAll());
                        dialog.dismiss();
                        sachAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}