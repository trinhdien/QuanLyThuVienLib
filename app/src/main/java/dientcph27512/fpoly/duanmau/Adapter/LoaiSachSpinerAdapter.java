package dientcph27512.fpoly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.R;

public class LoaiSachSpinerAdapter extends ArrayAdapter<LoaiSachDTO> {
    private Context context;
    private List<LoaiSachDTO> list;
    private TextView tenLoai;
    public LoaiSachSpinerAdapter(Context context, List<LoaiSachDTO> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spiner_loai_sach,null);
        }
        final LoaiSachDTO loaiSachDTO = list.get(position);
        if(convertView != null){
            tenLoai = convertView.findViewById(R.id.tenLoai);
            tenLoai.setText(loaiSachDTO.getMaLoai() + "." + loaiSachDTO.getTenLoai());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spiner_loai_sach,null);
        }
        final LoaiSachDTO loaiSachDTO = list.get(position);
        if(convertView != null){
            tenLoai = convertView.findViewById(R.id.tenLoai);
            tenLoai.setText(loaiSachDTO.getMaLoai() + "." + loaiSachDTO.getTenLoai());
        }
        return convertView;
    }
}
