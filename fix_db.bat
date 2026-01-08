@echo off
echo 正在修复数据库锁定问题...
echo.

echo 停止Java进程...
taskkill /f /im java.exe >nul 2>&1
timeout /t 2 /nobreak >nul

echo 删除数据库文件...
del "data\scoop_exercises_db.mv.db" 2>nul
del "data\scoop_exercises_db.trace.db" 2>nul

echo.
echo 修复完成！现在可以重新启动应用。
pause