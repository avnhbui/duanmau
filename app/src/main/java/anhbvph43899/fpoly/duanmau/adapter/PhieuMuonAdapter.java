package anhbvph43899.fpoly.duanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.PhieuMuonDAO;
import anhbvph43899.fpoly.duanmau.DAO.SachDAO;
import anhbvph43899.fpoly.duanmau.DAO.ThanhVienDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.model.PhieuMuon;
import anhbvph43899.fpoly.duanmau.model.Sach;
import anhbvph43899.fpoly.duanmau.model.ThanhVien;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<PhieuMuon> list;
    PhieuMuonDAO phieuMuonDAO;
    Spinner spnThanhVien, spnSach;
    EditText txtNgayThue, txtTienThue;
    CheckBox chkTrangThai;
    TextView lblMaPM;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    PhieuMuon index;
    int indexS;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        phieuMuonDAO = new PhieuMuonDAO(context);
    }

    @NonNull
    @Override
    public PhieuMuonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvmaphieu.setText(String.valueOf(list.get(position).getMaPhieuMuon()));
        holder.tvtenTV.setText(list.get(position).getTenTV());
        holder.tvSach.setText(list.get(position).getTenSach());
        holder.tvgiaThue.setText(list.get(position).getTienThue() + " VNĐ");
        holder.tvngayThue.setText(list.get(position).getNgayMuon());
        if (list.get(position).getTrangThai() == 0) {
            holder.tvstatus.setText("Đã trả sách");
            holder.tvstatus.setTextColor(Color.argb(255,22,255,22));
        } else {
            holder.tvstatus.setText("Chưa trả sách");
            holder.tvstatus.setTextColor(Color.argb(255 ,255 ,22,22));
        }
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = list.get(position);
                openDialog_del();
            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = list.get(position);
                OpenDialog_Update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvmaphieu, tvtenTV,tvSach, tvgiaThue, tvngayThue,tvstatus;
        ImageButton btnsua, btnxoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmaphieu = itemView.findViewById(R.id.tvmaphieu);
            tvtenTV = itemView.findViewById(R.id.tvtenTV);
            tvSach = itemView.findViewById(R.id.tvSach);
            tvgiaThue = itemView.findViewById(R.id.tvgiaThue);
            tvngayThue = itemView.findViewById(R.id.tvngayThue);
            tvstatus = itemView.findViewById(R.id.tvstatus);
            btnxoa = itemView.findViewById(R.id.btnxoa);
            btnsua = itemView.findViewById(R.id.btnsua);
        }
    }
    public void openDialog_del() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Warning");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (phieuMuonDAO.delete(index.getMaPhieuMuon())) {
                    list.clear();
                    list.addAll(phieuMuonDAO.selectAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "XÓa thất bại", Toast.LENGTH_SHORT).show();
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
    public void OpenDialog_Update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_suaphieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        lblMaPM = view.findViewById(R.id.lblMaPM);
        txtNgayThue = view.findViewById(R.id.txtNgayThue_up);
        txtTienThue = view.findViewById(R.id.txtTienThue_up);
        spnThanhVien = view.findViewById(R.id.spnThanhVien_up);
        chkTrangThai = view.findViewById(R.id.chkTrangThai);
        spnSach = view.findViewById(R.id.spnSach_up);

        sachDAO = new SachDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);

        ArrayList<Sach> listS = new ArrayList<>();
        listS = sachDAO.selectAll();
        ArrayList<String> sachArr = new ArrayList<>();
        ArrayList<String> tienThueArr = new ArrayList<>();
        for (Sach x: listS) {
            sachArr.add(x.getTenSach());
            tienThueArr.add(String.valueOf(x.getGiaThue()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sachArr);
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
        ArrayAdapter<String> adaptertv = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, thanhVienArr);
        spnThanhVien.setAdapter(adaptertv);

        for (int i = 0; i < thanhVienArr.size(); i++) {
            if (thanhVienArr.get(i).equals(thanhVienDAO.getTenTV(index.getMaTV()))) {
                spnThanhVien.setSelection(i);
            }
        }
        txtNgayThue.setText(phieuMuonDAO.getNgayThue(String.valueOf(index.getMaPhieuMuon())));
        if (index.getTrangThai() == 0) {
            chkTrangThai.setChecked(true);
        } else {
            chkTrangThai.setChecked(false);
        }
        lblMaPM.setText("Mã phiếu: " + index.getMaPhieuMuon());
        view.findViewById(R.id.btnUpdate_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tienThue = txtTienThue.getText().toString();
                index.setMaSach(indexS);
                index.setTienThue(Integer.valueOf(tienThue));
                if (chkTrangThai.isChecked()) {
                    index.setTrangThai(0);
                } else {
                    index.setTrangThai(1);
                }
                if (thanhVienArr.isEmpty() || sachArr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(phieuMuonDAO.update(index)) {
                        list.clear();
                        list.addAll(phieuMuonDAO.selectAll());
                        dialog.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

