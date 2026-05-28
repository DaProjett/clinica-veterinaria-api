@echo off
setlocal
title Frontend - Clinica Veterinaria
color 0A
cd /d "%~dp0"
cls

echo.
echo ==================================================
echo   INICIANDO FRONTEND - Clinica Veterinaria React
echo ==================================================
echo.

echo Verificando requisitos...
echo.

set "NODE_EXE="
for %%P in (
    "%~dp0.tools\node\node.exe"
    "C:\Program Files\nodejs\node.exe"
    "%LOCALAPPDATA%\Programs\nodejs\node.exe"
) do (
    if not defined NODE_EXE if exist %%~P set "NODE_EXE=%%~P"
)

if not defined NODE_EXE (
    for /f "delims=" %%P in ('where node 2^>nul') do (
        if not defined NODE_EXE set "NODE_EXE=%%P"
    )
)

if not defined NODE_EXE (
    echo ERROR: Node.js no encontrado.
    echo.
    echo Instala Node.js LTS desde https://nodejs.org/
    echo Luego cierra y abre otra vez esta terminal.
    echo.
    pause
    exit /b 1
)

echo Node.js encontrado:
"%NODE_EXE%" --version
echo.
for %%D in ("%NODE_EXE%") do set "PATH=%%~dpD;%PATH%"

if not exist "package.json" (
    echo ERROR: package.json no encontrado.
    echo Ejecuta este archivo desde la carpeta del frontend.
    echo Directorio actual: %CD%
    echo.
    pause
    exit /b 1
)

set "NPM_CMD="
for %%P in (
    "%~dp0.tools\node\npm.cmd"
    "C:\Program Files\nodejs\npm.cmd"
    "%LOCALAPPDATA%\Programs\nodejs\npm.cmd"
) do (
    if not defined NPM_CMD if exist %%~P set "NPM_CMD=%%~P"
)

if not defined NPM_CMD (
    for /f "delims=" %%P in ('where npm 2^>nul') do (
        if not defined NPM_CMD set "NPM_CMD=%%P"
    )
)

if not exist "node_modules\react-scripts\bin\react-scripts.js" (
    if not defined NPM_CMD (
        echo ERROR: npm no esta disponible y faltan dependencias.
        echo.
        echo Instala Node.js LTS completo desde https://nodejs.org/
        echo Despues ejecuta de nuevo este archivo.
        echo.
        pause
        exit /b 1
    )

    echo Instalando dependencias...
    call "%NPM_CMD%" install
    if errorlevel 1 (
        echo.
        echo ERROR: Fallo npm install.
        echo Revisa internet, permisos de carpeta o la instalacion de Node.js.
        echo.
        pause
        exit /b 1
    )
) else (
    echo Dependencias encontradas en node_modules.
)

echo.
echo ==================================================
echo   Frontend disponible en: http://localhost:3000
echo   Presiona Ctrl+C para detener el servidor
echo ==================================================
echo.

start http://localhost:3000

if exist "node_modules\react-scripts\bin\react-scripts.js" (
    "%NODE_EXE%" "node_modules\react-scripts\bin\react-scripts.js" start
) else (
    call "%NPM_CMD%" start
)

echo.
echo Frontend detenido.
pause
