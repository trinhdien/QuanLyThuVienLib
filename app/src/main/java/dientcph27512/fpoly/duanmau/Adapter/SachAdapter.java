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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DAO.LoaiSachDAO;
import dientcph27512.fpoly.duanmau.DAO.PhieuMuonDAO;
import dientcph27512.fpoly.duanmau.DAO.SachDAO;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.R;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHoler> {
    private Context context;
    private List<SachDTO> list;
    private List<LoaiSachDTO> loaiSachDTO;
    private LoaiSachDAO loaiSachDAO;
    public SachAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<SachDTO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SachViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach, parent, false);
        return new SachViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHoler holder, int position) {
        SachDTO sachDTO = list.get(position);
        final int index = position;
        loaiSachDAO = new LoaiSachDAO(context);
        LoaiSachDTO loaiSachDTOS = loaiSachDAO.getID(String.valueOf(sachDTO.getMaLoai()));
        if (sachDTO == null) {
            return;
        }
        holder.maSach.setText("ID: " + (sachDTO.getMaSach()));
        holder.tenSach.setText("Tên Sách: " + sachDTO.getTenSach());
        holder.giathue.setText("Giá: " + sachDTO.getGiaThue());
        holder.loaiSach.setText("Loại Sách: " + loaiSachDTOS.getTenLoai());
        holder.delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Bạn Có Chắc Muốn Xoá");
            builder.setMessage("ID: " + sachDTO.getMaSach() + "\t\t" + "Tên Sách: " + sachDTO.getTenSach() + "\n" + "Giá Thuê: " + sachDTO.getGiaThue() + "\n" + "Loại Sách : " + loaiSachDTOS.getTenLoai());
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list.remove(index);
                    SachDAO sachDAO = new SachDAO(context);
                    sachDAO.delete(sachDTO);
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
        holder.edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.add_sach);
            Dialog dialog = builder.create();
            dialog.show();
            TextView tiltle = (TextView) dialog.findViewById(R.id.title);
            tiltle.setText("Sửa Sách");
            EditText tenTv = (EditText) dialog.findViewById(R.id.ed_tenSach);
            EditText ngaySinh = (EditText) dialog.findViewById(R.id.ed_giaThue);
            Spinner spinerLoaiSach = (Spinner) dialog.findViewById(R.id.spiner_loaiSach);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            btnThem.setText("Sửa Sách");
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            loaiSachDTO = new ArrayList<>();
            loaiSachDTO = loaiSachDAO.getAll();
            LoaiSachSpinerAdapter loaiSachSpinerAdapter = new LoaiSachSpinerAdapter(context,loaiSachDTO);
            spinerLoaiSach.setAdapter(loaiSachSpinerAdapter);
            btnThem.setOnClickListener(v1 -> {
                sachDTO.setTenSach(tenTv.getText().toString());
                sachDTO.setGiaThue(Integer.parseInt(ngaySinh.getText().toString()));
                sachDTO.setMaLoai(loaiSachDTO.get(spinerLoaiSach.getSelectedItemPosition()).getMaLoai());
                SachDAO sachDAO = new SachDAO(context);
                list.set(index,sachDTO);
                sachDAO.edit(sachDTO);
                notifyDataSetChanged();
                Toast.makeText(context, "Đã Thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            btnHuy.setOnClickListener(v1 -> {
                Toast.makeText(context, "Đã Huỷ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        });
        holder.delete.setOnClickListener(v -> {
            SachDAO sachDAO = new SachDAO(context);
            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
            phieuMuonDAO.xoaPhieuMuon(String.valueOf(sachDTO.getMaSach()));
            sachDAO.delete(sachDTO);
            list.remove(index);
            notifyDataSetChanged();
            Toast.makeText(context, "Đã Xoá", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class SachViewHoler extends RecyclerView.ViewHolder {
        private TextView maSach;
        private TextView tenSach;
        private TextView giathue;
        private TextView loaiSach;
        private ImageView edit;
        private ImageView delete;
        public SachViewHoler(@NonNull View itemView) {
            super(itemView);
            maSach = (TextView) itemView.findViewById(R.id.maSach);
            tenSach = (TextView) itemView.findViewById(R.id.tenSach);
            giathue = (TextView) itemView.findViewById(R.id.giathue);
            loaiSach = (TextView) itemView.findViewById(R.id.loaiSach);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
