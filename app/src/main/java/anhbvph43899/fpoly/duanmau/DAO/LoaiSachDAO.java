package anhbvph43899.fpoly.duanmau.DAO;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.database.dbHelper;
import anhbvph43899.fpoly.duanmau.model.LoaiSach;

public class LoaiSachDAO {
    private final dbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new dbHelper(context);
    }

    public ArrayList<LoaiSach> selectAll() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM LoaiSach", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    LoaiSach loaiSach = new LoaiSach();
                    loaiSach.setMaLoai(cursor.getInt(0));
                    loaiSach.setTenLoai(cursor.getString(1));
                    list.add(loaiSach);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
    public boolean insert(LoaiSach loaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long row = db.insert("LoaiSach", null, values);
        return (row > 0);
    }
    public boolean update(LoaiSach loaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long row = db.update("LoaiSach", values, "maLoai = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
        return (row > 0);
    }
    public boolean delete(int maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("LoaiSach", "maLoai=?", new String[]{String.valueOf(maLoai)});
        return (row > 0);
    }
    int row;
    public int getMaLoai(String tenLoai) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT maLoai FROM LoaiSach WHERE LoaiSach.tenLoai = ?", new String[] {tenLoai});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    row = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return row;
    }
}
