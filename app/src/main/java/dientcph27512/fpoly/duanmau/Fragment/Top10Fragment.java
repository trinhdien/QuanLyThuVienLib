package dientcph27512.fpoly.duanmau.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.Adapter.Top10Adapter;
import dientcph27512.fpoly.duanmau.DAO.PhieuMuonDAO;
import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.Top;
import dientcph27512.fpoly.duanmau.R;
public class Top10Fragment extends Fragment {
    private RecyclerView recyclerView2;
    private List<Top> list;
    private PhieuMuonDAO phieuMuonDAO;
    private Top10Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        list = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        adapter = new Top10Adapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager);
        fillRecycleView();
    }
    public void fillRecycleView(){
        list = phieuMuonDAO.getTop();
        adapter.setList(list);
        recyclerView2.setAdapter(adapter);
    }
}