package com.example.michalzahir.pagk16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class resultActivity extends AppCompatActivity {
    private TextView firstUserResultTextView;
    private TextView secondUserResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        firstUserResultTextView = (TextView) findViewById(R.id.firstUserResult );
        secondUserResultTextView = (TextView) findViewById(R.id.secondUserResult);
        Bundle bundle = this.getIntent().getExtras();
        System.out.println("first result from bundle"+bundle.getInt("1st user result")+"      second result from bundle"+bundle.getInt("2nd user result"));
        int intFirstResult;
        int intSecondResult;
        intFirstResult = bundle.getInt("1st user result");
        intSecondResult = bundle.getInt("2nd user result");
        firstUserResultTextView.setText(Integer.toString(intFirstResult)+":" );
        secondUserResultTextView.setText(Integer.toString(intSecondResult));
    }
}
