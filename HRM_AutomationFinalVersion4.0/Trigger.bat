"javac" -cp ".;.\JarFiles\Jars\*" src\test\driver\*.java src\test\resources\generic\*.java src\test\testcases\*.java

cd /d "src"

java -cp ".;.\..\JarFiles\Jars\*" test.driver.TestRunner

pause