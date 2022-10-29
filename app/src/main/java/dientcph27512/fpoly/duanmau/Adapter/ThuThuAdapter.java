package dientcph27512.fpoly.duanmau.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
import dientcph27512.fpoly.duanmau.DAO.ThuThuDAO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.R;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ThanhVienViewHoler> {
    private Context context;
    private List<ThuThuDTO> list;

    public ThuThuAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThuThuDTO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThanhVienViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_user, parent, false);
        return new ThanhVienViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHoler holder, int position) {
        ThuThuDTO thuThuDTO = list.get(position);
        final int index = position;
        if (thuThuDTO == null) {
            return;
        }
        holder.idUser.setText("ID: " + thuThuDTO.getMaTT());
        holder.tenUser.setText("Tên: "+thuThuDTO.getHoTen());
        holder.passUser.setText("Pass: " + thuThuDTO.getMatkhau());
        holder.edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.sua_user);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            EditText tenUser = (EditText) dialog.findViewById(R.id.ten_user);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            btnThem.setOnClickListener(v1 -> {
                thuThuDTO.setHoTen(tenUser.getText().toString());
                list.set(index, thuThuDTO);
                ThuThuDAO thuThuDAO = new ThuThuDAO(context);
                thuThuDAO.edit(thuThuDTO);
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
            builder.setMessage("ID: " + thuThuDTO.getMaTT() + "\t\t"+ "Tên: " + thuThuDTO.getHoTen() + "\n" +"Mật Khẩu: "+ thuThuDTO.getMatkhau());
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                    phieuMuonDAO.xoaThuThu(thuThuDTO.getMaTT());
                    list.remove(index);
                    ThuThuDAO thuThuDAO = new ThuThuDAO(context);
                    thuThuDAO.delete(thuThuDTO);
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
        private TextView idUser;
        private TextView tenUser;
        private TextView passUser;
        private ImageView edit;
        private ImageView delete;

        public ThanhVienViewHoler(@NonNull View itemView) {
            super(itemView);
            idUser = (TextView) itemView.findViewById(R.id.id_user);
            tenUser = (TextView) itemView.findViewById(R.id.ten_user);
            passUser = (TextView) itemView.findViewById(R.id.pass_user);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
