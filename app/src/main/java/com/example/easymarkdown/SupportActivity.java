package com.example.easymarkdown;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SupportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView support_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        support_info = findViewById(R.id.support_info_id);
        toolbar = findViewById(R.id.support_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SpannableString spannableString = new SpannableString(getString(R.string.support_info));
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 461, 474, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        support_info.setText(spannableString);

    }
}