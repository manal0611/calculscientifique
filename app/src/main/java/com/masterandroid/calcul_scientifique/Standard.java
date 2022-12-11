package com.masterandroid.calcul_scientifique;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;


public class Standard  extends AppCompatActivity implements View.OnClickListener{

    TextView result,solution;
    MaterialButton buttonC,buttonOpenBracket,buttonCloseBracket,buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC,buttonDot;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard);
        result = findViewById(R.id.result_tv);
        solution = findViewById(R.id.solution_tv);

        assignId(buttonC,R.id.button_c);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonOpenBracket,R.id.button_open_bracket);
        assignId(buttonCloseBracket,R.id.button_close_bracket);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEquals,R.id.button_equals);
        assignId(buttonDot,R.id.button_dot);
        assignId(buttonAC,R.id.button_ac);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
    }
    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        String dataToCalc = solution.getText().toString();

        if(buttonText.equals("AC")){
            solution.setText("");
            result.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solution.setText(result.getText());
            return;
        }
        if(buttonText.equals("C")){
            if(dataToCalc.length()>1)
                dataToCalc = dataToCalc.substring(0,dataToCalc.length()-1);
            if(dataToCalc.length()==1){
                dataToCalc="0";
            }
        }

        else{
            if(dataToCalc.equals("0") && buttonText!="0") dataToCalc="";
            ArrayList<String> operators = new ArrayList<String>();
            operators.add("+");
            operators.add("-");
            operators.add("*");
            operators.add("/");

            if(dataToCalc.equals("")){
                if(operators.contains(buttonText))
                    dataToCalc = "0" + buttonText;
                else
                    dataToCalc = dataToCalc + buttonText;
            }else {
                if ((operators.contains(dataToCalc.substring(dataToCalc.length() - 1)) && operators.contains(buttonText))){
                    dataToCalc = dataToCalc.substring(0,dataToCalc.length()-1);
                    dataToCalc += buttonText;
                }else
                    dataToCalc = dataToCalc + buttonText;
            }
        }
        solution.setText(dataToCalc);
        String finalResult = getResult(dataToCalc);
        if(!finalResult.equals("Err")) {
            result.setText(finalResult);
        }


    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }



}
