package dientcph27512.fpoly.duanmau.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import dientcph27512.fpoly.duanmau.DAO.ThuThuDAO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.R;
public class DoiMatKhauFragment extends Fragment {
    private EditText pass1;
    private ImageView showpass1;
    private EditText pass2;
    private ImageView showpass2;
    private Button btnThem;
    private Button btnHuy;
    private ThuThuDAO thuThuDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pass1 = (EditText) view.findViewById(R.id.pass1);
        showpass1 = (ImageView) view.findViewById(R.id.showpass1);
        pass2 = (EditText) view.findViewById(R.id.pass2);
        showpass2 = (ImageView) view.findViewById(R.id.showpass2);
        btnThem = (Button) view.findViewById(R.id.btn_them);
        btnHuy = (Button) view.findViewById(R.id.btn_huy);
        btnThem.setOnClickListener(v -> {
            if(pass1.getText().toString().equals(pass2.getText().toString())){
                Intent intent = getActivity().getIntent();
                thuThuDAO = new ThuThuDAO(getActivity());
                ThuThuDTO thuThuDTO = thuThuDAO.getID(intent.getStringExtra("id"));
                thuThuDTO.setMatkhau(pass1.getText().toString());
                thuThuDAO.editMK(thuThuDTO);
            }
        });
    }
}