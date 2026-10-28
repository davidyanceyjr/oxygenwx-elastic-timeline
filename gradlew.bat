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
python "%APP_HOME%scripts\gradle_launcher.py" %*
exit /b %ERRORLEVEL%
