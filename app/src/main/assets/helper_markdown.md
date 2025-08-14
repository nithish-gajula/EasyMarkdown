# Headings
 
To create headings/titles, use `#` symbols. The number of `#` symbols indicates the heading level from 1 to 6.  
 
# Heading 1
 
## Heading 2 
 
### Heading 3
 
#### Heading 4
 
##### Heading 5
 
###### Heading 6
 
---
# Paragraphs 
 
This is a simple paragraph in Markdown. Just start typing and it will automatically format as a paragraph. 
 
This is another paragraph after a blank line. 
 
--- 
# Line Breaks 
 
To create a line break,   
add two spaces at the end of a line. 
 
--- 
# Styling text 

- **This text is bold using `**`** (`**bold**`)
- __This text is bold using `__`__ (`__bold__`)
- *This text is italic using `*`* (`*italic*`)
- _This text is italic using `_`_ (`_italic_`)
- ~~This text is strikethrough~~ (`~~strikethrough~~`) 
- <ins>This text is inserted</ins> (`<ins>inserted</ins>`) 
 
--- 
# Blockquotes 
 
Blockquotes can be written by using `>` symbol
 
> Blockquotes can also be nested...
>  
>> ...by using additional greater-than signs right next to each other...
> > > ...or with spaces between arrows.
 
--- 
# Ordered Lists 
Use **only numeric values** followed by dot(.) and a space to create the ordered list.   

1. first list item
2. second list item
3. third list item

1. You can use sequential numbers...
1. ...or keep all the numbers as `1.`
   1. Sub list can be created by...
   2. ... indenting 3 spaces
      1. Nesting is indicated...
      2. ..by numerics `1`, romans `i`...
   3. ..and alphabets `a`

You can also start numbering with offset:

25. foo
1. bar
 
--- 
# Unordered Lists 

+ Create a unordered list by starting a line with `+`, `-`, or `*`
+ Sub-lists are made by indenting 2 spaces:
  - Hyphen character change forces new list start:
    * unordered nested list item with `*`
    + unordered nested list item with `+`
    - unordered nested list item with `-`
+ Very easy!
 
--- 
# Code 
Code can be written by using backticks (`) symbol. 
                 
**Inline code:**  `print(\Hello, World!\)` 

**Indented code**

    // Some comments
    line 1 of code
    line 2 of code
    line 3 of code

**Simple Code Block using (```)**

```
Sample text here...
```

--- 

# Code Block
Code block : Syntax highlighting can be written by using triple backticks (```) followed by language

**js**

``` js
var foo = function (bar) {
  return bar++;
};

console.log(foo(5));
```
**python**

``` python 
def hello_world(): 
    print(\Hello, world!\) 
``` 
 
--- 

# Math Formulas  
Use single $ symbol to use inline math, $$ for math block   

**Inline math:**  $E = mc^2$  
                 
**Math Block:** 
$$ 
\int_{0}^{\infty} x^2 dx 
$$ 
 
--- 

# Horizontal Rules 
Use three or more dashes(-), asterisks(*), or underscores(_) to make horizontal rule
___
----
*****

--- 
# Links 

**Inline Link :**

[Nithish Gajula](https://nithish-gajula.github.io) 



**Reference-style Link :**

[Developer Website][website-id]

With a reference later in the document defining the URL location:

[website-id]: https://nithish-gajula.github.io "Nithish Gajula"

--- 
# Images 
 
**Inline image:** 

![Alt text](https://markdown-here.com/img/icon256.png)
 


**Reference-style image:**

![Alt text][image-id]

With a reference later in the document defining the URL location:

[image-id]: https://octodex.github.com/images/dojocat.jpg "The Dojocat" 
 
--- 
# Tables 
``` 
Use dashes(`-`), pipes(`|`) and semicolon(`:`) to create tables 
```
**Simple Table :** 

| Left Header | Middle Header | Right Header | 
| ----------  | ------------  | ------------ | 
|  Top-Left   |  Top-Center   |   Top-Right  | 
| Middle-Left | Middle-Center | Middle-Right | 
| Bottom-Left | Bottom-Center | Bottom-Right | 


**Right aligned columns :**

| Option | Description | 
| ------:| -----------:| 
| data   | path to data files to supply the data that will be passed into templates. | 
| engine | engine to be used for processing templates. Handlebars is the default. | 
| ext    | extension to be used for dest files. | 
 
---
 
# HTML 
 
You can include HTML elements in Markdown: 
 
`<strong>HTML</strong>` → <strong>HTML</strong>  
 
`<i>HTML</i>` → <i>HTML</i>  
 
<a href=\https://www.example.com\>This is a link using HTML anchor tag</a>  
 
`<font color='red'>**RED**</font>` → <font color='red'>**RED**</font>  
 
`<font color='green'>**GREEN**</font>` → <font color='green'>**GREEN**</font>  
 
`<font color='blue'>**BLUE**</font>` → <font color='blue'>**BLUE**</font>  
 
 
--- 
 
# Custom Containers 
 
To create the containers use `info`, `success`, `warning`, `error` keywords and `:::` symbol for opening and closing as below example 

::: info 
This is a **info** box 
::: 

::: success
This is a success box
:::

::: warning
This is a *warning* box
:::

::: error
This is a <ins>error</ins> box
:::


End of file.





