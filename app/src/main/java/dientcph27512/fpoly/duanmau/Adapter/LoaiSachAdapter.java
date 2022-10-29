package dientcph27512.fpoly.duanmau.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dientcph27512.fpoly.duanmau.DAO.LoaiSachDAO;
import dientcph27512.fpoly.duanmau.DAO.SachDAO;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.R;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ThanhVienViewHoler> {
    private Context context;
    private List<LoaiSachDTO> list;

    public LoaiSachAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LoaiSachDTO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThanhVienViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_sach, parent, false);
        return new ThanhVienViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHoler holder, int position) {
        LoaiSachDTO loaiSachDTO = list.get(position);
        final int index = position;
        if (loaiSachDTO == null) {
            return;
        }
        holder.idLoaiSach.setText("ID: " + loaiSachDTO.getMaLoai());
        holder.tenLoaiSach.setText("Tên Loại Sách: " + loaiSachDTO.getTenLoai());
        holder.edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.add_loai_sach);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText("Sửa Loại Sách");
            EditText tenSach = (EditText) dialog.findViewById(R.id.ten_sach);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            btnThem.setText("Sửa");
            btnThem.setOnClickListener(v1 -> {
                loaiSachDTO.setTenLoai(tenSach.getText().toString());
                list.set(index, loaiSachDTO);
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                loaiSachDAO.edit(loaiSachDTO);
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(context, "Đã Sửa Thành Viên", Toast.LENGTH_SHORT).show();
            });
            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
                Toast.makeText(context, "Đã Huỷ Sửa Thành Viên", Toast.LENGTH_SHORT).show();
            });
        });
        holder.delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Bạn Có Chắc Muốn Xoá");
            builder.setMessage("ID: " + loaiSachDTO.getMaLoai() + "\t\t" + "Tên Loại Sách: " + loaiSachDTO.getTenLoai());
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SachDAO sachDAO = new SachDAO(context);
                    sachDAO.xoaSach(String.valueOf(loaiSachDTO.getMaLoai()),context);
                    list.remove(index);
                    LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                    loaiSachDAO.delete(loaiSachDTO);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã Xoá", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "Đã Huỷ Xoá", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ThanhVienViewHoler extends RecyclerView.ViewHolder {
        private TextView idLoaiSach;
        private TextView tenLoaiSach;
        private ImageView edit;
        private ImageView delete;

        public ThanhVienViewHoler(@NonNull View itemView) {
            super(itemView);
            idLoaiSach = (TextView) itemView.findViewById(R.id.id_loaiSach);
            tenLoaiSach = (TextView) itemView.findViewById(R.id.ten_loaiSach);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
