package dientcph27512.fpoly.duanmau.MyDbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(Context context) {
        super(context, "db_lib", null, 14);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTBThuThu = "CREATE TABLE THUTHU(MATT TEXT PRIMARY KEY,HOTEN TEXT NOT NULL,MATKHAU TEXT NOT NULL)";
        db.execSQL(createTBThuThu);
        createTBThuThu = "INSERT INTO THUTHU VALUES('admin','Trịnh Công Điền','admin')";
        db.execSQL(createTBThuThu);
        createTBThuThu = "INSERT INTO THUTHU VALUES('1','Trịnh Công A','a')";
        db.execSQL(createTBThuThu);
        createTBThuThu = "INSERT INTO THUTHU VALUES('2','Trịnh Công B','b')";
        db.execSQL(createTBThuThu);
        createTBThuThu = "INSERT INTO THUTHU VALUES('3','Trịnh Công C','c')";
        db.execSQL(createTBThuThu);
        createTBThuThu = "INSERT INTO THUTHU VALUES('4','Trịnh Công D','d')";
        db.execSQL(createTBThuThu);
        createTBThuThu = "INSERT INTO THUTHU VALUES('5','Trịnh Công E','e')";
        db.execSQL(createTBThuThu);
        String creatTBThanhVien = "CREATE TABLE THANHVIEN(MATV INTEGER PRIMARY KEY AUTOINCREMENT,HOTEN TEXT NOT NULL,NAMSINH TEXT NOT NULL)";
        db.execSQL(creatTBThanhVien);
        creatTBThanhVien = "INSERT INTO THANHVIEN VALUES(null,'Lê Văn A','03-09-2003')";
        db.execSQL(creatTBThanhVien);
        creatTBThanhVien = "INSERT INTO THANHVIEN VALUES(null,'Lê Văn B','03-09-2003')";
        db.execSQL(creatTBThanhVien);
        creatTBThanhVien = "INSERT INTO THANHVIEN VALUES(null,'Lê Văn C','03-09-2003')";
        db.execSQL(creatTBThanhVien);
        creatTBThanhVien = "INSERT INTO THANHVIEN VALUES(null,'Lê Văn D','03-09-2003')";
        db.execSQL(creatTBThanhVien);
        creatTBThanhVien = "INSERT INTO THANHVIEN VALUES(null,'Lê Văn E','03-09-2003')";
        db.execSQL(creatTBThanhVien);
        String createTBLoaiSach = "CREATE TABLE LOAISACH(MALOAI INTEGER PRIMARY KEY AUTOINCREMENT,TENLOAI TEXT NOT NULL)";
        db.execSQL(createTBLoaiSach);
        createTBLoaiSach = "INSERT INTO LOAISACH VALUES(null,'Truyện Tranh')";
        db.execSQL(createTBLoaiSach);
        createTBLoaiSach = "INSERT INTO LOAISACH VALUES(null,'Tiểu Thuyết')";
        db.execSQL(createTBLoaiSach);
        createTBLoaiSach = "INSERT INTO LOAISACH VALUES(null,'Lập Trình')";
        db.execSQL(createTBLoaiSach);
        createTBLoaiSach = "INSERT INTO LOAISACH VALUES(null,'Giải Đố')";
        db.execSQL(createTBLoaiSach);
        createTBLoaiSach = "INSERT INTO LOAISACH VALUES(null,'Khoa Học')";
        db.execSQL(createTBLoaiSach);
        String createTBSach = "CREATE TABLE SACH(MASACH INTEGER PRIMARY KEY AUTOINCREMENT,TENSACH TEXT NOT NULL,GIATHUE INTEGER NOT NULL,MALOAI INTERGER REFERENCES LOAISACH(MALOAI))";
        db.execSQL(createTBSach);
        createTBSach = "INSERT INTO SACH VALUES(null,'Conan',1000,1)";
        db.execSQL(createTBSach);
        createTBSach = "INSERT INTO SACH VALUES(null,'Sói Xám',2000,2)";
        db.execSQL(createTBSach);
        createTBSach = "INSERT INTO SACH VALUES(null,'Android',3000,3)";
        db.execSQL(createTBSach);
        createTBSach = "INSERT INTO SACH VALUES(null,'1000 Câu Hỏi',4000,4)";
        db.execSQL(createTBSach);
        createTBSach = "INSERT INTO SACH VALUES(null,'Vũ Trụ',5000,5)";
        db.execSQL(createTBSach);
        String createTBPhieuMuon = "CREATE TABLE PHIEUMUON(MAPM INTEGER PRIMARY KEY AUTOINCREMENT,MATT TEXT REFERENCES THUTHU(MATT),MATV INTEGER REFERENCES THANHVIEN(MATV),MASACH INTEGER REFERENCES SACH(MASACH),TIENTHUE INTEGER NOT NULL,NGAY DATE NOT NULL,TRASACH INTEGER NOT NULL)";
        db.execSQL(createTBPhieuMuon);
        createTBPhieuMuon = "INSERT INTO PHIEUMUON VALUES(null,'1',1,1,1000,'2022/10/03',1)";
        db.execSQL(createTBPhieuMuon);
        createTBPhieuMuon = "INSERT INTO PHIEUMUON VALUES(null,'2',2,2,2000,'2022/10/03',0)";
        db.execSQL(createTBPhieuMuon);
        createTBPhieuMuon = "INSERT INTO PHIEUMUON VALUES(null,'3',3,3,3000,'2022/10/03',1)";
        db.execSQL(createTBPhieuMuon);
        createTBPhieuMuon = "INSERT INTO PHIEUMUON VALUES(null,'4',4,4,4000,'2022/10/03',0)";
        db.execSQL(createTBPhieuMuon);
        createTBPhieuMuon = "INSERT INTO PHIEUMUON VALUES(null,'5',5,5,5000,'2022/10/03',1)";
        db.execSQL(createTBPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTBThuThu = "DROP TABLE IF EXISTS THUTHU";
        db.execSQL(dropTBThuThu);
        String dropTBThanhVien = "DROP TABLE IF EXISTS THANHVIEN";
        db.execSQL(dropTBThanhVien);
        String dropTBLoaiSach = "DROP TABLE IF EXISTS LOAISACH";
        db.execSQL(dropTBLoaiSach);
        String dropTBSach = "DROP TABLE IF EXISTS SACH";
        db.execSQL(dropTBSach);
        String dropTBPhieuMuon = "DROP TABLE IF EXISTS PHIEUMUON";
        db.execSQL(dropTBPhieuMuon);
        onCreate(db);
    }
}
