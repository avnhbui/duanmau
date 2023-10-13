package anhbvph43899.fpoly.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.model.Top10;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Top10> list;

    public Top10Adapter(Context context, ArrayList<Top10> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Top10Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, null);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull Top10Adapter.ViewHolder holder, int position) {
        holder.tvTenSach.setText(list.get(position).getTenSach());
        holder.tvSoLuong.setText(String.valueOf(list.get(position).getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }
}
