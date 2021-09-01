package com.example.practiceudemy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    private EditText newNumber;
    private EditText result;
    private TextView showOperation;

    private Double operand1 = null;

    private String pendingOperation = "=";
    private static final String OPERATION_STATE = "operation";
    private static final String OPERAND_STATE = "operand1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        inIt();
    }

    private void inIt() {
        newNumber = findViewById(R.id.newNumber);
        result = findViewById(R.id.result);
        showOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.button_dot);

        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonSubtract = findViewById(R.id.button_subtract);
        Button buttonMultiply = findViewById(R.id.button_multiply);
        Button buttonDivide = findViewById(R.id.button_divide);
        Button buttonEqual = findViewById(R.id.button_equal);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String operator = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, operator);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                showOperation.setText(operator);

                pendingOperation = operator;
            }
        };


        buttonAdd.setOnClickListener(operatorListener);
        buttonSubtract.setOnClickListener(operatorListener);
        buttonMultiply.setOnClickListener(operatorListener);
        buttonDivide.setOnClickListener(operatorListener);
        buttonEqual.setOnClickListener(operatorListener);
    }

    private void performOperation(Double value, String operator) {
        if (operand1 == null) {
            operand1 = Double.valueOf(value);
        } else {
            if (pendingOperation.equals("="))
                pendingOperation = operator;

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "/":
                    if (value == 0)
                        operand1 = 0.0;
                    else
                        operand1 /= value;
                    break;
            }
        }
        newNumber.setText("");
        result.setText(operand1.toString());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(OPERATION_STATE, pendingOperation);
        if (operand1 != null)
            outState.putDouble(OPERAND_STATE, operand1);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(OPERATION_STATE);
        operand1 = savedInstanceState.getDouble(OPERAND_STATE);
        showOperation.setText(pendingOperation);
    }
}