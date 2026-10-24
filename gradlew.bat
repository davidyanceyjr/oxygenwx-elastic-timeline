@echo off
setlocal
set "APP_HOME=%~dp0"
where python >nul 2>nul
if errorlevel 1 (
  echo Python 3 is required to bootstrap the pinned Gradle wrapper JAR. 1>&2
  exit /b 1
)
python "%APP_HOME%scripts\bootstrap_wrapper.py"
if errorlevel 1 exit /b 1
java -Dorg.gradle.appname=gradlew -classpath "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
exit /b %ERRORLEVEL%
