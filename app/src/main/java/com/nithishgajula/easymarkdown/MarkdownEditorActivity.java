package com.nithishgajula.easymarkdown;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MarkdownEditorActivity extends AppCompatActivity {
    private static final int CREATE_FILE_REQUEST_CODE = 1;
    private static final int OPEN_FILE_REQUEST_CODE = 2;
    NavigationView navigationView;
    private ActivityResultLauncher<Intent> openFileLauncher;
    private ActivityResultLauncher<Intent> createFileLauncher;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private long pressedTime = 0;
    private EditText markdownEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setNavigationBarContrastEnforced(false);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_editor);

        openFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri uri = data.getData();
                            if (uri != null) {
                                loadMarkdownFile(uri);
                            }
                        }
                    }
                }
        );

        createFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri uri = data.getData();
                            if (uri != null) {
                                saveMarkdownFile(uri);
                            }
                        }
                    }
                }
        );


        toolbar = findViewById(R.id.editor_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        TextView drawerVersion = findViewById(R.id.drawer_version);
        ImageView tabIcon = findViewById(R.id.tabIcon);
        ImageView boldIcon = findViewById(R.id.boldIcon);
        ImageView italicIcon = findViewById(R.id.italicIcon);
        ImageView strikeIcon = findViewById(R.id.strikeIcon);
        ImageView headerIcon = findViewById(R.id.headerIcon);
        ImageView quoteIcon = findViewById(R.id.quoteIcon);
        ImageView listIcon = findViewById(R.id.listIcon);
        ImageView codeIcon = findViewById(R.id.codeIcon);
        ImageView codeBlockIcon = findViewById(R.id.codeBlockIcon);
        ImageView linkIcon = findViewById(R.id.linkIcon);
        ImageView imageIcon = findViewById(R.id.imgIcon);
        ImageView horizontalRowIcon = findViewById(R.id.horizontalRowIcon);
        ImageView tableIcon = findViewById(R.id.tableIcon);
        ImageView wordCountIcon = findViewById(R.id.wordIcon);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        markdownEditor = findViewById(R.id.markdown_editor);

        Intent intent = getIntent();
        String uriString = intent.getStringExtra("fileUri");
        if (uriString != null) {
            Uri uri = Uri.parse(uriString);
            loadMarkdownFile(uri);
        }

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            drawerVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MarkdownEditorActivity", "Failed to get package info", e);
            drawerVersion.setText(R.string.version_info_error);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_help) {
                Intent navIntent = new Intent(MarkdownEditorActivity.this, HelpActivity.class);
                startActivity(navIntent);
            } else if (item.getItemId() == R.id.nav_support) {
                Intent navIntent = new Intent(MarkdownEditorActivity.this, SupportActivity.class);
                startActivity(navIntent);
            } else if (item.getItemId() == R.id.nav_relaunch) {
                if (!markdownEditor.getText().toString().isEmpty()) {
                    openSaveDialog("Restart");
                } else {
                    finishAffinity();
                    startActivity(new Intent(MarkdownEditorActivity.this, MarkdownEditorActivity.class));
                }
            } else if (item.getItemId() == R.id.nav_about) {
                Intent navIntent = new Intent(MarkdownEditorActivity.this, AboutActivity.class);
                startActivity(navIntent);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (!markdownEditor.getText().toString().trim().isEmpty()) {
                    openSaveDialog("Exit");
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

        tabIcon.setOnClickListener(v -> {
            int startPosition = markdownEditor.getSelectionStart();
            markdownEditor.getText().insert(startPosition, "    ");
        });

        boldIcon.setOnClickListener(v -> {
            int selectionStart = markdownEditor.getSelectionStart();
            int selectionEnd = markdownEditor.getSelectionEnd();
            Editable text = markdownEditor.getText();
            text.insert(selectionStart, "**");
            text.insert(selectionEnd + 2, "**");
        });


        italicIcon.setOnClickListener(v -> {
            int selectionStart = markdownEditor.getSelectionStart();
            int selectionEnd = markdownEditor.getSelectionEnd();
            Editable text = markdownEditor.getText();
            text.insert(selectionStart, "*");
            text.insert(selectionEnd + 1, "*");
        });

        strikeIcon.setOnClickListener(v -> {
            int selectionStart = markdownEditor.getSelectionStart();
            int selectionEnd = markdownEditor.getSelectionEnd();
            Editable text = markdownEditor.getText();
            text.insert(selectionStart, "~~");
            text.insert(selectionEnd + 2, "~~");
        });

        headerIcon.setOnClickListener(v -> {
            int selectionStart = markdownEditor.getSelectionStart();
            int selectionEnd = markdownEditor.getSelectionEnd();
            Editable text = markdownEditor.getText();
            text.insert(selectionStart, "# ");
            text.insert(selectionEnd + 2, "\n");
        });

        quoteIcon.setOnClickListener(v -> markdownEditor.getText().insert(markdownEditor.getSelectionStart(), ">"));


        listIcon.setOnClickListener(v -> markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "* "));

        codeIcon.setOnClickListener(v -> {
            int selectionStart = markdownEditor.getSelectionStart();
            int selectionEnd = markdownEditor.getSelectionEnd();
            Editable text = markdownEditor.getText();
            text.insert(selectionStart, "`");
            text.insert(selectionEnd + 1, "`");
        });

        codeBlockIcon.setOnClickListener(v -> {
            int selectionStart = markdownEditor.getSelectionStart();
            markdownEditor.getText().insert(selectionStart, "\n``` python\n print(\"Hello Python\")\n```");
            int i4 = selectionStart + 10;
            markdownEditor.setSelection(i4, i4);
        });

        tableIcon.setOnClickListener(v -> showBottomSheetDialogForTable());

        horizontalRowIcon.setOnClickListener(v -> markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "\n---"));

        imageIcon.setOnClickListener(v -> {
            int startPosition = markdownEditor.getSelectionStart();
            markdownEditor.getText().insert(startPosition, "![Alt_text](image_url)");
        });

        linkIcon.setOnClickListener(v -> showBottomSheetDialogForLink());

        wordCountIcon.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View dialogView = inflater.inflate(R.layout.word_count_dialog, null);
            TextView linesCount = dialogView.findViewById(R.id.linesCount);
            TextView wordsCount = dialogView.findViewById(R.id.wordsCount);
            TextView charactersCount = dialogView.findViewById(R.id.charactersCount);
            Button okButton = dialogView.findViewById(R.id.okButton);

            linesCount.setText(getString(R.string.lines_count, countLines()));
            wordsCount.setText(getString(R.string.words_count, countWords()));
            charactersCount.setText(getString(R.string.characters_count, countCharacters()));

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);

            okButton.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });

        applyDisplayCutouts();
        applyAppBarCutouts();
        applyWindowInsets();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_toolbar_menu, menu);
        return true;
    }

    private void applyDisplayCutouts() {
        // Works in portrait mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editor_content), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime() | WindowInsetsCompat.Type.displayCutout());

            v.setPadding(bars.left, 0, bars.right, 0);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void applyAppBarCutouts() {
        // Works in Landscape mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editor_appbar), (v, insets) -> {
            Insets appbars = insets.getInsets(
                    WindowInsetsCompat.Type.statusBars());

            v.setPadding(0, appbars.top, 0, 0);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void applyWindowInsets() {
        // Navigation view content in landscape and portrait
        NavigationView navView = findViewById(R.id.nav_view);
        ViewCompat.setOnApplyWindowInsetsListener(navView, (v, windowInsets) -> {
            Insets sysInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    sysInsets.left,
                    v.getPaddingTop(),
                    v.getPaddingRight(),
                    sysInsets.bottom
            );
            return windowInsets; // Let children receive the insets too
        });
    }

    private void openSaveDialog(String action) {
        AlertDialog dialogA = new AlertDialog.Builder(MarkdownEditorActivity.this)
                .setTitle("Unsaved Changes")
                .setMessage("You have unsaved changes. Would you like to save your file before " + action + "?")
                .setPositiveButton("Save", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/markdown");
                    intent.putExtra(Intent.EXTRA_TITLE, "my_markdown_file.md");
                    createFileLauncher.launch(intent);
                })
                .setNegativeButton(action, (dialog, which) -> {
                    if (action.equals("Restart")) {
                        finishAffinity();
                        startActivity(new Intent(MarkdownEditorActivity.this, MarkdownEditorActivity.class));
                    } else if (action.equals("Open")) {
                        markdownEditor.setText("");

                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("text/markdown");
                        openFileLauncher.launch(intent);
                    } else {
                        finishAffinity(); // Closes the app
                    }
                })
                .setNeutralButton("Cancel", null)
                .show();

        int buttonColor = ContextCompat.getColor(this, R.color.blue_jeans);

        dialogA.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor);
        dialogA.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(buttonColor);
        dialogA.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(buttonColor);
    }

//    private void highlightSearchText(String query) {
//        String text = markdownEditor.getText().toString();
//        int startIndex = text.indexOf(query);
//
//        if (startIndex >= 0) {
//            markdownEditor.requestFocus();
//            markdownEditor.setSelection(startIndex, startIndex + query.length());
//        } else {
//            // Clear selection if query not found
//            markdownEditor.setSelection(0, 0);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String markdownContent = markdownEditor.getText().toString().trim();
        if (item.getItemId() == R.id.menu_copy) {
            String textToCopy = markdownEditor.getText().toString(); // Get text from EditText
            if (textToCopy.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Write something before copying", Toast.LENGTH_SHORT).show();
            } else {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
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
                createFileLauncher.launch(intent);
            } else {
                Toast.makeText(MarkdownEditorActivity.this, "Please write something before saving.", Toast.LENGTH_SHORT).show();
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

            if (!markdownEditor.getText().toString().trim().isEmpty()) {
                openSaveDialog("Open");
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/markdown");
                openFileLauncher.launch(intent);
                return true;
            }


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
            markdownEditor.setText(stringBuilder.toString());

            new Handler().postDelayed(() -> onOptionsItemSelected(toolbar.getMenu().findItem(R.id.menu_preview)), 500);

        } catch (IOException e) {
            Toast.makeText(this, "Error opening file", Toast.LENGTH_SHORT).show();
        }
    }

    public void showBottomSheetDialogForLink() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.link_setting_dialog, bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet), false);
        bottomSheetDialog.setContentView(dialogView);
        final EditText insertedLink = dialogView.findViewById(R.id.insertLink);
        final EditText insertedAltText = dialogView.findViewById(R.id.insertAltText);
        Button abortButton = dialogView.findViewById(R.id.abortButton);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        abortButton.setOnClickListener(view -> bottomSheetDialog.dismiss());

        confirmButton.setOnClickListener(view -> {
            if (insertedLink.getText().toString().trim().isEmpty()) {
                insertedLink.setError("Please insert the link");
            } else if (insertedAltText.getText().toString().trim().isEmpty()) {
                insertedAltText.setError("Please insert alternate text");
            } else {
                markdownEditor.getText().insert(markdownEditor.getSelectionStart(), "[" + (insertedAltText.getText()) + "](" + (insertedLink.getText()) + ")");
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void showBottomSheetDialogForTable() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.table_setting_dialog, bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet), false);
        bottomSheetDialog.setContentView(view);

        EditText insertedColumns = view.findViewById(R.id.insertColumns);
        EditText insertedRows = view.findViewById(R.id.insertRows);

        CheckBox cbLeft = view.findViewById(R.id.LeftBox);
        CheckBox cbCenter = view.findViewById(R.id.CenterBox);
        CheckBox cbRight = view.findViewById(R.id.RightBox);

        Button btnCancel = view.findViewById(R.id.abortButton);
        Button btnConfirm = view.findViewById(R.id.confirmButton);

        // Only one checkbox allowed (like radio buttons)
        View.OnClickListener checkBoxClickListener = v -> {
            cbLeft.setChecked(v.getId() == R.id.LeftBox);
            cbCenter.setChecked(v.getId() == R.id.CenterBox);
            cbRight.setChecked(v.getId() == R.id.RightBox);
        };

        cbLeft.setOnClickListener(checkBoxClickListener);
        cbCenter.setOnClickListener(checkBoxClickListener);
        cbRight.setOnClickListener(checkBoxClickListener);

        btnCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            int i;
            if (insertedColumns.getText().toString().isEmpty() || Integer.parseInt(insertedColumns.getText().toString()) <= 0) {
                insertedColumns.setText("1");
            }
            if (insertedRows.getText().toString().isEmpty() || Integer.parseInt(insertedRows.getText().toString()) <= 0) {
                insertedRows.setText("1");
            }
            if (cbLeft.isChecked()) {
                i = 0;
            } else {
                i = cbRight.isChecked() ? 2 : 1;
            }
            markdownEditor.getText().insert(markdownEditor.getSelectionStart(), MarkdownEditorActivity.this.AssembleTable(Integer.parseInt(insertedColumns.getText().toString()), Integer.parseInt(insertedRows.getText().toString()), i));
            bottomSheetDialog.dismiss();
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

