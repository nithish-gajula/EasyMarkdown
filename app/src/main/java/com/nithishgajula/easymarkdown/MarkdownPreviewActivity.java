package com.nithishgajula.easymarkdown;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MarkdownPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_preview);

        MarkdownIt markdownView = findViewById(R.id.markdown_view);
        Toolbar toolbar = findViewById(R.id.preview_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // Check if the Intent has markdown content
        Intent intent = getIntent();
        if (intent != null) {
            String markdownContent = intent.getStringExtra("markdownContent");
            if (markdownContent != null) {
                markdownView.setMarkdownString(markdownContent);
            } else {
                Toast.makeText(this, "No content received", Toast.LENGTH_SHORT).show();
            }
        }

        applyDisplayCutouts();
        applyAppBarCutouts();
    }

    private void applyDisplayCutouts() {
        // Works in portrait mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.markdown_view), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime() | WindowInsetsCompat.Type.displayCutout());

            v.setPadding(bars.left, 0, bars.right, bars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void applyAppBarCutouts() {
        // Works in Landscape mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.preview_appbar), (v, insets) -> {
            Insets appbars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(0, appbars.top, 0, 0);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}