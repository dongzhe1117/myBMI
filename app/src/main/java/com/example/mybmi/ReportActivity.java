package com.example.mybmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import java.text.DecimalFormat;


public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initViews();
        showResult();
        setListensers();
    }

    private Button button_back;
    private TextView show_result;
    private TextView show_suggest;
    private double BMI;

    private void initViews()
    {
        button_back = findViewById(R.id.button);
        show_result = findViewById(R.id.result);
        show_suggest = findViewById(R.id.suggest);
    }

    private void showResult()
    {
        try
        {
            DecimalFormat nf = new DecimalFormat("0.00");
            Bundle bundle = this.getIntent().getExtras();

            //身高
            double height = Double.parseDouble(bundle.getString("KEY_HEIGHT"))/100;

            //體重
            double weight = Double.parseDouble(bundle.getString("KEY_WEIGHT"));

            //計算BMI
            BMI = weight / (height * height);    //double BMI = weight / height * height ;

            //結果
            show_result.setText(getText(R.string.bmi_result) + nf.format(BMI));

            //建議
            if(BMI>25)
                show_suggest.setText(R.string.advice_heavy);
            else if(BMI<20)
                show_suggest.setText(R.string.advice_light);
            else
                show_suggest.setText(R.string.advice_average);

        }
        catch(Exception ex)
        {
            Toast.makeText(this,"必須輸入身高體重!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setListensers()
    {
        button_back.setOnClickListener(backtoMain);
    }

    private Button.OnClickListener backtoMain = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            DecimalFormat nf = new DecimalFormat("0.00");
            Bundle bundle = new Bundle();
            Intent intent = new Intent();

            bundle.putString("BMI", nf.format(BMI));
            intent.putExtras(bundle);

            setResult(RESULT_OK,intent);

            ReportActivity.this.finish();
        }
    };



}
