package com.example.mybmi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得控制項事件
        initViews();

        //設定監聽事件
        setListensers();

        //按鈕事件
        //Button btn = (Button)findViewById(R.id.button);
        //btn.setOnClickListener(calcBMI);

    }

    private Button btn;
    //private Button btnreport;
    private EditText num_height;
    private EditText num_weight;
    private TextView show_result;
    private TextView show_suggest;
    private static final int ACTIVITY_REPORT = 1000;


    //取得控制項物件
    private void initViews()
    {
        btn = (Button)findViewById(R.id.button);
        //btnreport = findViewById(R.id.btn_report);
        num_height = (EditText)findViewById(R.id.height);
        num_weight = (EditText)findViewById(R.id.weight);
        show_result = (TextView)findViewById(R.id.result);
        show_suggest=(TextView)findViewById(R.id.suggest);
    }

    //設定監聽事件
    private void setListensers()
    {
        btn.setOnClickListener(calcBMI);
        //btnreport.setOnClickListener(reportBMI);
    }


    private void showMsg(int msg){
        //彈出訊息
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(msg);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void toastMsg(CharSequence cs){
        Toast.makeText(this,cs,Toast.LENGTH_LONG).show();
    }


    /*
    private View.OnClickListener reportBMI = new View.OnClickListener(){
        @Override
        public void onClick(View v){        }
    };
    */


    private View.OnClickListener calcBMI = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try
            {
                //傳遞結果
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ReportActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("KEY_HEIGHT", num_height.getText().toString());
                bundle.putString("KEY_WEIGHT", num_weight.getText().toString());
                intent.putExtras(bundle);
                //startActivity(intent);
                startActivityForResult(intent,ACTIVITY_REPORT);

            /*
            //Toast toast;
            DecimalFormat nf = new DecimalFormat("0.00");
            //EditText fieldheight = (EditText)findViewById(R.id.height);
            //EditText fieldweight = (EditText)findViewById(R.id.weight);

            //檢查輸入欄位
            if ("".equals(num_height.getText().toString()))
            {
                showMsg(R.string.alert_height);
                //toast.makeText(R.string.alert_height,Toast.LENGTH_LONG).show();
            }
            else if ("".equals(num_weight.getText().toString()))
            {
                showMsg(R.string.alert_weight);
                //toast.makeText(R.string.alert_weight,Toast.LENGTH_LONG).show();
            }
            else
            {
                //身高
                Double height = Double.parseDouble(num_height.getText().toString())/100;

                //體重
                Double weight = Double.parseDouble(num_weight.getText().toString());

                //計算BMI
                Double BMI = weight / (height*height);

                //結果
                //TextView result = (TextView)findViewById(R.id.result);
                show_result.setText(getText(R.string.bmi_result) + nf.format(BMI));

                //建議
                //TextView fieldsuggest = (TextView)findViewById(R.id.suggest);
                if(BMI > 25)
                    show_suggest.setText(R.string.advice_heavy);
                else if(BMI<20)
                    show_suggest.setText(R.string.advice_light);
                else
                    show_suggest.setText(R.string.advice_average);
            }
            */

            }
            catch(Exception e)
            {
                String str = "請輸入身高體重!!! " + e.getMessage();
                CharSequence cs = str;
                toastMsg(cs);
                //Toast.makeText(this,cs,Toast.LENGTH_LONG).show();
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == ACTIVITY_REPORT)
            {
                Bundle bundle = intent.getExtras();
                String bmi = bundle.getString("BMI");

                show_suggest.setText(getString(R.string.advice_history) + bmi);
                num_weight.setText(R.string.input_empty);
                num_weight.requestFocus();
            }
        }
    }

    //加入Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id)
        {
            case R.id.item1:
                openOptionToast();  //openOptionsDialog();
            break;
        }

        /*
        if(id == R.id.item1){
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    private void openOptionsDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("關於Android BMI")
                .setMessage("Android BMI 計算")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener(){
                    @Override
                            public void onClick(DialogInterface dialog,int which) {}
                        })
                .show();
    }

    private void openOptionToast()
    {
        Toast popup = Toast.makeText(this,"BMI計算機",Toast.LENGTH_SHORT);
        popup.show();
    }

}
