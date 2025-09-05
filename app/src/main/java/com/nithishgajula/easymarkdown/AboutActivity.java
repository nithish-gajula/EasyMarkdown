package com.nithishgajula.easymarkdown;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView developerSite = findViewById(R.id.developer_site_id);
        TextView appVersionName = findViewById(R.id.app_version_name);
        ImageView gmail = findViewById(R.id.gmail_img_id);
        ImageView github = findViewById(R.id.github_img_id);
        ImageView instagram = findViewById(R.id.instagram_img_id);
        ImageView linkedin = findViewById(R.id.linkedin_img_id);
        Toolbar toolbar = findViewById(R.id.about_toolbar);

        try {
            PackageInfo pInfo;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.PackageInfoFlags.of(0));
            } else {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            }
            String version = pInfo.versionName;
            appVersionName.setText(version != null ? version : getString(R.string.version_info_error));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AboutActivity", "Failed to get package info", e);
            appVersionName.setText(R.string.version_info_error);
        }

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        developerSite.setOnClickListener(v -> openUrl(getString(R.string.site_developer)));
        gmail.setOnClickListener(v -> sendEmail(getString(R.string.email_info)));
        github.setOnClickListener(v -> openUrl(getString(R.string.github_info)));
        instagram.setOnClickListener(v -> openUrl(getString(R.string.instagram_info)));
        linkedin.setOnClickListener(v -> openUrl(getString(R.string.linkedin_info)));

        applyDisplayCutouts();
        applyAppBarCutouts();
    }

    final void openUrl(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No browser or suitable app found to open this link.", Toast.LENGTH_SHORT).show();
        }
    }

    final void sendEmail(String emailUri) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(emailUri));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No browser or suitable app found to open this link.", Toast.LENGTH_SHORT).show();
        }
    }

    private void applyDisplayCutouts() {
        // Works in portrait mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.about_content), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime() | WindowInsetsCompat.Type.displayCutout());

            v.setPadding(bars.left, 0, bars.right, bars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void applyAppBarCutouts() {
        // Works in Landscape mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.about_appbar), (v, insets) -> {
            Insets appbars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(0, appbars.top, 0, 0);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}