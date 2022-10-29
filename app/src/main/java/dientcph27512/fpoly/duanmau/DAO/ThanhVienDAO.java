package dientcph27512.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.PhieuMuonDTO;
import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.MyDbHelper.MyDbHelper;

public class ThanhVienDAO {
    private MyDbHelper myDbHelper;
    private SQLiteDatabase database;

    public ThanhVienDAO(Context context) {
        myDbHelper = new MyDbHelper(context);
        database = myDbHelper.getWritableDatabase();
    }
    public long insert(ThanhVienDTO thanhVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN",thanhVienDTO.getHoTen());
        contentValues.put("NAMSINH",thanhVienDTO.getNamSinh());
        return database.insert("THANHVIEN",null,contentValues);
    }
    public int edit(ThanhVienDTO thanhVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN",thanhVienDTO.getHoTen());
        contentValues.put("NAMSINH",thanhVienDTO.getNamSinh());
        return database.update("THANHVIEN",contentValues,"MATV=?",new String[]{String.valueOf(thanhVienDTO.getMaTV())});
    }
    public int delete(ThanhVienDTO thanhVienDTO){
        return database.delete("THANHVIEN","MATV=?",new String[]{String.valueOf(thanhVienDTO.getMaTV())});
    }
    public List<ThanhVienDTO> getAll(){
        String sql = "SELECT * FROM THANHVIEN";
        return getData(sql);
    }
    public ThanhVienDTO getID(String id){
        String sql = "SELECT * FROM THANHVIEN WHERE MATV=?";
        List<ThanhVienDTO> list = getData(sql,id);
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<ThanhVienDTO> getData(String sql, String...selectionArgs){
        List<ThanhVienDTO> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
            thanhVienDTO.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MATV"))));
            thanhVienDTO.setHoTen(cursor.getString(cursor.getColumnIndex("HOTEN")));
            thanhVienDTO.setNamSinh(cursor.getString(cursor.getColumnIndex("NAMSINH")));
            list.add(thanhVienDTO);
            cursor.moveToNext();
        }
        return list;
    }
}
