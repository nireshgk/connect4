@echo off

rem change working directory to project root
cd /d %~dp0

rem set classs path
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;build\classes\main;

:: run ConnectFour class
java game.ConnectFour