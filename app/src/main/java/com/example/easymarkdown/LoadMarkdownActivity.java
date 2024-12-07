package com.example.easymarkdown;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadMarkdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_markdown);

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            Uri uri = intent.getData();
            //loadMarkdownFile(uri); // Load the file content into the editor
            Intent markdownEditorActivityIntent = new Intent(LoadMarkdownActivity.this, MarkdownEditorActivity.class);
            markdownEditorActivityIntent.putExtra("fileUri", uri.toString());
            startActivity(markdownEditorActivityIntent);
        }
    }

    public void loadMarkdownFile1(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            // Assuming you have an EditText named markdownEditor to display content
            //markdownEditor.setText(stringBuilder.toString());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Simulate a click on the second menu item
                    //onOptionsItemSelected(toolbar.getMenu().findItem(R.id.menu_preview));
                }
            }, 500);

        } catch (IOException e) {
            Toast.makeText(this, "Error opening file", Toast.LENGTH_SHORT).show();
        }
    }
}