if test -f "$classes/"; then
    mkdir classes/
fi
if [[ $1 = "demo" ]]
then
    javac -cp lib/CLIPSJNI.jar:lib/jade.jar src/com/demo/Demo.java -d classes/
else
    echo "Pass number of challenge to build"
fi
