package anhbvph43899.fpoly.duanmau.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import anhbvph43899.fpoly.duanmau.DAO.PhieuMuonDAO;
import anhbvph43899.fpoly.duanmau.R;

public class Fragment_DoanhThu extends Fragment {
    EditText edtTuNgay, edtDenNgay;
    TextView tvDoanhThu;
    ImageButton btnTuNgay, btnDenNgay;
    Button btnDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    int ngay, thang, nam;
    public Fragment_DoanhThu() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__doanh_thu, container, false);
        //
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        //
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar lich = Calendar.getInstance();

                ngay = lich.get(Calendar.DAY_OF_MONTH);
                thang = lich.get(Calendar.MONTH);
                nam = lich.get(Calendar.YEAR);
                DatePickerDialog bangLich = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtTuNgay.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar lich = Calendar.getInstance();

                ngay = lich.get(Calendar.DAY_OF_MONTH);
                thang = lich.get(Calendar.MONTH);
                nam = lich.get(Calendar.YEAR);

                DatePickerDialog bangLich = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDenNgay.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edtTuNgay.getText().toString();
                String denNgay = edtDenNgay.getText().toString();
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
                tvDoanhThu.setText("Doanh Thu: " + phieuMuonDAO.getDoanhThu(tuNgay, denNgay) + " VNĐ");
            }
        });
        //
        return view;
    }
    DatePickerDialog.OnDateSetListener dateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ngay = dayOfMonth;
            thang = month;
            nam = year;
            GregorianCalendar gregorianCalendar = new GregorianCalendar( nam, thang, ngay);
            edtTuNgay.setText(sdf.format(gregorianCalendar.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener dateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ngay = dayOfMonth;
            thang = month;
            nam = year;
            GregorianCalendar gregorianCalendar = new GregorianCalendar( nam, thang, ngay);
            edtDenNgay.setText(sdf.format(gregorianCalendar.getTime()));
        }
    };

}