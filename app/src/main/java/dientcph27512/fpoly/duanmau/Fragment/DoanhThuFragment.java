package dientcph27512.fpoly.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dientcph27512.fpoly.duanmau.DAO.PhieuMuonDAO;
import dientcph27512.fpoly.duanmau.R;

public class DoanhThuFragment extends Fragment {
    private Button tuNgay;
    private Button denNgay;
    private TextView tvTuNgay;
    private TextView tvDenNgaay;
    private TextView doanhThu;
    private int mmonth;
    private int myear;
    private int mday;
    private Button thongKe;
    private PhieuMuonDAO phieuMuonDAO;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tuNgay = (Button) view.findViewById(R.id.tuNgay);
        denNgay = (Button) view.findViewById(R.id.denNgay);
        tvTuNgay = (TextView) view.findViewById(R.id.tv_tuNgay);
        tvDenNgaay = (TextView) view.findViewById(R.id.tv_denNgaay);
        doanhThu = (TextView) view.findViewById(R.id.doanhThu);
        thongKe = (Button) view.findViewById(R.id.thongke);
        tuNgay.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mday = calendar.get(Calendar.DAY_OF_MONTH);
            mmonth = calendar.get(Calendar.MONTH);
            myear = calendar.get(Calendar.YEAR);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(),0,setNgayBD,myear,mmonth,mday);
            dialog.show();
        });
        denNgay.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mday = calendar.get(Calendar.DAY_OF_MONTH);
            mmonth = calendar.get(Calendar.MONTH);
            myear = calendar.get(Calendar.YEAR);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(),0,setNgayKT,myear,mmonth,mday);
            dialog.show();

        });
        thongKe.setOnClickListener(v -> {
            if (!tvTuNgay.getText().toString().isEmpty() && !tvDenNgaay.getText().toString().isEmpty()) {
                phieuMuonDAO = new PhieuMuonDAO(getActivity());
                doanhThu.setText(String.valueOf(phieuMuonDAO.getDoangThu(tvTuNgay.getText().toString(), tvDenNgaay.getText().toString())));
            }
        });
    }
    DatePickerDialog.OnDateSetListener setNgayBD = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myear = year;
            mmonth = month;
            mday = dayOfMonth;
            GregorianCalendar gregorianCalendar = new GregorianCalendar(myear,month,mday);
            tvTuNgay.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener setNgayKT = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myear = year;
            mmonth = month;
            mday = dayOfMonth;
            GregorianCalendar gregorianCalendar = new GregorianCalendar(myear,month,mday);
            tvDenNgaay.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
        }
    };
}