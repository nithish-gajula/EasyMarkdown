package com.nithishgajula.easymarkdown;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        TextView support_info = findViewById(R.id.support_info_id);
        Toolbar toolbar = findViewById(R.id.support_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        SpannableString spannableString = new SpannableString(getString(R.string.support_info));
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 446, 459, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        support_info.setText(spannableString);

        applyDisplayCutouts();
        applyAppBarCutouts();

    }

    private void applyDisplayCutouts() {
        // Works in portrait mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.support_content), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime() | WindowInsetsCompat.Type.displayCutout());

            v.setPadding(bars.left, 0, bars.right, bars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void applyAppBarCutouts() {
        // Works in Landscape mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.support_appbar), (v, insets) -> {
            Insets appbars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(0, appbars.top, 0, 0);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}