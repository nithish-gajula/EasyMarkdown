package com.nithishgajula.easymarkdown;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appVersionName.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AboutActivity", "Failed to get package info", e);
            appVersionName.setText(R.string.version_info_error);
        }

        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        developerSite.setOnClickListener(v -> {
            Uri uri = Uri.parse(getString(R.string.site_developer));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        gmail.setOnClickListener(v -> {
            Uri uri = Uri.parse(getString(R.string.email_info));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        github.setOnClickListener(v -> {
            Uri uri = Uri.parse(getString(R.string.github_info));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        instagram.setOnClickListener(v -> {
            Uri uri = Uri.parse(getString(R.string.instagram_info));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        linkedin.setOnClickListener(v -> {
            Uri uri = Uri.parse(getString(R.string.linkedin_info));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}