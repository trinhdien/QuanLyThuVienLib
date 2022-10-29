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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.Adapter.ThanhVienAdapter;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.R;
public class QuanLiNhanVienFragment extends Fragment {
    private RecyclerView recyclerViewTV;
    private FloatingActionButton addTv;
    private List<ThanhVienDTO> list;
    private ThanhVienAdapter adapter;
    private ThanhVienDAO thanhVienDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewTV = (RecyclerView) view.findViewById(R.id.recyclerView_TV);
        addTv = (FloatingActionButton) view.findViewById(R.id.add_tv);
        list = new ArrayList<>();
        adapter = new ThanhVienAdapter(getActivity());
        thanhVienDAO = new ThanhVienDAO(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerViewTV.setLayoutManager(manager);
        fillRecycleView();
        addTV();
    }
    public void fillRecycleView(){
        list = thanhVienDAO.getAll();
        adapter.setData(list);
        recyclerViewTV.setAdapter(adapter);
    }
    public void addTV(){
        addTv.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.add_tv);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            TextView tenTv = (EditText) dialog.findViewById(R.id.ten_Tv);
            TextView ngaySinh = (EditText) dialog.findViewById(R.id.ngay_sinh);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            btnThem.setOnClickListener(v1 -> {
                ThanhVienDTO thanhVienDTO = new ThanhVienDTO(tenTv.getText().toString(),ngaySinh.getText().toString());
                thanhVienDAO.insert(thanhVienDTO);
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