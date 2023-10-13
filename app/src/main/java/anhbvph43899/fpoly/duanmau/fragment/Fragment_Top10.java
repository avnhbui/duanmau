package anhbvph43899.fpoly.duanmau.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import anhbvph43899.fpoly.duanmau.DAO.PhieuMuonDAO;
import anhbvph43899.fpoly.duanmau.R;
import anhbvph43899.fpoly.duanmau.adapter.Top10Adapter;
import anhbvph43899.fpoly.duanmau.model.PhieuMuon;
import anhbvph43899.fpoly.duanmau.model.Top10;

public class Fragment_Top10 extends Fragment {
    RecyclerView rcvTop10;
    ArrayList<Top10> list = new ArrayList<>();
    PhieuMuonDAO phieuMuonDAO;
    Top10Adapter top10Adapter;

    public Fragment_Top10() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__top10, container, false);
        rcvTop10 = view.findViewById(R.id.rcvTop10);
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getTop();
        top10Adapter = new Top10Adapter(getContext(), list);
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvTop10.setLayoutManager(linearLayoutManager);
        rcvTop10.setAdapter(top10Adapter);
        top10Adapter.notifyDataSetChanged();
        return view;
    }
}