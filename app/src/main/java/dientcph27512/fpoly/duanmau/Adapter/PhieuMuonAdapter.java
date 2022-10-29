package dientcph27512.fpoly.duanmau.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dientcph27512.fpoly.duanmau.DAO.LoaiSachDAO;
import dientcph27512.fpoly.duanmau.DAO.PhieuMuonDAO;
import dientcph27512.fpoly.duanmau.DAO.SachDAO;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DAO.ThuThuDAO;
import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.MainActivity;
import dientcph27512.fpoly.duanmau.R;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHoler>{
    private List<PhieuMuonDTO> list;
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private SachDAO sachDAO;
    private List<SachDTO> sachDTOList;
    private ThanhVienDAO thanhVienDAO;
    private List<ThanhVienDTO> thanhVienDTOList;
    private ThuThuDAO thuThuDAO;
    private List<ThuThuDTO> thuThuDTOList;
    private SachSpinerAdapter sachSpinerAdapter;
    private TTSpinerAdapter ttSpinerAdapter;
    private TVSpinerAdapter tvSpinerAdapter;
    public void setList(List<PhieuMuonDTO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public PhieuMuonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PhieuMuonViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieu_muon,parent,false);
        return new PhieuMuonViewHoler(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHoler holder, int position) {
        PhieuMuonDTO phieuMuonDTO = list.get(position);
        final int index = position;
        sachDAO = new SachDAO(context);
        thuThuDAO = new ThuThuDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
        SachDTO sachDTO = sachDAO.getID(String.valueOf(phieuMuonDTO.getMaSach()));
        ThuThuDTO thuThuDTO = thuThuDAO.getID(String.valueOf(phieuMuonDTO.getMaTT()));
        ThanhVienDTO thanhVienDTO = thanhVienDAO.getID(String.valueOf(phieuMuonDTO.getMaTV()));
        if (phieuMuonDTO == null){
            return;
        }
        holder.idPm.setText("ID: " + phieuMuonDTO.getMaPM());
        holder.tensach.setText("Tên Sách: " + sachDTO.getTenSach());
        holder.tenTt.setText("Tên Thủ Thư: " + thuThuDTO.getHoTen());
        holder.tenTV.setText("Tên Thành Viên: " + thanhVienDTO.getHoTen());
        holder.tienThue.setText("Giá Thuê: " + sachDTO.getGiaThue());
        holder.ngayThue.setText(sdf.format(phieuMuonDTO.getNgay()));
        if (phieuMuonDTO.getTraSach() == 1) {
            holder.traSach.setText("Đã Trả");
            holder.traSach.setChecked(true);
        } else {
            holder.traSach.setText("Chưa Trả");
            holder.traSach.setChecked(false);
        }
        holder.edit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.add_phieu_muon);
            Dialog dialog = builder.create();
            dialog.show();

            TextView title = (TextView) dialog.findViewById(R.id.title);
            TextView tv_thuthu = (TextView) dialog.findViewById(R.id.tv_thuthu);
            Spinner spinerTV = (Spinner) dialog.findViewById(R.id.spinerTV);
            Spinner spinerS = (Spinner) dialog.findViewById(R.id.spinerS);
            Button them = (Button) dialog.findViewById(R.id.them);
            Button huy = (Button) dialog.findViewById(R.id.huy);

            them.setText("Sửa");
            title.setText("Sửa Phiếu Mượn");
            sachDTOList = new ArrayList<>();
            sachDAO = new SachDAO(context);
            sachDTOList = sachDAO.getAll();
            sachSpinerAdapter = new SachSpinerAdapter(context,sachDTOList);
            spinerS.setAdapter(sachSpinerAdapter);

            tv_thuthu.setText(thuThuDTO.getHoTen());

            thanhVienDTOList = new ArrayList<>();
            thanhVienDAO = new ThanhVienDAO(context);
            thanhVienDTOList = thanhVienDAO.getAll();
            tvSpinerAdapter = new TVSpinerAdapter(context,thanhVienDTOList);
            spinerTV.setAdapter(tvSpinerAdapter);

            them.setOnClickListener(v1 -> {
                    phieuMuonDTO.setMaTV(thanhVienDTOList.get(spinerTV.getSelectedItemPosition()).getMaTV());
                    phieuMuonDTO.setMaSach(sachDTOList.get(spinerS.getSelectedItemPosition()).getMaSach());
                    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                    phieuMuonDAO.edit(phieuMuonDTO);
                    list.set(index,phieuMuonDTO);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Đã Thêm", Toast.LENGTH_SHORT).show();
            });
            huy.setOnClickListener(v1 -> {
                dialog.dismiss();
                Toast.makeText(context, "Đã Huỷ", Toast.LENGTH_SHORT).show();
            });
        });
        holder.delete.setOnClickListener(v -> {
           PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
           phieuMuonDAO.delete(phieuMuonDTO);
           list.remove(index);
           notifyDataSetChanged();
            Toast.makeText(context, "Đã Xoá", Toast.LENGTH_SHORT).show();
        });
        holder.traSach.setOnClickListener(v -> {
            if(holder.traSach.isChecked()){
                holder.traSach.setText("Đã Trả");
                phieuMuonDTO.setTraSach(1);
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                list.set(index,phieuMuonDTO);
                phieuMuonDAO.editTT(phieuMuonDTO);
            }else{
                holder.traSach.setText("Chưa Trả");
                phieuMuonDTO.setTraSach(0);
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                list.set(index,phieuMuonDTO);
                phieuMuonDAO.editTT(phieuMuonDTO);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class PhieuMuonViewHoler extends RecyclerView.ViewHolder{
        private TextView idPm;
        private TextView tenTt;
        private TextView tenTV;
        private TextView tensach;
        private CheckBox traSach;
        private TextView ngayThue;
        private TextView tienThue;
        private ImageView edit;
        private ImageView delete;
        public PhieuMuonViewHoler(@NonNull View itemView) {
            super(itemView);
            idPm = (TextView) itemView.findViewById(R.id.id_pm);
            tenTt = (TextView) itemView.findViewById(R.id.ten_tt);
            tenTV = (TextView) itemView.findViewById(R.id.tenTV);
            tensach = (TextView) itemView.findViewById(R.id.tensach);
            traSach = (CheckBox) itemView.findViewById(R.id.traSach);
            ngayThue = (TextView) itemView.findViewById(R.id.ngayThue);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            tienThue = (TextView) itemView.findViewById(R.id.gia_thue);
        }
    }
}
