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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.Adapter.ThanhVienAdapter;
import dientcph27512.fpoly.duanmau.Adapter.ThuThuAdapter;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DAO.ThuThuDAO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.R;
public class NguoiDungFragment extends Fragment {
    private RecyclerView recyclerViewUser;
    private FloatingActionButton floatingActionButtonAddUser;
    private List<ThuThuDTO> list;
    private ThuThuAdapter adapter;
    private ThuThuDAO thuThuDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewUser = (RecyclerView) view.findViewById(R.id.recyclerView_user);
        floatingActionButtonAddUser = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_add_user);
        list = new ArrayList<>();
        adapter = new ThuThuAdapter(getActivity());
        thuThuDAO = new ThuThuDAO(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerViewUser.setLayoutManager(manager);
        fillRecycleView();
        addTV();
    }
    public void fillRecycleView(){
        list = thuThuDAO.getAll();
        adapter.setData(list);
        recyclerViewUser.setAdapter(adapter);
    }
    public void addTV(){
        floatingActionButtonAddUser.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.add_user);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            EditText maUser = (EditText) dialog.findViewById(R.id.ma_user);
            EditText tenUser = (EditText) dialog.findViewById(R.id.ten_user);
            EditText pass1 = (EditText) dialog.findViewById(R.id.pass1);
            ImageView showpass1 = (ImageView) dialog.findViewById(R.id.showpass1);
            EditText pass2 = (EditText) dialog.findViewById(R.id.pass2);
            ImageView showpass2 = (ImageView) dialog.findViewById(R.id.showpass2);
            Button btnThem = (Button) dialog.findViewById(R.id.btn_them);
            Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
            showpass1.setOnClickListener(v1 -> {
                if (pass1.getInputType() == 129) {
                    pass1.setInputType(1);
                } else {
                    pass1.setInputType(129);
                }
            });
            showpass2.setOnClickListener(v1 -> {
                if (pass2.getInputType() == 129) {
                    pass2.setInputType(1);
                } else {
                    pass2.setInputType(129);
                }
            });
            btnThem.setOnClickListener(v1 -> {
                if(pass1.getText().toString().equalsIgnoreCase(pass2.getText().toString())){
                    ThuThuDTO thuThuDTO = new ThuThuDTO(maUser.getText().toString(),tenUser.getText().toString(),pass1.getText().toString());
                    thuThuDAO.insert(thuThuDTO);
                    Log.d("zzzzzzzz", "addTV: " + thuThuDAO.insert(thuThuDTO));
                    fillRecycleView();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Đã Thêm Thành Viên", Toast.LENGTH_SHORT).show();
                }
            });
            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Đã Huỷ Thêm Thành Viên", Toast.LENGTH_SHORT).show();
            });
        });
    }
}