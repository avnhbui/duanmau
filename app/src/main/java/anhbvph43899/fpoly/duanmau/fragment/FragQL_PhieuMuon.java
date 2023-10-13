package anhbvph43899.fpoly.duanmau.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import anhbvph43899.fpoly.duanmau.DAO.PhieuMuonDAO;
import anhbvph43899.fpoly.duanmau.DAO.SachDAO;
import anhbvph43899.fpoly.duanmau.DAO.ThanhVienDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.adapter.PhieuMuonAdapter;
import anhbvph43899.fpoly.duanmau.model.PhieuMuon;
import anhbvph43899.fpoly.duanmau.model.Sach;
import anhbvph43899.fpoly.duanmau.model.ThanhVien;


public class FragQL_PhieuMuon extends Fragment {
    RecyclerView rcvPhieuMuon;
    FloatingActionButton flt_btn_Them;
    ArrayList<PhieuMuon> list = new ArrayList<>();
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    Spinner spnThanhVien, spnSach;
    EditText txtNgayThue, txtTienThue;
    ImageButton btnNgayThue;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    int indexS, indexTV;
    public FragQL_PhieuMuon() {
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
        View view = inflater.inflate(R.layout.fragment_frag_q_l__phieu_muon, container, false);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);
        flt_btn_Them = view.findViewById(R.id.btnThemPM);
        //
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.selectAll();
        //
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvPhieuMuon.setLayoutManager(linearLayoutManager);
        rcvPhieuMuon.setAdapter(phieuMuonAdapter);
        phieuMuonAdapter.notifyDataSetChanged();
        //
        flt_btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog_Them();
            }
        });
        return view;
    }
    public void OpenDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.item_themphieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtNgayThue = view.findViewById(R.id.txtNgayThue);
        txtTienThue = view.findViewById(R.id.txtTienThue);
        spnThanhVien = view.findViewById(R.id.spnThanhVien);
        btnNgayThue = view.findViewById(R.id.btnNgayThue);
        spnSach = view.findViewById(R.id.spnSach);

        sachDAO = new SachDAO(getContext());
        thanhVienDAO = new ThanhVienDAO(getContext());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<Sach> listS = new ArrayList<>();
        listS = sachDAO.selectAll();
        ArrayList<String> sachArr = new ArrayList<>();
        ArrayList<String> tienThueArr = new ArrayList<>();
        for (Sach x: listS) {
            sachArr.add(x.getTenSach());
            tienThueArr.add(String.valueOf(x.getGiaThue()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sachArr);
        spnSach.setAdapter(adapter);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexS = sachDAO.getMaS(sachArr.get(position).toString());
                txtTienThue.setText(String.valueOf(sachDAO.getTienThue(sachArr.get(position).toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        ArrayList<ThanhVien> listTV = new ArrayList<>();
        listTV = thanhVienDAO.selectAll();
        ArrayList<String> thanhVienArr = new ArrayList<>();
        for (ThanhVien x: listTV) {
            thanhVienArr.add(x.getTenTV());
        }
        ArrayAdapter<String> adaptertv = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, thanhVienArr);
        spnThanhVien.setAdapter(adaptertv);
        spnThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexTV = thanhVienDAO.getMaTV(thanhVienArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnNgayThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog getDay = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtNgayThue.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, year, month, day);
                getDay.show();
            }
        });
        txtNgayThue.setText(String.format("%d/%d/%d", day, month + 1, year));
        view.findViewById(R.id.btnThem_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayThue = txtNgayThue.getText().toString();
                String tienThue = txtTienThue.getText().toString();
                if (thanhVienArr.isEmpty() || sachArr.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(phieuMuonDAO.insert(new PhieuMuon("TT01", indexTV, indexS, Integer.valueOf(tienThue), ngayThue, 1))) {
                        list.clear();
                        list.addAll(phieuMuonDAO.selectAll());
                        dialog.dismiss();
                        phieuMuonAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}