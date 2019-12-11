package com.wgc.labtwo;


import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    View view;
    SQLiteDB sqLiteDB;
    Cursor cursor;
    TextView textView;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_list, container, false);
        view = inflater.inflate(R.layout.fragment_list, container,false);
        sqLiteDB = SQLiteDB.getSingleInstance(getContext());
        cursor = sqLiteDB.getAllCollege();

        if(cursor.getCount() > 0) {
            cursor.moveToNext();
            textView = view.findViewById(R.id.college_name);
            textView.setText(cursor.getString(1));
            textView = view.findViewById(R.id.college_loc);
            textView.setText(cursor.getString(2));
            textView = view.findViewById(R.id.college_sat);
            textView.setText(cursor.getInt(3) + "");

            System.out.println("get data from sqlite." + cursor.getString(1));
            System.out.println("get data from sqlite." + cursor.getString(2));
            System.out.println("get data from sqlite." + cursor.getInt(3));
        }

        return view;
    }


}
