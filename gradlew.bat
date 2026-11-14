@echo off
setlocal
set "APP_HOME=%~dp0"
where python >nul 2>nul
if errorlevel 1 (
  echo Python 3 is required to launch the Gradle wrapper. 1>&2
  exit /b 1
)
python "%APP_HOME%scripts\gradle_launcher.py" %*
exit /b %ERRORLEVEL%
