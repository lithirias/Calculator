package br.feevale.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic);

        LinearLayout historyLinearLayout = findViewById(R.id.linearLayout);
        ArrayList<String> historyList = getIntent().getStringArrayListExtra("history");

        findViewById(R.id.inBack).setOnClickListener(this::goToMain);

        if (historyList != null) {
            for (String entry : historyList) {
                TextView textView = new TextView(this);
                textView.setText(entry);
                textView.setTextSize(18);
                historyLinearLayout.addView(textView);
            }
        }
    }
    public void goToMain(View k){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}

