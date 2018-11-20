package com.consultants.week5daily1.ui.secondAct;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.consultants.week5daily1.R;
import com.consultants.week5daily1.ui.secondAct.adapter.CursorAdapter;

public class Second_Activity extends AppCompatActivity {

    private ListView lvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);

        String URL = "content://com.consultants.week5daily1.services.EmployeesProvider";

        Uri employees = Uri.parse(URL);
        Cursor c = managedQuery(employees, null, null, null, "name");

        lvSecond = findViewById(R.id.lvSecond);
        CursorAdapter todoAdapter = new CursorAdapter(this, c);
        lvSecond.setAdapter(todoAdapter);
    }
}
