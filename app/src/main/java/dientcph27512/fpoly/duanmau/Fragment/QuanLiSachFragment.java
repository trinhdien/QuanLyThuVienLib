package dientcph27512.fpoly.duanmau.Fragment;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.Adapter.LoaiSachAdapter;
import dientcph27512.fpoly.duanmau.Adapter.LoaiSachSpinerAdapter;
import dientcph27512.fpoly.duanmau.Adapter.SachAdapter;
import dientcph27512.fpoly.duanmau.DAO.LoaiSachDAO;
import dientcph27512.fpoly.duanmau.DAO.SachDAO;
import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.R;
public class QuanLiSachFragment extends Fragment {
    private RecyclerView recyclerViewSach;
    private FloatingActionButton floatingActionButtonAddSach;
    private List<LoaiSachDTO> list;
    private  LoaiSachDAO loaiSachDAO;
    private LoaiSachSpinerAdapter loaiSachSpinerAdapter;
    private  List<SachDTO> sachDTOList;
    private SachAdapter adapter;
    private SachDAO dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewSach = (RecyclerView) view.findViewById(R.id.recyclerView_sach);
        floatingActionButtonAddSach = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_add_sach);
        sachDTOList = new ArrayList<>();
        adapter = new SachAdapter(getActivity());
        dao = new SachDAO(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewSach.setLayoutManager(manager);
        fillRecycleView();
        addSach();
    }
    public void addSach(){
        floatingActionButtonAddSach.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.add_sach);
            Dialog dialog = builder.create();
            dialog.show();
            EditText tenTv = (EditText) dialog.findViewById(R.id.ed_tenSach);
            EditText ngaySinh = (EditText) dialog.findViewById(R.id.ed_giaThue);
            Spinner spinerLoaiSach = (Spinner) dialog.findViewById(R.id.spiner_loaiSach);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            list = new ArrayList<>();
            loaiSachDAO = new LoaiSachDAO(getActivity());
            list = loaiSachDAO.getAll();
            loaiSachSpinerAdapter = new LoaiSachSpinerAdapter(getActivity(),list);
            spinerLoaiSach.setAdapter(loaiSachSpinerAdapter);
            btnThem.setOnClickListener(v1 -> {
                SachDTO sachDTO = new SachDTO(tenTv.getText().toString(),Integer.parseInt(ngaySinh.getText().toString()),list.get(spinerLoaiSach.getSelectedItemPosition()).getMaLoai());
                dao.insert(sachDTO);
                fillRecycleView();
                Toast.makeText(getActivity(), "Đã Thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            btnHuy.setOnClickListener(v1 -> {
                Toast.makeText(getActivity(), "Đã Huỷ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        });
    }
    public void fillRecycleView(){
        sachDTOList = dao.getAll();
        adapter.setList(sachDTOList);
        recyclerViewSach.setAdapter(adapter);
    }
}