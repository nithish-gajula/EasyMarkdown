package com.nithishgajula.easymarkdown;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        TextView support_info = findViewById(R.id.support_info_id);
        Toolbar toolbar = findViewById(R.id.support_toolbar);

        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        SpannableString spannableString = new SpannableString(getString(R.string.support_info));
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 446, 459, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        support_info.setText(spannableString);

    }
}