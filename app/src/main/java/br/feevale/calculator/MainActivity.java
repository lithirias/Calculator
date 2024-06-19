package br.feevale.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private StringBuilder inputNumber = new StringBuilder();
    private TextView display;
    private double currentValue = 0.0;
    private String currentOperation = "";
    private boolean isOperationSelected = false;
    private ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.textView);

        int[] buttonIds = {
                R.id.inZero, R.id.inOne, R.id.inTwo, R.id.inThree, R.id.inFour,
                R.id.inFive, R.id.inSix, R.id.inSeven, R.id.inEight, R.id.inNine,
                R.id.inPoint, R.id.inAdd, R.id.inSub, R.id.inTime, R.id.inDiv,
                R.id.inEqual, R.id.inClear
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this::onButtonClick);
        }
        findViewById(R.id.inHistory).setOnClickListener(view -> openHistory());
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();

        switch (text) {
            case "+":
            case "-":
            case "*":
            case "/":
                onOperationClick(text);
                break;
            case "=":
                onEqualClick();
                break;
            case "CLEAR":
                onClearClick();
                break;
            default:
                onNumberClick(text);
                break;
        }
    }

    private void onClearClick() {
        inputNumber.setLength(0);
        currentValue = 0.0;
        currentOperation = "";
        isOperationSelected = false;
        display.setText("");
    }

    private void onNumberClick(String text) {
        if (isOperationSelected) {
            inputNumber.setLength(0); // Clear the input for the new number
            isOperationSelected = false;
        }
        inputNumber.append(text);
        display.setText(inputNumber.toString());
    }

    private void onOperationClick(String operation) {
        if (inputNumber.length() > 0) {
            currentValue = Double.parseDouble(inputNumber.toString());
            currentOperation = operation;
            isOperationSelected = true;
        }
    }

    private void onEqualClick() {
        if (inputNumber.length() > 0 && !currentOperation.isEmpty()) {
            double secondValue = Double.parseDouble(inputNumber.toString());
            double result = 0.0;

            switch (currentOperation) {
                case "+":
                    result = currentValue + secondValue;
                    break;
                case "-":
                    result = currentValue - secondValue;
                    break;
                case "*":
                    result = currentValue * secondValue;
                    break;
                case "/":
                    if (secondValue != 0) {
                        result = currentValue / secondValue;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            String historyEntry = currentValue + " " + currentOperation + " " + secondValue + " = " + result;
            historyList.add(historyEntry);

            display.setText(String.valueOf(result));
            inputNumber.setLength(0);
            inputNumber.append(result);
            currentValue = result;
            currentOperation = "";
        }
    }

    public void openHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putStringArrayListExtra("history", historyList);
        startActivity(intent);
    }
}