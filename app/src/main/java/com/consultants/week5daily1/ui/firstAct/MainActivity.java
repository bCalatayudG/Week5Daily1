package com.consultants.week5daily1.ui.firstAct;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.consultants.week5daily1.R;
import com.consultants.week5daily1.ui.secondAct.Second_Activity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter presenter;
    private EditText etFirst;
    private EditText etLast;
    private EditText etDays;
    private EditText etSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);
        etDays = findViewById(R.id.etDays);
        etSalary = findViewById(R.id.etSalary);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
    }

    @Override
    public void showError(String error) {

    }

    public void onClickAddName(View view) {
        String first = etFirst.getText().toString();
        String last = etLast.getText().toString();
        String days = etDays.getText().toString();
        String salary = etSalary.getText().toString();

        if (!first.equals("") && !last.equals("") && !days.equals("") && !salary.equals(""))
            presenter.setDataToContent(first, last, days, salary, this);
        else
            Toast.makeText(this,"No empty fields allowed",Toast.LENGTH_SHORT).show();
    }

    public void onClickRetrieveEmployees(View view) {
        Intent intent = new Intent(this, Second_Activity.class);
        startActivity(intent);
    }
}
