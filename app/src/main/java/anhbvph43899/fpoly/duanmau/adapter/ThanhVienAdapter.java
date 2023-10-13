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

import anhbvph43899.fpoly.duanmau.DAO.ThanhVienDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ThanhVien> list;
    ThanhVienDAO thanhVienDAO;
    ThanhVien tv;
    TextView txtTenTV, txtNamSinh, lblMaTV;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        thanhVienDAO = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public ThanhVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvmatv.setText("Mã thành viên: " + String.valueOf(list.get(position).getMaTV()));
        holder.tvtentv.setText(list.get(position).getTenTV());
        holder.tvnamsinh.setText("Ngày sinh: " + list.get(position).getNamSinh());
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv = list.get(position);
                openDialog_del();
            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv = list.get(position);
                OpenDialog_Update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvmatv, tvtentv, tvnamsinh;
        ImageButton btnsua, btnxoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmatv = itemView.findViewById(R.id.tvmatv);
            tvtentv = itemView.findViewById(R.id.tvtentv);
            tvnamsinh = itemView.findViewById(R.id.tvnamsinh);
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
                if (thanhVienDAO.delete(tv.getMaTV())) {
                    list.clear();
                    list.addAll(thanhVienDAO.selectAll());
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
        View view = inflater.inflate(R.layout.item_suathanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtTenTV = view.findViewById(R.id.txtTenTV_Up);
        txtNamSinh = view.findViewById(R.id.txtNamSinh_Up);
        lblMaTV = view.findViewById(R.id.lblMaTV);

        txtTenTV.setText(tv.getTenTV());
        txtNamSinh.setText(tv.getNamSinh());
        lblMaTV.setText("Mã thành viên: " + tv.getMaTV());
        view.findViewById(R.id.btnThem_TV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTV = txtTenTV.getText().toString();
                String namSinh = txtNamSinh.getText().toString();

                if(tenTV.isEmpty() || namSinh.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    tv.setTenTV(tenTV);
                    tv.setNamSinh(namSinh);
                    if(thanhVienDAO.update(tv)) {
                        list.clear();
                        list.addAll(thanhVienDAO.selectAll());
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
