export PATH_INCLUDE=-Djava.library.path=lib/
export INCLUDE_CP="-cp lib/CLIPSJNI.jar:lib/jade.jar:classes/:lib/sqlite-jdbc-3.34.0.jar"

if [[ $1 = "demo" ]]; then
    java $PATH_INCLUDE $INCLUDE_CP com.demo.Demo
elif [ $1 = "four" ]; then
    java $PATH_INCLUDE $INCLUDE_CP jade.Boot -gui misael:com.handson.four.EvalDynamicClispInput
elif [ $1 = "five" ]; then
    java $PATH_INCLUDE $INCLUDE_CP jade.Boot -gui misael:com.handson.five.EvalClispFilesAgent
elif [ $1 = "project" ]; then
    java $PATH_INCLUDE $INCLUDE_CP com.project.db.DBConnection
else
    echo "Pass at least 1 argument, number of challenge"
fi
