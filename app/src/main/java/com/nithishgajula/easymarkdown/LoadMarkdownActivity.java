package com.nithishgajula.easymarkdown;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LoadMarkdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_markdown);

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            Uri uri = intent.getData();
            // Load the file content into the MarkdownEditorActivity
            Intent markdownEditorActivityIntent = new Intent(LoadMarkdownActivity.this, MarkdownEditorActivity.class);
            markdownEditorActivityIntent.putExtra("fileUri", uri.toString());
            startActivity(markdownEditorActivityIntent);
        }
    }

}