package com.example.easymarkdown;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.easymarkdown.MarkdownIt;

public class MarkdownPreviewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MarkdownIt markdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_preview);

        markdownView = findViewById(R.id.markdown_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preview_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_editor) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}