# Easy Frontend

Easy Frontend is a purpose-built tool designed specifically for
straightforward web projects. While it may not boast the complexity and
innovation seen in advanced frameworks like React, it excels at simplifying
the development process for simple HTML websites.

Easy Frontend simplifies web development with tags that make it easy to add
dynamic elements to your HTML code. These tags, such as JS tags and global
variables, streamline text generation and data management for simple projects.

### Source

    
    
            <div>
                <p>@{js-inline:2+2}@</p>
                <p>@{js-file:my-utils:exampleFunction()}@</p>
                <p>@{template:example.html}@</p>
                <p>@{global:globalVariable}@</p>
            </div>
        

### Build

    
    
            <div>
                <p>4</p>
                <p>exampleFunction() result</p>
                <p>example text from **example.html** template</p>
                <p>global variable value</p>
            </div>
        

Easy Frontend offers a built-in server with auto-refreshing feature for
quickly previewing your website during development

# Project structure

  * **build** \- Files with recompiled tags.
  * **templates** \- Template files used by _template_ tag.
  * **utils** \- Contains all scripts that can be used by _js-file_ tag.
  * **utils/global.js** \- Contains global object that is used to store global variables used in _global_ tag. 

# Commands

## Start - [start]

Starts development server

    
    
                --port=<PORT - DEFAULT VALUE: 8080> - Port on which server will be started
                --dont-watch-files - Disables watching for file changes
                --help - Display help
            

## Initialize project structure - [init]

Creates example project structure without running development server

    
    
                --help - Display help
            

## Build project - [build]

Recompiles sources without running development server

    
    
                --help - Display help
            

## Version - [version, --version, -v]

Prints version

    
    
                --help - Display help
            

## Help - [help, --help, -h]

Displays help screen

    
    
                --help - Display help
            

# Tags

Tags are used to dynamically generate text.

    
    
            Tag structure
            @{tag-name:tag-parameters}@
    
            Example
            @{template:template-file.js}@
    
            Tags can also be nested
            @{js-inline:exampleFunction(@{global:randomNumberVariable}@)}@
        

Tags can also be used in file names

    
    
            Before
            script-@{global:buildVersion}@.js
    
            After
            script-99010f60-e810-a572-3cf7-5618c9f7e47c.js
        

## Templates

Used to copy content from files placed in the **templates** directory. Tags
can also be used in the template file.

### Structure

    
    
            @{template: _template/file/path.html_ }@
        

### Source

    
    
            @{template:template.html}@
            @{template:templateWithSubtemplate.html}@
        

### Build

    
    
            
    
    ### Example template
    
    
            
    
    #### Template with **subtemplate**
    
    
        

## JS

Used to generate text using inline scripts or scripts from javascript files
from **utils** directory.

The script is evaluated every time when it's used.

### Structure

    
    
            @{js-inline: _inline script_ }@
            @{js-file: _script file name_ : _script_ }@
        

### Source

    
    
            @{js-inline:2+2}@
            @{js-file:example:exampleFunction()}@
            @{js-file:example:exampleVariable}@
        

### Build

    
    
            4
            example javascript value from function
            example javascript value from variable
        

## Global Variables

Used to store constant variables in the _global_ object in the
**utils/global.js** file.

The variables are evaluated only once, before every recompilation, so the
value will always be the same.

### Structure

    
    
            @{global: _variable name_ }@
        

### Source

    
    
            @{global:buildVersion}@
            @{global:buildDate}@
        

### Build

    
    
            99010f60-e810-a572-3cf7-5618c9f7e47c
            2023-09-03T15:39:42.219Z
        

