package com.example.easymarkdown;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.imcloudfloating.markdown.MarkdownIt;


public class HelpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MarkdownIt markdownHelpMarkdownIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        markdownHelpMarkdownIt = findViewById(R.id.markdown_help_markdownIt);

        toolbar = findViewById(R.id.help_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        markdownHelpMarkdownIt.setMarkdownString(getMarkdownString());

    }

    private String getMarkdownOldString() {

        String markdownContent = "Markdown is a markup language that allows you to quickly and easily write for the web! Many\n" +
                "# Heading 1 {#heading-1}\n" +
                "## Heading 2 {#heading-2}\n" +
                "### Heading 3 {#heading-3}\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Paragraphs\n" +
                "\n" +
                "This is a simple paragraph in Markdown. Just start typing and it will automatically format as a paragraph.\n" +
                "\n" +
                "This is another paragraph after a blank line.\n" +
                "\n" +
                "## Line Breaks\n" +
                "\n" +
                "To create a line break,  \n" +
                "add two spaces at the end of a line.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Bold and Italic\n" +
                "\n" +
                "- **This text is bold** (`**bold**`)\n" +
                "- *This text is italic* (`*italic*`)\n" +
                "- ***This text is both bold and italic*** (`***bold and italic***`)\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Blockquotes\n" +
                "\n" +
                "> This is a blockquote.\n" +
                "> \n" +
                "> Blockquotes can be nested:\n" +
                "> > Nested blockquote.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Ordered Lists\n" +
                "\n" +
                "1. First item\n" +
                "2. Second item\n" +
                "   1. Sub-item 1\n" +
                "   2. Sub-item 2\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Unordered Lists\n" +
                "\n" +
                "- First item\n" +
                "- Second item\n" +
                "  - Sub-item 1\n" +
                "  - Sub-item 2\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Code\n" +
                "\n" +
                "Inline code: `print(\"Hello, World!\")`\n" +
                "\n" +
                "### Code Block:\n" +
                "\n" +
                "```\n" +
                "def hello_world():\n" +
                "    print(\"Hello, world!\")\n" +
                "```\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Horizontal Rules\n" +
                "\n" +
                "Three or more dashes, asterisks, or underscores:\n" +
                "\n" +
                "---\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Links\n" +
                "\n" +
                "[Example Link](https://www.example.com)\n" +
                "\n" +
                "Reference-style link: [Example][example-ref]\n" +
                "\n" +
                "[example-ref]: https://www.example.com\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Images\n" +
                "\n" +
                "Inline image: ![Alt text](https://markdown-here.com/img/icon256.png)\n" +
                "\n" +
                "Reference-style image: ![Alt text][image-ref]\n" +
                "\n" +
                "[image-ref]: https://markdown-here.com/img/icon256.png\n" +
                "\n" +
                "---\n" +
                "\n" +
                "## Tables\n" +
                "\n" +
                "| Header 1 | Header 2 |\n" +
                "| -------- | -------- |\n" +
                "| Row 1    | Row 1    |\n" +
                "| Row 2    | Row 2    |\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Footnotes\n" +
                "\n" +
                "This is a sentence with a footnote.[^1] You can add more information here.\n" +
                "\n" +
                "[^1]: This is the footnote text, which provides additional information.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Heading IDs\n" +
                "\n" +
                "### Custom Heading with ID {#custom-heading-id}\n" +
                "\n" +
                "You can link to this heading using its ID: `#custom-heading-id`.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Definition Lists\n" +
                "\n" +
                "Term 1  \n" +
                ": This is the definition for Term 1, providing details.\n" +
                "\n" +
                "Term 2  \n" +
                ": This is the definition for Term 2, with more information.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Strikethrough\n" +
                "\n" +
                "This text is ~~crossed out~~ using `~~text~~`.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Task Lists\n" +
                "\n" +
                "- [x] Completed Task 1\n" +
                "- [ ] Incomplete Task 2\n" +
                "- [ ] Incomplete Task 3\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Emoji (Copy and Paste)\n" +
                "\n" +
                "😄 🎉 🚀 ❤️\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Automatic URL Linking\n" +
                "\n" +
                "Visit this link: https://www.example.com. It will automatically convert to a clickable link.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Disabling Automatic URL Linking\n" +
                "\n" +
                "To display a URL without it being clickable, use backticks like this: `https://www.example.com`.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# HTML\n" +
                "\n" +
                "You can include HTML elements in Markdown:\n" +
                "\n" +
                "<strong>This text is bold using HTML.</strong>  \n" +
                "<i>This text is italic using HTML.</i>  \n" +
                "<a href=\"https://www.example.com\">This is a link using HTML</a>\n";

        return markdownContent;
    }

    private String getMarkdownString() {

        String markdownContent = "# **Headings**\n" +
                "\n" +
                "To create headings/titles, use `#` symbols. The number of `#` symbols indicates the heading level from 1 to 6. \n" +
                "\n" +
                "`# Biggest`\n" +
                "# **Biggest**\n" +
                "\n" +
                "`## Bigger`\n" +
                "## **Bigger**\n" +
                "\n" +
                "`### Big`\n" +
                "### **Big**\n" +
                "\n" +
                "`#### Small`\n" +
                "#### **Small**\n" +
                "\n" +
                "`##### Smaller`\n" +
                "##### **Smaller**\n" +
                "\n" +
                "`###### Smallest`\n" +
                "###### **Smallest**" +
                "\n\n" +
                "---\n" +
                "# Paragraphs\n" +
                "\n" +
                "This is a simple paragraph in Markdown. Just start typing and it will automatically format as a paragraph.\n" +
                "\n" +
                "This is another paragraph after a blank line.\n" +
                "\n" +
                "---\n" +
                "# Line Breaks\n" +
                "\n" +
                "To create a line break,  \n" +
                "add two spaces at the end of a line.\n" +
                "\n" +
                "---\n" +
                "# Styling text\n" +
                "\n" +
                "- **This text is bold** (`**bold**`)\n" +
                "- *This text is italic* (`*italic*`)\n" +
                "- ~~This text is strikethrough~~ (`~~strikethrough~~`)\n" +
                "- <ins>This text is inserted</ins> (`<ins>inserted</ins>`)\n" +
                "\n" +
                "---\n" +
                "# Blockquotes\n" +
                "\n" +
                "Blockquotes can be written by using `>` symbol\n" +
                "\n" +
                "> This is a blockquote.\n" +
                "> \n" +
                "> Blockquotes can be nested:\n" +
                "> > Nested blockquote.\n" +
                "\n" +
                "---\n" +
                "# Ordered Lists\n" +
                "\n" +
                "1. First item\n" +
                "2. Second item\n" +
                "   1. Sub-item 1\n" +
                "   2. Sub-item 2\n" +
                "\n" +
                "---\n" +
                "# Unordered Lists\n" +
                "use dash(-) symbol to create an unordered list.\n" +
                "\n" +
                "- First item\n" +
                "- Second item\n" +
                "  - Sub-item 1\n" +
                "  - Sub-item 2\n" +
                "\n" +
                "---\n" +
                "# Code\n" +
                "Code can be written by using backticks (`) symbol.\n" +
                "\n" +
                "Inline code: `print(\"Hello, World!\")`\n" +
                "\n" +
                "---\n" +
                "# Code Block\n" +
                "Code block can be written by using triple backticks (```).\n" +
                "\n" +
                "```python\n" +
                "def hello_world():\n" +
                "    print(\"Hello, world!\")\n" +
                "```\n" +
                "\n" +
                "---\n" +
                "# Horizontal Rules\n" +
                "Three or more dashes(-), asterisks(*), or underscores(_).\n" +
                "\n" +
                "---\n" +
                "---\n" +
                "# Links\n" +
                "\n" +
                "`[Example](https://www.example.com)`  \n" +
                "[Example](https://www.example.com)\n" +
                "\n" +
                "\n" +
                "`Reference-style link: [Example][example-ref]`\n" +
                "`[example-ref]: https://www.example.com`  \n" +
                "\n" +
                "Reference-style link: [Example][example-ref]\n" +
                "\n" +
                "[example-ref]: https://www.example.com\n" +
                "\n" +
                "---\n" +
                "# Images\n" +
                "`Inline image: ![Alt text](https://markdown-here.com/img/icon256.png)`\n" +
                "\n" +
                "Inline image: ![Alt text](https://markdown-here.com/img/icon256.png)\n" +
                "\n" +
                "`Reference-style image: ![Alt text][image-ref]`  \n" +
                "`[image-ref]: https://markdown-here.com/img/icon256.png`\n" +
                "\n" +
                "Reference-style image: ![Alt text][image-ref]\n" +
                "\n" +
                "[image-ref]: https://markdown-here.com/img/icon256.png\n" +
                "\n" +
                "---\n" +
                "# Tables\n" +
                "```\n" +
                "Use dashes(`-`), pipes(`|`) and semicolor(`:`) to create tables\n" +
                "```\n" +
                "\n" +
                "```\n" +
                "| Left Header | Middle Header | Right Header |\n" +
                "| ----------  | ------------  | ------------ |\n" +
                "|  Top-Left   |  Top-Center   |   Top-Right  |\n" +
                "| Middle-Left | Middle-Center | Middle-Right |\n" +
                "| Bottom-Left | Bottom-Center | Bottom-Right |\n" +
                "```\n" +
                "\n" +
                "| Left Header | Middle Header | Right Header |\n" +
                "| ----------  | ------------  | ------------ |\n" +
                "|  Top-Left   |  Top-Center   |   Top-Right  |\n" +
                "| Middle-Left | Middle-Center | Middle-Right |\n" +
                "| Bottom-Left | Bottom-Center | Bottom-Right |\n" +
                "\n" +
                "```\n" +
                "Right aligned columns\n" +
                "\n" +
                "| Option | Description |\n" +
                "| ------:| -----------:|\n" +
                "| data   | path to data files to supply the data that will be passed into templates. |\n" +
                "| engine | engine to be used for processing templates. Handlebars is the default. |\n" +
                "| ext    | extension to be used for dest files. |\n" +
                "```\n" +
                "\n" +
                "| Option | Description |\n" +
                "| ------:| -----------:|\n" +
                "| data   | path to data files to supply the data that will be passed into templates. |\n" +
                "| engine | engine to be used for processing templates. Handlebars is the default. |\n" +
                "| ext    | extension to be used for dest files. |\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Emoji (Copy and Paste)\n" +
                "\n" +
                "\uD83D\uDE04 \uD83C\uDF89 \uD83D\uDE80 ❤\uFE0F\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# Emoji (Shortcodes)\n" +
                "\n" +
                "Here are some shortcodes for emojis:\n" +
                "\n" +
                "- `:smile:` → \uD83D\uDE04\n" +
                "- `:tada:` → \uD83C\uDF89\n" +
                "- `:rocket:` → \uD83D\uDE80\n" +
                "- `:heart:` → ❤\uFE0F\n" +
                "\n" +
                "---\n" +
                "\n" +
                "# HTML\n" +
                "\n" +
                "You can include HTML elements in Markdown:\n" +
                "\n" +
                "`<strong>HTML</strong>` → <strong>HTML</strong> \n" +
                "\n" +
                "`<i>HTML</i>` → <i>HTML</i> \n" +
                "\n" +
                "<a href=\"https://www.example.com\">This is a link using HTML anchor tag</a> \n" +
                "\n" +
                "`<font color='red'>**RED**</font>` → <font color='red'>**RED**</font> \n" +
                "\n" +
                "`<font color='green'>**GREEN**</font>` → <font color='green'>**GREEN**</font> \n" +
                "\n" +
                "`<font color='blue'>**BLUE**</font>` → <font color='blue'>**BLUE**</font> \n" +
                "\n" +
                "\n" +
                "End";

        return markdownContent;
    }
}