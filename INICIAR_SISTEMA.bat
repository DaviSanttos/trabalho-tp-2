@echo off
echo ==========================================
echo   INICIANDO SISTEMA COXINHA (FULL STACK)
echo ==========================================

echo [1/2] Iniciando Back-End (Spring Boot)...
start cmd /k "cd backend && echo Tentando rodar o Back-End... && mvn spring-boot:run || echo ERRO: Verifique se o Java 17 e o Maven estao instalados."

echo [2/2] Iniciando Front-End (React + Vite)...
start cmd /k "cd frontend && npm install && npm run dev"

echo ==========================================
echo Servidores em inicializacao!
echo Back-End: http://localhost:8080
echo Front-End: http://localhost:3000
echo ==========================================
pause
