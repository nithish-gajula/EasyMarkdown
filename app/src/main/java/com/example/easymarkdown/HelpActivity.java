package com.example.easymarkdown;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        MarkdownIt markdownHelpMarkdownIt = findViewById(R.id.markdown_help_markdownIt);

        Toolbar toolbar = findViewById(R.id.help_toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        String markdownText = loadMarkdownFromAssets();
        markdownHelpMarkdownIt.setMarkdownString(markdownText);

    }

    private String loadMarkdownFromAssets() {
        StringBuilder sb = new StringBuilder();
        AssetManager assetManager = getAssets();
        try (InputStream is = assetManager.open("helper_markdown.md");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
