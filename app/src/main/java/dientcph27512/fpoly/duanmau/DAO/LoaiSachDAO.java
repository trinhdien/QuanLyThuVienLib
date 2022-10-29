package dientcph27512.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.LoaiSachDTO;
import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.MyDbHelper.MyDbHelper;

public class LoaiSachDAO{
    private MyDbHelper myDbHelper;
    private SQLiteDatabase database;

    public LoaiSachDAO(Context context) {
        myDbHelper = new MyDbHelper(context);
        database = myDbHelper.getWritableDatabase();
    }
    public long insert(LoaiSachDTO loaiSachDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI",loaiSachDTO.getTenLoai());
        return database.insert("LOAISACH",null,contentValues);
    }
    public int edit(LoaiSachDTO loaiSachDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENLOAI",loaiSachDTO.getTenLoai());
        return database.update("LOAISACH",contentValues,"MALOAI=?",new String[]{String.valueOf(loaiSachDTO.getMaLoai())});
    }
    public int delete(LoaiSachDTO loaiSachDTO){
        return database.delete("LOAISACH","MALOAI=?",new String[]{String.valueOf(loaiSachDTO.getMaLoai())});
    }
    @SuppressLint("Range")
    public List<LoaiSachDTO> getData(String sql, String...selectionArgs){
        List<LoaiSachDTO> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            LoaiSachDTO loaiSachDTO = new LoaiSachDTO();
            loaiSachDTO.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MALOAI"))));
            loaiSachDTO.setTenLoai(cursor.getString(cursor.getColumnIndex("TENLOAI")));
            list.add(loaiSachDTO);
            cursor.moveToNext();
        }
        return list;
    }
    public List<LoaiSachDTO> getAll(){
        String sql = "SELECT * FROM LOAISACH";
        return getData(sql);
    }
    public LoaiSachDTO getID(String id){
        String sql = "SELECT * FROM LOAISACH WHERE MALOAI=?";
        List<LoaiSachDTO> list = getData(sql,id);
        return list.get(0);
    }
}
