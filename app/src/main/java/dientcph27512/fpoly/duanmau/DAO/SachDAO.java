package dientcph27512.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.SachDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.MyDbHelper.MyDbHelper;

public class SachDAO {
    private MyDbHelper myDbHelper;
    private SQLiteDatabase database;

    public SachDAO(Context context) {
        myDbHelper = new MyDbHelper(context);
        database = myDbHelper.getWritableDatabase();
    }
    public long insert(SachDTO sachDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH",sachDTO.getTenSach());
        contentValues.put("GIATHUE",sachDTO.getGiaThue());
        contentValues.put("MALOAI",sachDTO.getMaLoai());
        return database.insert("SACH",null,contentValues);
    }
    public int xoaSach(String id,Context context){
        String sql = "SELECT * FROM SACH WHERE MALOAI=?";
        List<SachDTO> list = getData(sql,id);
        if(list!=null){
            for (int i =0; i < list.size();i++){
                String sql2 = "SELECT * FROM SACH WHERE MASACH =?";
                List<SachDTO> list1 = getData(sql2, String.valueOf(list.get(i).getMaSach()));
                if(list1!=null){
                    for(int j = 0; j < list1.size();j++){
                        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                        phieuMuonDAO.xoaPhieuMuon(String.valueOf(list1.get(j).getMaSach()));
                    }
                }
                delete(list.get(i));
            }
            return 1;
        }
        return  -1;
    }
    public int edit(SachDTO sachDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENSACH",sachDTO.getTenSach());
        contentValues.put("GIATHUE",sachDTO.getGiaThue());
        contentValues.put("MALOAI",sachDTO.getMaLoai());
        return database.update("SACH",contentValues,"MASACH=?",new String[]{String.valueOf(sachDTO.getMaSach())});
    }
    public int delete(SachDTO sachDTO){
        return database.delete("SACH","MASACH=?",new String[]{String.valueOf(sachDTO.getMaSach())});
    }
    public List<SachDTO> getAll(){
        String sql = "SELECT * FROM SACH";
        return getData(sql);
    }
    public SachDTO getID(String id){
        String sql = "SELECT * FROM SACH WHERE MASACH=?";
        List<SachDTO> list = getData(sql,id);
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<SachDTO> getData(String sql, String...selectionArgs){
        List<SachDTO> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            SachDTO sachDTO = new SachDTO();
            sachDTO.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MASACH"))));
            sachDTO.setTenSach(cursor.getString(cursor.getColumnIndex("TENSACH")));
            sachDTO.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("GIATHUE"))));
            sachDTO.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MALOAI"))));
            list.add(sachDTO);
            cursor.moveToNext();
        }
        return list;
    }
}
