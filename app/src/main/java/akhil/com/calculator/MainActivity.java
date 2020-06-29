package akhil.com.calculator;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RelativeLayout relativeLayout;
    private TextInputLayout Classes_Attended,Classes_Conducted,Desired_Percentage;
    AppCompatButton CalculateButton,ResetButton;
    private AppCompatTextView result,result2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout=(RelativeLayout)findViewById(R.id.Activity_Main);
        relativeLayout.setOnClickListener(null);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Classes_Attended=(TextInputLayout) findViewById(R.id.classes1_TextInputLayout);
        Classes_Conducted=(TextInputLayout) findViewById(R.id.classes2_TextInputLayout);
        Desired_Percentage=(TextInputLayout) findViewById(R.id.classes3_TextInputLayout);

        result=(AppCompatTextView)findViewById(R.id.text_view2);
        result2=(AppCompatTextView)findViewById(R.id.text_view3);


        CalculateButton=(AppCompatButton)findViewById(R.id.calculate_button);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                String classAttended= Objects.requireNonNull(Classes_Attended.getEditText()).getText().toString().trim();
                String classConducted= Objects.requireNonNull(Classes_Conducted.getEditText()).getText().toString().trim();
                String desiredPercentage= Objects.requireNonNull(Desired_Percentage.getEditText()).getText().toString().trim();
                int classattended,classconducted,desiredpercentage;
                if(classAttended.isEmpty()||classConducted.isEmpty()||desiredPercentage.isEmpty())
                {
                    result.setText(getString(R.string.errorInput));
                    result2.setText(null);
                }
                else
                {
                    classattended=Integer.parseInt(classAttended);
                    classconducted=Integer.parseInt(classConducted);
                    desiredpercentage=Integer.parseInt(desiredPercentage);
                    float percent = ((float) classattended / (float) classconducted) * 100;
                    int samepercent=(classattended/classconducted)*100;
                    int attend = 0,bunk=0;
                    double y=0;
                    double d=0;
                    if(classattended<=classconducted&&desiredpercentage<100&&desiredpercentage>0) {
                        if (classattended > 5000000 || classconducted > 5000000) {
                            result.setText(getString(R.string.sizeError));
                            result2.setText(null);
                        }
                        else {
                            result.setText(String.format(getString(R.string.result_percent), percent));
                            if(percent==desiredpercentage){
                                result2.setText(null);
                            }

                            if (percent > desiredpercentage) {
                                int i;
                                d = ((double) classattended / (double) classconducted) * 100;
                                double z = (double) desiredpercentage;
                                for (i = 0; d > desiredpercentage; i++) {
                                    if (Math.ceil(d) == desiredpercentage)
                                        break;
                                    d = ((double) (classattended) / (double) (classconducted + i)) * 100;
                                    bunk = i;
                                    if (d < desiredpercentage) {
                                        bunk = bunk - 1;
                                        break;
                                    }
                                    if (Math.ceil(d) == desiredpercentage)
                                        break;
                                }
                                result2.setText(String.format(getString(R.string.result_bunk), bunk));
                            }
                            if (percent < desiredpercentage) {
                                int i;
                                for (i = 0; y <= desiredpercentage; i++) {
                                    if (y == desiredpercentage)
                                        break;
                                    y = ((double) (classattended + i) / (double) (classconducted + i)) * 100;
                                    attend = i;
                                    if (y == desiredpercentage)
                                        break;
                                }
                                result2.setText(String.format(getString(R.string.result_attend), attend));
                            }
                        }
                    }
                    else {
                        if(desiredpercentage==100&&classattended!=classconducted)
                        {
                            result.setText(getString(R.string.errorHundred));
                            result2.setText(null);
                        }
                        if(desiredpercentage==100&&classattended==classconducted){
                            result.setText(String.format(getString(R.string.result_percent), percent));
                            result2.setText(null);
                        }
                        if(desiredpercentage>100||desiredpercentage==0)
                        {
                            result.setText(R.string.errorpercent);
                            result2.setText(null);
                        }
                        if(classattended>classconducted){
                        result.setText(getString(R.string.errorNumber));
                        result2.setText(null);}
                    }
                }
            }
        });

        ResetButton=(AppCompatButton)findViewById(R.id.reset_button);
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(Classes_Attended.getEditText()).getText().clear();
                Objects.requireNonNull(Classes_Conducted.getEditText()).getText().clear();
                Objects.requireNonNull(Desired_Percentage.getEditText()).getText().clear();
                Classes_Attended.requestFocus();
            }
        });

        findViewById(R.id.Activity_Main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View focusedView = getCurrentFocus();
                if (v != null && focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            /*case R.id.setting_id:
                break;*/
            case R.id.rate_id:
                Uri uriUrl = Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*public void rateMe(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }*/
}
