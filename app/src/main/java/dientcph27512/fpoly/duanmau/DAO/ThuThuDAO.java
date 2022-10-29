package dientcph27512.fpoly.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dientcph27512.fpoly.duanmau.DTO.ThanhVienDTO;
import dientcph27512.fpoly.duanmau.DTO.ThuThuDTO;
import dientcph27512.fpoly.duanmau.MyDbHelper.MyDbHelper;

public class ThuThuDAO {
    private MyDbHelper myDbHelper;
    private SQLiteDatabase database;

    public ThuThuDAO(Context context) {
        myDbHelper = new MyDbHelper(context);
        database = myDbHelper.getWritableDatabase();
    }

    public long insert(ThuThuDTO thuThuDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATT", thuThuDTO.getMaTT());
        contentValues.put("HOTEN", thuThuDTO.getHoTen());
        contentValues.put("MATKHAU", thuThuDTO.getMatkhau());
        return database.insert("THUTHU", null, contentValues);
    }

    public int edit(ThuThuDTO thuThuDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("HOTEN", thuThuDTO.getHoTen());
        return database.update("THUTHU", contentValues, "MATT=?", new String[]{String.valueOf(thuThuDTO.getMaTT())});
    }
    public int editMK(ThuThuDTO thuThuDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATKHAU", thuThuDTO.getMatkhau());
        return database.update("THUTHU", contentValues, "MATT=?", new String[]{String.valueOf(thuThuDTO.getMaTT())});
    }

    public int delete(ThuThuDTO thuThuDTO) {
        return database.delete("THUTHU", "MATT=?", new String[]{String.valueOf(thuThuDTO.getMaTT())});
    }

    public List<ThuThuDTO> getAll() {
        String sql = "SELECT * FROM THUTHU";
        return getData(sql);
    }

    public ThuThuDTO getID(String id) {
        String sql = "SELECT * FROM THUTHU WHERE MATT=?";
        List<ThuThuDTO> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    public List<ThuThuDTO> getData(String sql, String... selectionArgs) {
        List<ThuThuDTO> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            ThuThuDTO thuThuDTO = new ThuThuDTO();
            thuThuDTO.setMaTT(cursor.getString(cursor.getColumnIndex("MATT")));
            thuThuDTO.setHoTen(cursor.getString(cursor.getColumnIndex("HOTEN")));
            thuThuDTO.setMatkhau(cursor.getString(cursor.getColumnIndex("MATKHAU")));
            list.add(thuThuDTO);
            cursor.moveToNext();
        }
        return list;
    }

    public int checkLogin(String id, String pass){
        String sql = "SELECT * FROM THUTHU WHERE MATT=? AND MATKHAU=?";
        List<ThuThuDTO> list = getData(sql,id,pass);
        if(list.size() == 0){
            return -1;
        }
        return 1;
    }
}
