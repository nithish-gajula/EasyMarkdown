package com.example.easymarkdown;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MarkdownEditorActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private long pressedTime = 0;
    private EditText markdownEditor;
    private TextView drawerVersion;
    NavigationView navigationView;
    private ImageView tabIcon;
    private ImageView boldIcon;
    private ImageView italicIcon;
    private ImageView strikeIcon;
    private ImageView headerIcon;
    private ImageView quoteIcon;
    private ImageView listIcon;
    private ImageView codeIcon;
    private ImageView codeBlockIcon;
    private ImageView tableIcon;
    private ImageView horizontalRowIcon;
    private ImageView imageIcon;
    private ImageView linkIcon;
    private ImageView wordCountIcon;
    private static final int CREATE_FILE_REQUEST_CODE = 1;
    private static final int OPEN_FILE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_editor);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerVersion = findViewById(R.id.drawer_version);
        tabIcon = findViewById(R.id.tabIcon);
        boldIcon = findViewById(R.id.boldIcon);
        italicIcon = findViewById(R.id.italicIcon);
        strikeIcon = findViewById(R.id.strikeIcon);
        headerIcon = findViewById(R.id.headerIcon);
        quoteIcon = findViewById(R.id.quoteIcon);
        listIcon = findViewById(R.id.listIcon);
        codeIcon = findViewById(R.id.codeIcon);
        codeBlockIcon = findViewById(R.id.codeBlockIcon);
        linkIcon = findViewById(R.id.linkIcon);
        imageIcon = findViewById(R.id.imgIcon);
        horizontalRowIcon = findViewById(R.id.horizontalRowIcon);
        tableIcon = findViewById(R.id.tableIcon);
        wordCountIcon = findViewById(R.id.wordIcon);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        markdownEditor = findViewById(R.id.markdown_editor);
        drawerVersion.setText(getString(R.string.version));

        Intent intent = getIntent();
        String uriString = intent.getStringExtra("fileUri");
        if (uriString != null) {
            Uri uri = Uri.parse(uriString);
            loadMarkdownFile(uri);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_help) {
                    Intent intent = new Intent(MarkdownEditorActivity.this, HelpActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(MarkdownEditorActivity.this, SupportActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.nav_relaunch) {
                    finishAffinity();
                    startActivity(new Intent(MarkdownEditorActivity.this, MarkdownEditorActivity.class));
                } else if (item.getItemId() == R.id.nav_about) {
                    Intent intent = new Intent(MarkdownEditorActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    if (pressedTime + 2000 > System.currentTimeMillis()) {
                        finishAffinity(); // Closes the app
                    } else {
                        Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                    }
                    pressedTime = System.currentTimeMillis();
                }
            }
        });

        tabIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startPosition = markdownEditor.getSelectionStart();
                markdownEditor.getText().insert(startPosition, "    ");
            }
        });

        boldIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectionStart = markdownEditor.getSelectionStart();
                int selectionEnd = markdownEditor.getSelectionEnd();
                Editable text = markdownEditor.getText();
                text.insert(selectionStart, "**");
                text.insert(selectionEnd + 2, "**");
            }
        });


        italicIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectionStart = markdownEditor.getSelectionStart();
                int selectionEnd = markdownEditor.getSelectionEnd();
                Editable text = markdownEditor.getText();
                text.insert(selectionStart, "*");
                text.insert(selectionEnd + 1, "*");
            }
        });

        strikeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectionStart = markdownEditor.getSelectionStart();
                int selectionEnd = markdownEditor.getSelectionEnd();
                Editable text = markdownEditor.getText();
                text.insert(selectionStart, "~~");
                text.insert(selectionEnd + 2, "~~");
            }
        });

        headerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectionStart = markdownEditor.getSelectionStart();
                int selectionEnd = markdownEditor.getSelectionEnd();
                Editable text = markdownEditor.getText();
                text.insert(selectionStart, "# ");
                text.insert(selectionEnd + 2, "\n");
            }
        });

        quoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markdownEditor.getText().insert(markdownEditor.getSelectionStart(), ">");
            }
        });

        listIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "* ");
            }
        });

        codeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectionStart = markdownEditor.getSelectionStart();
                int selectionEnd = markdownEditor.getSelectionEnd();
                Editable text = markdownEditor.getText();
                text.insert(selectionStart, "`");
                text.insert(selectionEnd + 1, "`");
            }
        });

        codeBlockIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectionStart = markdownEditor.getSelectionStart();
                markdownEditor.getText().insert(selectionStart, "\n``` python\n print(\"Hello Python\")\n```");
                int i4 = selectionStart + 10;
                markdownEditor.setSelection(i4, i4);

            }
        });

        tableIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogForTable();
            }
        });

        horizontalRowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.horizontal_row_setting_dialog, null);
                RadioButton radioButton1 = dialogView.findViewById(R.id.radioButton1);
                RadioButton radioButton2 = dialogView.findViewById(R.id.radioButton2);
                Button okButton = dialogView.findViewById(R.id.okButton);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (radioButton1.isChecked()) {
                            markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "\n---");
                        } else if (radioButton2.isChecked()) {
                            markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "\n\n---");
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startPosition = markdownEditor.getSelectionStart();
                markdownEditor.getText().insert(startPosition, "![Alt_text](image_url)");
            }
        });

        linkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogForLink();
            }
        });

        wordCountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogForWordCount();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_toolbar_menu, menu);

        return true;
    }

    private void highlightSearchText(String query) {
        String text = markdownEditor.getText().toString();
        int startIndex = text.indexOf(query);

        if (startIndex >= 0) {
            markdownEditor.requestFocus();
            markdownEditor.setSelection(startIndex, startIndex + query.length());
        } else {
            // Clear selection if query not found
            markdownEditor.setSelection(0, 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String markdownContent = markdownEditor.getText().toString().trim();
        if (item.getItemId() == R.id.menu_copy) {
            String textToCopy = markdownEditor.getText().toString(); // Get text from EditText
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.menu_clear) {
            markdownEditor.setText("");
            return true;
        } else if (item.getItemId() == R.id.menu_save) {
            if (!markdownContent.isEmpty()) {
                // Open file chooser
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/markdown");
                intent.putExtra(Intent.EXTRA_TITLE, "my_markdown_file.md");
                startActivityForResult(intent, CREATE_FILE_REQUEST_CODE);
            } else {
                Toast.makeText(MarkdownEditorActivity.this, "Please write some content before saving.", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.menu_preview) {
            if (!markdownContent.isEmpty()) {
                // Start MarkdownViewer activity with the written content
                Intent intent = new Intent(MarkdownEditorActivity.this, MarkdownPreviewActivity.class);
                intent.putExtra("markdownContent", markdownContent);
                startActivity(intent);
            } else {
                Toast.makeText(MarkdownEditorActivity.this, "Please write some content.", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.menu_open) {
            markdownEditor.setText("");

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/markdown");
            startActivityForResult(intent, OPEN_FILE_REQUEST_CODE); // Use a unique request code
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData(); // Get the URI of the selected file
                if (uri != null) {
                    loadMarkdownFile(uri); // Load the content of the Markdown file
                }
            }
        } else if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData(); // Get the URI of the newly created file
                if (uri != null) {
                    saveMarkdownFile(uri); // Save the content of the Markdown file
                }
            }
        }
    }

    private void saveMarkdownFile(Uri uri) {
        String markdownContent = markdownEditor.getText().toString().trim();
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            if (outputStream != null) {
                outputStream.write(markdownContent.getBytes());
                outputStream.flush();
                Toast.makeText(this, "File saved successfully!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadMarkdownFile(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            // Assuming you have an EditText named markdownEditor to display content
            markdownEditor.setText(stringBuilder.toString());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Simulate a click on the second menu item
                    onOptionsItemSelected(toolbar.getMenu().findItem(R.id.menu_preview));
                }
            }, 500);

        } catch (IOException e) {
            Toast.makeText(this, "Error opening file", Toast.LENGTH_SHORT).show();
        }
    }

    public void showBottomSheetDialogForLink() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.link_setting_dialog, (ViewGroup) null));
        final EditText insertedLink = (EditText) bottomSheetDialog.findViewById(R.id.insertLink);
        final EditText insertedAltText = (EditText) bottomSheetDialog.findViewById(R.id.insertAltText);
        Button abortButton = (Button) bottomSheetDialog.findViewById(R.id.abortButton);
        Button confirmButton = (Button) bottomSheetDialog.findViewById(R.id.confirmButton);

        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (insertedLink.getText().toString().isEmpty()) {
                    insertedLink.setText("link?");
                    return;
                }
                if (insertedAltText.getText().toString().isEmpty()) {
                    insertedAltText.setText("Alt Text");
                }
                markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "[" + ((Object) insertedAltText.getText()) + "](" + ((Object) insertedLink.getText()) + ")");
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    public void showBottomSheetDialogForTable() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.table_setting_layout, (ViewGroup) null));
        final EditText insertedColumns = (EditText) bottomSheetDialog.findViewById(R.id.insertColumns);
        final EditText insertedRows = (EditText) bottomSheetDialog.findViewById(R.id.insertRows);
        Button abortButton = (Button) bottomSheetDialog.findViewById(R.id.abortButton);
        Button confirmButton = (Button) bottomSheetDialog.findViewById(R.id.confirmButton);
        final CheckBox leftCheckBox = (CheckBox) bottomSheetDialog.findViewById(R.id.LeftBox);
        final CheckBox centerCheckBox = (CheckBox) bottomSheetDialog.findViewById(R.id.CenterBox);
        final CheckBox rightCheckBox = (CheckBox) bottomSheetDialog.findViewById(R.id.RightBox);
        leftCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (leftCheckBox.isChecked()) {
                    rightCheckBox.setChecked(false);
                    centerCheckBox.setChecked(false);
                }
            }
        });
        rightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (rightCheckBox.isChecked()) {
                    leftCheckBox.setChecked(false);
                    centerCheckBox.setChecked(false);
                }
            }
        });
        centerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (centerCheckBox.isChecked()) {
                    rightCheckBox.setChecked(false);
                    leftCheckBox.setChecked(false);
                }
            }
        });
        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i;
                if (insertedColumns.getText().toString().isEmpty() || Integer.parseInt(insertedColumns.getText().toString()) <= 0) {
                    insertedColumns.setText("1");
                }
                if (insertedRows.getText().toString().isEmpty() || Integer.parseInt(insertedRows.getText().toString()) <= 0) {
                    insertedRows.setText("1");
                }
                if (leftCheckBox.isChecked()) {
                    i = 0;
                } else {
                    i = rightCheckBox.isChecked() ? 2 : 1;
                }
                markdownEditor.getText().insert(markdownEditor.getSelectionStart(), MarkdownEditorActivity.this.AssembleTable(Integer.parseInt(insertedColumns.getText().toString()), Integer.parseInt(insertedRows.getText().toString()), i));
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    public String AssembleTable(int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder("|");
        for (int i4 = 1; i4 <= i; i4++) {
            sb.append(" Header ").append(i4).append(" |");
        }
        sb.append("\n|");
        String str = i3 == 0 ? " :------" : i3 == 1 ? " :------:" : " ------:";
        for (int i5 = 1; i5 <= i; i5++) {
            sb.append(str.concat(" |"));
        }
        sb.append("\n");
        for (int i6 = 1; i6 <= i2; i6++) {
            sb.append("|");
            for (int i7 = 1; i7 <= i; i7++) {
                sb.append(" Content ").append(i6).append(" |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void showBottomSheetDialogForWordCount() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.word_count_layout, (ViewGroup) null));
        ((TextView) bottomSheetDialog.findViewById(R.id.actualCount)).setText(countLines() + " / " + countWords() + " / " + countCharacters());
        bottomSheetDialog.show();
    }

    private int countLines() {
        if (markdownEditor.getText().toString().isEmpty()) {
            return 0;
        }
        return markdownEditor.getText().toString().split("\\r?\\n").length;
    }

    private int countCharacters() {
        return markdownEditor.getText().toString().length();
    }

    private int countWords() {
        if (markdownEditor.getText().toString().trim().isEmpty()) {
            return 0;
        }
        return markdownEditor.getText().toString().trim().split("\\s+").length;
    }
}

