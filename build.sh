#!/bin/sh

COMPILE_FILE_MIN="src/com/demo/Demo.java src/com/clisp/*.java src/com/jade/*.java src/com/gui/*.java"

if test -f "$classes/"; then
    mkdir classes/
fi

if [ $1 = "demo" ]; then
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar $COMPILE_FILE_MIN -d classes/
elif [ $1 = "five" ]; then
    COMPILE_FIVE_SRC="src/com/handson/five/*.java"
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar $COMPILE_FILE_MIN $COMPILE_FIVE_SRC -d classes/
else
    echo "Pass number of challenge to build"
fi
