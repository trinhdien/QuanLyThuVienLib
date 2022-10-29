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

import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.R;

public class TTSpinerAdapter extends ArrayAdapter<ThuThuDTO> {
    private Context context;
    private List<ThuThuDTO> list;
    private TextView tenLoai;
    public TTSpinerAdapter(Context context, List<ThuThuDTO> list) {
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
        final ThuThuDTO sachDTO = list.get(position);
        if(convertView != null){
            tenLoai = convertView.findViewById(R.id.tenLoai);
            tenLoai.setText(sachDTO.getMaTT() + "." + sachDTO.getHoTen());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spiner_loai_sach,null);
        }
        final ThuThuDTO sachDTO = list.get(position);
        if(convertView != null){
            tenLoai = convertView.findViewById(R.id.tenLoai);
            tenLoai.setText(sachDTO.getMaTT() + "." + sachDTO.getHoTen());
        }
        return convertView;
    }
}
