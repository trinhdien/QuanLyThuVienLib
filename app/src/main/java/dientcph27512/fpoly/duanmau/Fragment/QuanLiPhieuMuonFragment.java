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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dientcph27512.fpoly.duanmau.Adapter.PhieuMuonAdapter;
import dientcph27512.fpoly.duanmau.Adapter.SachSpinerAdapter;
import dientcph27512.fpoly.duanmau.Adapter.TTSpinerAdapter;
import dientcph27512.fpoly.duanmau.Adapter.TVSpinerAdapter;
import dientcph27512.fpoly.duanmau.DAO.PhieuMuonDAO;
import dientcph27512.fpoly.duanmau.DAO.SachDAO;
import dientcph27512.fpoly.duanmau.DAO.ThanhVienDAO;
import dientcph27512.fpoly.duanmau.DAO.ThuThuDAO;
import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.MainActivity;
import dientcph27512.fpoly.duanmau.R;

public class QuanLiPhieuMuonFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<PhieuMuonDTO> phieuMuonDTOList;
    private PhieuMuonAdapter phieuMuonAdapter;
    private PhieuMuonDAO phieuMuonDAO;
    private List<SachDTO> sachDTOList;
    private SachDAO sachDAO;
    private List<ThuThuDTO> thuThuDTOList;
    private ThuThuDAO thuThuDAO;
    private List<ThanhVienDTO> thanhVienDTOList;
    private ThanhVienDAO thanhVienDAO;
    private SachSpinerAdapter sachSpinerAdapter;
    private TTSpinerAdapter ttSpinerAdapter;
    private TVSpinerAdapter tvSpinerAdapter;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        phieuMuonDTOList = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        phieuMuonAdapter = new PhieuMuonAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        fillRecycleView();
        add();
    }

    public void fillRecycleView() {
        phieuMuonDTOList = phieuMuonDAO.getAll();
        phieuMuonAdapter.setList(phieuMuonDTOList);
        recyclerView.setAdapter(phieuMuonAdapter);
    }

    public void add() {
        floatingActionButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.add_phieu_muon);
            Dialog dialog = builder.create();
            dialog.show();
            TextView title = (TextView) dialog.findViewById(R.id.title);
            TextView tv_thuthu = (TextView) dialog.findViewById(R.id.tv_thuthu);
            Spinner spinerTV = (Spinner) dialog.findViewById(R.id.spinerTV);
            Spinner spinerS = (Spinner) dialog.findViewById(R.id.spinerS);
            Button them = (Button) dialog.findViewById(R.id.them);
            Button huy = (Button) dialog.findViewById(R.id.huy);
            mainActivity = (MainActivity) getActivity();

            sachDTOList = new ArrayList<>();
            sachDAO = new SachDAO(getActivity());
            sachDTOList = sachDAO.getAll();
            sachSpinerAdapter = new SachSpinerAdapter(getActivity(), sachDTOList);
            spinerS.setAdapter(sachSpinerAdapter);

            tv_thuthu.setText(mainActivity.getThuthu());

            thanhVienDTOList = new ArrayList<>();
            thanhVienDAO = new ThanhVienDAO(getActivity());
            thanhVienDTOList = thanhVienDAO.getAll();
            tvSpinerAdapter = new TVSpinerAdapter(getActivity(), thanhVienDTOList);
            spinerTV.setAdapter(tvSpinerAdapter);

            them.setOnClickListener(v1 -> {
                try {
                    PhieuMuonDTO phieuMuonDTO = new PhieuMuonDTO(mainActivity.getMaTT(), thanhVienDTOList.get(spinerTV.getSelectedItemPosition()).getMaTV(), sachDTOList.get(spinerS.getSelectedItemPosition()).getMaSach(), sachDTOList.get(spinerS.getSelectedItemPosition()).getGiaThue(), 0, simpleDateFormat.parse(simpleDateFormat.format(Calendar.getInstance().getTime())));
                    phieuMuonDAO = new PhieuMuonDAO(getActivity());
                    phieuMuonDAO.insert(phieuMuonDTO);
                    fillRecycleView();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Đã Thêm", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            huy.setOnClickListener(v1 -> {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Đã Huỷ", Toast.LENGTH_SHORT).show();
            });
        });
    }
}