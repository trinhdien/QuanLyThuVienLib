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

import dientcph27512.fpoly.duanmau.DAO.PhieuMuonDAO;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.R;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHoler> {
    private Context context;
    private List<ThanhVienDTO> list;

    public ThanhVienAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThanhVienDTO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThanhVienViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanh_vien, parent, false);
        return new ThanhVienViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHoler holder, int position) {
        ThanhVienDTO thanhVienDTO = list.get(position);
        final int index = position;
        if (thanhVienDTO == null) {
            return;
        }
        holder.idTv.setText("ID: " + thanhVienDTO.getMaTV());
        holder.tenTv.setText("Tên: "+thanhVienDTO.getHoTen());
        holder.namSinhTv.setText(thanhVienDTO.getNamSinh());
        holder.edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.add_tv);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText("Sửa Thành Viên");
            TextView ten_Tv = (EditText) dialog.findViewById(R.id.ten_Tv);
            TextView ngaySinh = (EditText) dialog.findViewById(R.id.ngay_sinh);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            btnThem.setText("Sửa");
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            btnThem.setOnClickListener(v1 -> {
                thanhVienDTO.setHoTen(ten_Tv.getText().toString());
                thanhVienDTO.setNamSinh(ngaySinh.getText().toString());
                list.set(index, thanhVienDTO);
                ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                thanhVienDAO.edit(thanhVienDTO);
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
            builder.setMessage("ID: " + thanhVienDTO.getMaTV() + "\t\t" + thanhVienDTO.getHoTen() + "\n" +"Sinh Ngày: "+ thanhVienDTO.getNamSinh());
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                    phieuMuonDAO.xoaThanhVien(String.valueOf(thanhVienDTO.getMaTV()));
                    list.remove(index);
                    ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                    thanhVienDAO.delete(thanhVienDTO);
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
        private TextView idTv;
        private TextView tenTv;
        private TextView namSinhTv;
        private ImageView edit;
        private ImageView delete;

        public ThanhVienViewHoler(@NonNull View itemView) {
            super(itemView);
            idTv = (TextView) itemView.findViewById(R.id.id_tv);
            tenTv = (TextView) itemView.findViewById(R.id.ten_Tv);
            namSinhTv = (TextView) itemView.findViewById(R.id.namSinh_Tv);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
