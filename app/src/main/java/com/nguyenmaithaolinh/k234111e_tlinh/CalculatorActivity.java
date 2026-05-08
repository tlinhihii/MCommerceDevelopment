package com.nguyenmaithaolinh.k234111e_tlinh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    private TextView txtDisplay;
    private String currentNumber = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isOperatorClicked = false;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        
        txtDisplay = findViewById(R.id.txtDisplay);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onNumberClick(View view) {
        if (isOperatorClicked) {
            currentNumber = "";
            isOperatorClicked = false;
        }

        String val = ((Button) view).getText().toString();
        
        if (val.equals(".") && currentNumber.contains(".")) {
            return;
        }
        
        if (currentNumber.equals("0") && !val.equals(".")) {
            currentNumber = val;
        } else {
            currentNumber += val;
        }
        
        updateDisplay();
    }

    public void onOperatorClick(View view) {
        String op = ((Button) view).getText().toString();

        switch (op) {
            case "⌫":
                if (currentNumber.length() > 0) {
                    currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                    if (currentNumber.isEmpty()) currentNumber = "0";
                }
                updateDisplay();
                break;
            case "+/−":
                if (!currentNumber.isEmpty() && !currentNumber.equals("0")) {
                    double val = Double.parseDouble(currentNumber);
                    currentNumber = decimalFormat.format(val * -1);
                    updateDisplay();
                }
                break;
            case "%":
                if (!currentNumber.isEmpty()) {
                    double val = Double.parseDouble(currentNumber);
                    currentNumber = decimalFormat.format(val / 100);
                    updateDisplay();
                }
                break;
            default: // +, −, ×, ÷
                if (!currentNumber.isEmpty()) {
                    firstOperand = Double.parseDouble(currentNumber);
                    operator = op;
                    isOperatorClicked = true;
                }
                break;
        }
    }

    public void onClearClick(View view) {
        currentNumber = "0";
        firstOperand = 0;
        operator = "";
        isOperatorClicked = false;
        updateDisplay();
    }

    public void onEqualClick(View view) {
        if (operator.isEmpty() || currentNumber.isEmpty()) return;

        double secondOperand = Double.parseDouble(currentNumber);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "−":
                result = firstOperand - secondOperand;
                break;
            case "×":
                result = firstOperand * secondOperand;
                break;
            case "÷":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    txtDisplay.setText("Error");
                    currentNumber = "0";
                    operator = "";
                    return;
                }
                break;
        }

        currentNumber = decimalFormat.format(result);
        operator = "";
        updateDisplay();
        isOperatorClicked = true;
    }

    private void updateDisplay() {
        if (currentNumber.isEmpty() || currentNumber.equals("0")) {
            txtDisplay.setText("0");
        } else {
            txtDisplay.setText(currentNumber);
        }
    }
}
