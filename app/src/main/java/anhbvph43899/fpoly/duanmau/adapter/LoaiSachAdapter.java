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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.LoaiSachDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.model.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private final ArrayList<LoaiSach> list;
    private final Context context;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    TextView txtTenLoai, lblLoaiSach;

    public LoaiSachAdapter(ArrayList<LoaiSach> list, Context context) {
        this.list = list;
        this.context = context;
        loaiSachDAO = new LoaiSachDAO(context);
    }

    @NonNull
    @Override
    public LoaiSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvmaloai.setText(String.valueOf(list.get(position).getMaLoai()));
        holder.tvtenloai.setText(list.get(position).getTenLoai());
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSach = list.get(position);
                openDialog_del();
            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSach = list.get(position);
                OpenDialog_Update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvmaloai, tvtenloai;
        ImageButton btnsua, btnxoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmaloai =itemView.findViewById(R.id.tvmaloai);
            tvtenloai =itemView.findViewById(R.id.tvtenloai);
            btnsua =itemView.findViewById(R.id.btnsua);
            btnxoa =itemView.findViewById(R.id.btnxoa);
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
                if (loaiSachDAO.delete(loaiSach.getMaLoai())) {
                    list.clear();
                    list.addAll(loaiSachDAO.selectAll());
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
        View view = inflater.inflate(R.layout.item_sualoaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        lblLoaiSach = view.findViewById(R.id.lblMaLoai);
        txtTenLoai = view.findViewById(R.id.txtTenLoai_Up);

        txtTenLoai.setText(loaiSach.getTenLoai());
        lblLoaiSach.setText("Mã loại: " + loaiSach.getMaLoai());

        view.findViewById(R.id.btnUpdate_LS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = txtTenLoai.getText().toString();

                loaiSach.setTenLoai(tenLoai);

                if(tenLoai.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(loaiSachDAO.update(loaiSach)) {
                        list.clear();
                        list.addAll(loaiSachDAO.selectAll());
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
