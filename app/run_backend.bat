@echo off
title Backend - Clinica Veterinaria
cd /d "%~dp0"
echo Starting Backend...
call mvnw.cmd spring-boot:run
echo.
echo Backend server stopped.
pause