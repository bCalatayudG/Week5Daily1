package com.consultants.week5daily1.ui.firstAct;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.consultants.week5daily1.services.EmployeesProvider;

public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void setDataToContent(String first, String last, String days, String salary, Context context) {
        // Add a new student record
        ContentValues values = new ContentValues();
        values.put(EmployeesProvider.NAME, first);

        values.put(EmployeesProvider.LAST, last);

        values.put(EmployeesProvider.DAYS, days);

        values.put(EmployeesProvider.SALARY, salary);

        Uri uri = context.getContentResolver().insert(
                EmployeesProvider.CONTENT_URI, values);

        assert uri != null;
        Toast.makeText(context,
                uri.toString(), Toast.LENGTH_LONG).show();
    }
}
