@echo off
title Backend - Clinica Veterinaria
cd /d "%~dp0"
echo ================================================
echo   INICIANDO BACKEND - Clinica Veterinaria
echo ================================================
echo.

set "MYSQL_EXE=C:\xampp\mysql\bin\mysql.exe"
if exist "%MYSQL_EXE%" (
    echo Verificando MySQL de XAMPP...
    "%MYSQL_EXE%" -u root -e "CREATE DATABASE IF NOT EXISTS clinica_veterinaria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" >nul 2>&1
    if errorlevel 1 (
        echo.
        echo ERROR: No se pudo conectar a MySQL en XAMPP.
        echo Abre XAMPP Control Panel e inicia MySQL antes de arrancar el backend.
        echo.
        pause
        exit /b 1
    )
    echo MySQL OK y base clinica_veterinaria lista.
) else (
    echo AVISO: No se encontro "%MYSQL_EXE%".
    echo Si MySQL no esta iniciado, el backend no podra conectar.
)

echo.
echo Starting Backend...
call mvnw.cmd spring-boot:run
echo.
echo Backend server stopped.
pause
