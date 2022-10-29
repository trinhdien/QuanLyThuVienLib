package dientcph27512.fpoly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.Top;
import dientcph27512.fpoly.duanmau.R;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Top10ViewHoler> {
    private List<Top> list;
    private Context context;

    public void setList(List<Top> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Top10Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Top10ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top10,parent,false);
        return new Top10ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10ViewHoler holder, int position) {
        Top top = list.get(position);
        final int index = position;
        holder.top.setText("Top " + (index + 1));
        holder.tenSachTop.setText("Tên Sách: " + top.getTenSach());
        holder.soLuong.setText("Số Lượng: " + top.getSoLuong());
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public  class Top10ViewHoler extends RecyclerView.ViewHolder {
        private TextView top;
        private TextView tenSachTop;
        private TextView soLuong;
        public Top10ViewHoler(@NonNull View itemView) {
            super(itemView);
            top = (TextView) itemView.findViewById(R.id.top);
            tenSachTop = (TextView) itemView.findViewById(R.id.ten_sach_top);
            soLuong = (TextView) itemView.findViewById(R.id.soLuong);

        }
    }
}
