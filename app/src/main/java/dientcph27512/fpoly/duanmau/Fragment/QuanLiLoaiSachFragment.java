package dientcph27512.fpoly.duanmau.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.Adapter.LoaiSachAdapter;
import dientcph27512.fpoly.duanmau.Adapter.ThanhVienAdapter;
import dientcph27512.fpoly.duanmau.DAO.LoaiSachDAO;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.R;
public class QuanLiLoaiSachFragment extends Fragment {
    private RecyclerView recyclerViewLoaiSach;
    private FloatingActionButton addLoaiSach;
    private List<LoaiSachDTO> list;
    private LoaiSachAdapter adapter;
    private LoaiSachDAO loaiSachDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewLoaiSach = (RecyclerView) view.findViewById(R.id.recyclerView_loaiSach);
        addLoaiSach = (FloatingActionButton) view.findViewById(R.id.add_loaiSach);
        list = new ArrayList<>();
        adapter = new LoaiSachAdapter(getActivity());
        loaiSachDAO = new LoaiSachDAO(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerViewLoaiSach.setLayoutManager(manager);
        fillRecycleView();
        addTV();
    }
    public void fillRecycleView(){
        list = loaiSachDAO.getAll();
        adapter.setData(list);
        recyclerViewLoaiSach.setAdapter(adapter);
    }
    public void addTV(){
        addLoaiSach.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.add_loai_sach);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            EditText tenSach = (EditText) dialog.findViewById(R.id.ten_sach);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            btnThem.setOnClickListener(v1 -> {
                LoaiSachDTO loaiSachDTO = new LoaiSachDTO(tenSach.getText().toString());
                loaiSachDAO.insert(loaiSachDTO);
                fillRecycleView();
                dialog.dismiss();
                Toast.makeText(getActivity(), "Đã Thêm Thành Viên", Toast.LENGTH_SHORT).show();
            });
            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Đã Huỷ Thêm Thành Viên", Toast.LENGTH_SHORT).show();
            });
        });
    }
}