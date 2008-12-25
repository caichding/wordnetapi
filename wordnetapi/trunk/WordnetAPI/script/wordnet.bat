@rem Simple windows command-line executable to lookup in the dictionary
@echo off

@if "%OS%" == "Windows_NT" setlocal

if "%OS%" == "Windows_NT" (
  set "WORDNETAPI_HOME=%~dp0%"
) else (
  set WORDNETAPI_HOME=.\
)

@rem JAVA_OPTS="%JAVA_OPTS% -Djava.util.logging.config.file=%WORDNETAPI_HOME%/logging.properties"
set WORDNETAPI_CLASSPATH=%WORDNETAPI_HOME%\lib\wordnetapi.jar
set WORDNETAPI_CLASSPATH=%WORDNETAPI_CLASSPATH%;%WORDNETAPI_HOME%\ext\h2.jar
set WORDNETAPI_CLASSPATH=%WORDNETAPI_CLASSPATH%;%WORDNETAPI_HOME%\ext\toplink-essentials.jar
set WORDNETAPI_CLASSPATH=%WORDNETAPI_CLASSPATH%;%WORDNETAPI_HOME%\database

set MAIN=eu.kostia.main.WordnetCLI

java %JAVA_OPTS% -classpath "%WORDNETAPI_CLASSPATH%" %MAIN% %*