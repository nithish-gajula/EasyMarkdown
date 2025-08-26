package com.nithishgajula.easymarkdown;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        String markdownRawText = loadMarkdownFromAssets();
        markdownHelpMarkdownIt.setMarkdownString(markdownRawText);

        TextView helpTextView = findViewById(R.id.markdown_help_textview);
        helpTextView.setText(markdownRawText);

        Button switchButton = findViewById(R.id.switch_view_button);
        switchButton.setOnClickListener(view -> {
            if (switchButton.getText() == getString(R.string.switch_to_help_raw)) {
                markdownHelpMarkdownIt.setVisibility(View.GONE);
                helpTextView.setVisibility(View.VISIBLE);
                switchButton.setText(R.string.switch_to_help_preview);
            } else {
                helpTextView.setVisibility(View.GONE);
                markdownHelpMarkdownIt.setVisibility(View.VISIBLE);
                switchButton.setText(R.string.switch_to_help_raw);
            }
        });

        ImageButton copyRawTextButton = findViewById(R.id.copy_raw_text);
        copyRawTextButton.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Raw Markdown Text", markdownRawText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
        });
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
            Log.e("HelpActivity", "Failed to load markdown file", e);
            return "Error loading help file.";
        }
        return sb.toString();
    }
}
