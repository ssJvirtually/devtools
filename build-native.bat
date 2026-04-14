@echo off
REM Native Image Build Script for DevUtils (Windows)
REM This script helps build native images with proper configuration

setlocal enabledelayedexpansion

echo Building Native Image for DevUtils...

REM Get the JAR file
for %%f in (target\devtools-*.jar) do (
    set "JAR_FILE=%%f"
    goto :found
)

:found
if "%JAR_FILE%"=="" (
    echo No JAR file found. Run 'mvn clean package' first.
    exit /b 1
)

echo Found JAR: %JAR_FILE%

REM Build native image
native-image ^
    --no-fallback ^
    --initialize-at-build-time ^
    -H:+ReportExceptionStackTraces ^
    -H:ReflectionConfigurationFiles=src/main/resources/META-INF/native-image/reflect-config.json ^
    -H:ResourceConfigurationFiles=src/main/resources/META-INF/native-image/resource-config.json ^
    --add-opens=java.base/java.util=ALL-UNNAMED ^
    --add-opens=java.base/java.lang=ALL-UNNAMED ^
    -Djavafx.platform=win ^
    -jar "%JAR_FILE%" ^
    -o target/DevUtils-Windows.exe

echo Native image built successfully: target/DevUtils-Windows.exe
echo You can now run it with: target\DevUtils-Windows.exe

endlocal
