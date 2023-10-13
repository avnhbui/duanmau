package anhbvph43899.fpoly.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private static final String dbName = "QLTV";
    private static final int version = 2;

    public dbHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ThuThu = "CREATE TABLE ThuThu(" +
                "maTT text primary key, " +
                "tenTT text not null, " +
                "matKhau text not null, " +
                "chucVu integer)";
        String ThemThuThu = "INSERT INTO ThuThu VALUES ('admin', 'Bui Viet Anh', 'admin1', 0)";
        db.execSQL(ThuThu);
        db.execSQL(ThemThuThu);
        //thanhvien
        String ThanhVien= "CREATE TABLE ThanhVien(" +
                "maTV integer primary key autoincrement, " +
                "tenTV text not null, " +
                "namSinh text not null)";
        db.execSQL(ThanhVien);
        String ThemThanhVien = "INSERT INTO ThanhVien VALUES ( 1, 'Bui Viet Anh', '06/11/2004'), ( 2, 'Ngoc Hai', '20/10/2004'), ( 3, 'Do Dat', '21/10/2004')";
        db.execSQL(ThemThanhVien);
        //loaisach
        String LoaiSach= "CREATE TABLE LoaiSach(" +
                "maLoai integer primary key autoincrement, " +
                "tenLoai text not null)";
        db.execSQL(LoaiSach);
        //sach
        String Sach= "CREATE TABLE Sach(" +
                "maSach integer primary key autoincrement, " +
                "tenSach text not null, " +
                "giaThue integer not null, " +
                "maLoai integer REFERENCES LoaiSach(maLoai))";
        db.execSQL(Sach);
        //phieumuon
        String PhieuMuon= "CREATE TABLE PhieuMuon(" +
                "maPhieuMuon integer primary key autoincrement, " +
                "maTT text REFERENCES ThuThu(maTT), " +
                "maTV integer REFERENCES ThanhVien(maTV), " +
                "maSach integer REFERENCES Sach(maSach), " +
                "tienThue integer not null,  " +
                "ngayMuon text not null, " +
                "traSach integer not null)";
        db.execSQL(PhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }
}
