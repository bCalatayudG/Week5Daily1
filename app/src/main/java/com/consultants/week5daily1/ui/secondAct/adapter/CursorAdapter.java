package com.consultants.week5daily1.ui.secondAct.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.consultants.week5daily1.R;

public class CursorAdapter extends android.widget.CursorAdapter {

    private TextView tvFirst;
    private TextView tvLast;
    private TextView tvDays;
    private TextView tvSalary;

    public CursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tvFirst = view.findViewById(R.id.tvFirst);
        tvLast = view.findViewById(R.id.tvLast);
        tvDays = view.findViewById(R.id.tvDays);
        tvSalary = view.findViewById(R.id.tvSalary);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String last = cursor.getString(cursor.getColumnIndexOrThrow("last"));
        String day = cursor.getString(cursor.getColumnIndexOrThrow("day"));
        String salary = cursor.getString(cursor.getColumnIndexOrThrow("salary"));

        tvFirst.setText(name);
        tvLast.setText(last);
        tvDays.setText(day);
        tvSalary.setText(salary);

    }
}
