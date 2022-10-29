package dientcph27512.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.DTO.Top;
import dientcph27512.fpoly.duanmau.MyDbHelper.MyDbHelper;

public class PhieuMuonDAO {
    private MyDbHelper myDbHelper;
    private SQLiteDatabase database;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Context context;

    public PhieuMuonDAO(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
        database = myDbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", phieuMuonDTO.getMaTT());
        contentValues.put("MATV", phieuMuonDTO.getMaTV());
        contentValues.put("MASACH", phieuMuonDTO.getMaSach());
        contentValues.put("TIENTHUE", phieuMuonDTO.getTienThue());
        contentValues.put("TRASACH", phieuMuonDTO.getTraSach());
        contentValues.put("NGAY", simpleDateFormat.format(phieuMuonDTO.getNgay()));
        return database.insert("PHIEUMUON", null, contentValues);
    }

    public int edit(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", phieuMuonDTO.getMaTT());
        contentValues.put("MATV", phieuMuonDTO.getMaTV());
        contentValues.put("MASACH", phieuMuonDTO.getMaSach());
        return database.update("PHIEUMUON", contentValues, "MAPM=?", new String[]{String.valueOf(phieuMuonDTO.getMaPM())});
    }
    public int editTT(PhieuMuonDTO phieuMuonDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TRASACH", phieuMuonDTO.getTraSach());
        return database.update("PHIEUMUON", contentValues, "MAPM=?", new String[]{String.valueOf(phieuMuonDTO.getMaPM())});
    }

    public int delete(PhieuMuonDTO phieuMuonDTO) {
        return database.delete("PHIEUMUON", "MAPM=?", new String[]{String.valueOf(phieuMuonDTO.getMaPM())});
    }

    public List<PhieuMuonDTO> getAll() {
        String sql = "SELECT * FROM PHIEUMUON";
        return getData(sql);
    }

    public PhieuMuonDTO getID(String id) {
        String sql = "SELECT * FROM PHIEUMUON WHERE MAPM=?";
        List<PhieuMuonDTO> list = getData(sql, id);
        return list.get(0);
    }
    public int xoaThanhVien(String id){
        String sql = "SELECT * FROM PHIEUMUON WHERE MATV=?";
        List<PhieuMuonDTO> list = getData(sql,id);
        if(list!=null){
            for (int i =0; i < list.size();i++){
                delete(list.get(i));
            }
            return 1;
        }
        return  -1;
    }
    public int xoaThuThu(String id){
        String sql = "SELECT * FROM PHIEUMUON WHERE MATT=?";
        List<PhieuMuonDTO> list = getData(sql,id);
        if(list!=null){
            for (int i =0; i < list.size();i++){
                delete(list.get(i));
            }
            return 1;
        }
        return  -1;
    }
    public int xoaPhieuMuon(String id){
        String sql = "SELECT * FROM PHIEUMUON WHERE MASACH=?";
        List<PhieuMuonDTO> list = getData(sql,id);
        if(list!=null){
            for (int i =0; i < list.size();i++){
                delete(list.get(i));
            }
            return 1;
        }
        return  -1;
    }
    @SuppressLint("Range")
    public List<PhieuMuonDTO> getData(String sql, String... selectionArgs) {
        List<PhieuMuonDTO> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            PhieuMuonDTO phieuMuonDTO = new PhieuMuonDTO();
            phieuMuonDTO.setMaPM(cursor.getInt(0));
            phieuMuonDTO.setMaTT(cursor.getString(1));
            phieuMuonDTO.setMaTV(cursor.getInt(2));
            phieuMuonDTO.setMaSach(cursor.getInt(3));
            phieuMuonDTO.setTienThue(cursor.getInt(4));
            try {
               phieuMuonDTO.setNgay(simpleDateFormat.parse(cursor.getString(5)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            phieuMuonDTO.setTraSach(cursor.getInt(6));
            list.add(phieuMuonDTO);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<Top> getTop() {
        String sql = "SELECT MASACH, COUNT(MASACH) AS SOLUONG FROM PHIEUMUON GROUP BY MASACH ORDER BY SOLUONG DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Top top = new Top();
            SachDTO sachDTO = sachDAO.getID(cursor.getString(cursor.getColumnIndex("MASACH")));
            top.setTenSach(sachDTO.getTenSach());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SOLUONG"))));
            list.add(top);
        }
        return list;
    }

    @SuppressLint("Range")
    public int getDoangThu(String tuNgay, String denNgay) {
        String sql = "SELECT SUM(TIENTHUE) AS DOANHTHU FROM PHIEUMUON WHERE NGAY BETWEEN ? AND ? ";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                Log.d("zzzzzz", "getDoangThu: " + cursor.getString(cursor.getColumnIndex("DOANHTHU")));
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("DOANHTHU"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
