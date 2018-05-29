package com.example.domagoj.radiostudentapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by User on 2/28/2017.
 */

public class VijestiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vijesti_fragment,container,false);
        Button btnTEST = (Button) view.findViewById(R.id.btnTEST);

        btnTEST.setOnClickListener(v ->
                Toast.makeText(getActivity(), "TESTING Vijesti BUTTON CLICK",
                        Toast.LENGTH_SHORT).show());

        return view;
    }
}
