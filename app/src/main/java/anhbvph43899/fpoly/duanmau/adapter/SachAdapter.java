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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.LoaiSachDAO;
import anhbvph43899.fpoly.duanmau.DAO.PhieuMuonDAO;
import anhbvph43899.fpoly.duanmau.DAO.SachDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.model.LoaiSach;
import anhbvph43899.fpoly.duanmau.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<Sach> list;
    private ArrayList<LoaiSach> listLS;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    PhieuMuonDAO phieuMuonDAO;
    Sach indexsach;
    int index;
    EditText txtTenSach, txtGiaThue;
    TextView lblMaSach;
    Spinner spnLoaiSach;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        sachDAO = new SachDAO(context);
        loaiSachDAO = new LoaiSachDAO(context);
    }

    @NonNull
    @Override
    public SachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvmasach.setText(String.valueOf(list.get(position).getMaSach()));
        holder.tvtensach.setText(list.get(position).getTenSach());
        holder.tvgiaThue.setText(list.get(position).getGiaThue() + " VNĐ");
        holder.tvloaisach.setText(String.valueOf(list.get(position).getTenLoai()));
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexsach = list.get(position);
                openDialog_del();
            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexsach = list.get(position);
                OpenDialog_Update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvmasach, tvtensach, tvloaisach, tvgiaThue;
        ImageButton btnsua, btnxoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmasach = itemView.findViewById(R.id.tvmasach);
            tvtensach = itemView.findViewById(R.id.tvtensach);
            tvloaisach = itemView.findViewById(R.id.tvloaisach);
            tvgiaThue = itemView.findViewById(R.id.tvgiaThue);
            btnsua = itemView.findViewById(R.id.btnsua);
            btnxoa = itemView.findViewById(R.id.btnxoa);
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
                if (sachDAO.delete(indexsach.getMaSach())) {
                    list.clear();
                    list.addAll(sachDAO.selectAll());
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
        View view = inflater.inflate(R.layout.item_suasach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        lblMaSach = view.findViewById(R.id.lblMaSach);
        txtTenSach = view.findViewById(R.id.txtTenSach_Up);
        txtGiaThue = view.findViewById(R.id.txtGiaThue_Up);
        spnLoaiSach = view.findViewById(R.id.spnLoaiSach_Up);

        lblMaSach.setText("Mã sách: " + indexsach.getMaSach());

        ArrayList<LoaiSach> listLS = new ArrayList<>();
        listLS = loaiSachDAO.selectAll();
        ArrayList<String> loaiSachArr = new ArrayList<>();
        for (LoaiSach x: listLS) {
            loaiSachArr.add(x.getTenLoai());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, loaiSachArr);
        spnLoaiSach.setAdapter(adapter);

        for (int i = 0; i < loaiSachArr.size(); i++) {
            if (loaiSachArr.get(i).toString().equals(indexsach.getTenLoai())) {
                spnLoaiSach.setSelection(i);
            }
        }
        txtTenSach.setText(String.valueOf(indexsach.getTenSach()));
        txtGiaThue.setText(String.valueOf(indexsach.getGiaThue()));
        spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = loaiSachDAO.getMaLoai(loaiSachArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btnUpdate_S).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = txtTenSach.getText().toString();
                String giaThue = txtGiaThue.getText().toString();

                indexsach.setTenSach(tenSach);
                indexsach.setGiaThue(Integer.valueOf(giaThue));
                indexsach.setMaLoai(index);

                if(tenSach.isEmpty() || giaThue.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(giaThue.matches("\\d+") == false) {
                        Toast.makeText(context, "Giá thuê sai định dạng", Toast.LENGTH_SHORT).show();
                    } else if(Integer.valueOf(giaThue) < 0) {
                        Toast.makeText(context, "Giá thuê phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                    else if(sachDAO.update(indexsach)) {
                        list.clear();
                        list.addAll(sachDAO.selectAll());
                        phieuMuonDAO = new PhieuMuonDAO(context);
                        phieuMuonDAO.selectAll();
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
