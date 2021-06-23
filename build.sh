#!/bin/sh

COMPILE_FILE_MIN="src/com/demo/Demo.java src/com/clisp/*.java src/com/jade/*.java src/com/gui/*.java"

if test -f "$classes/"; then
    mkdir classes/
fi

if [ $1 = "demo" ]; then
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar $COMPILE_FILE_MIN -d classes/
elif [ $1 = "four" ]; then
    COMPILE_FOUR_SRC="src/com/handson/four/*.java"
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar $COMPILE_FILE_MIN $COMPILE_FOUR_SRC -d classes/
elif [ $1 = "five" ]; then
    COMPILE_FIVE_SRC="src/com/handson/five/*.java"
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar $COMPILE_FILE_MIN $COMPILE_FIVE_SRC -d classes/
elif [ $1 = "project" ]; then
    COMPILE_PROD_SRC="src/com/project/db/*.java"
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar:lib/sqlite-jdbc-3.34.0.jar $COMPILE_FILE_MIN $COMPILE_PROD_SRC -d classes/
else
    echo "Pass number of challenge to build"
fi
