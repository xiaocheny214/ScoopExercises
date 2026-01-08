@echo off
chcp 65001 >nul
title ScoopExercises 数据库修复工具

echo ========================================
echo     ScoopExercises 数据库修复工具
echo ========================================
echo.

echo [1/3] 正在停止Java进程...
tasklist /fi "imagename eq java.exe" | find "java.exe" >nul
if %errorlevel% equ 0 (
    taskkill /f /im java.exe >nul 2>&1
    echo ✓ Java进程已停止
    timeout /t 2 /nobreak >nul
) else (
    echo ⚠ 未发现运行的Java进程
)

echo.
echo [2/3] 正在检查数据库文件...
if exist "data\scoop_exercises_db.mv.db" (
    echo ✓ 发现数据库文件，正在删除...
    del "data\scoop_exercises_db.mv.db" 2>nul
    del "data\scoop_exercises_db.trace.db" 2>nul
    echo ✓ 数据库文件已删除
) else (
    echo ⚠ 未发现数据库文件
)

echo.
echo [3/3] 修复完成！
echo.
echo 操作说明：
echo 1. 关闭此窗口
echo 2. 重新启动 ScoopExercises 应用
echo 3. 系统将自动创建新的数据库
echo.
pause