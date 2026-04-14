#!/bin/bash

# Native Image Build Script for DevUtils
# This script helps build native images with proper configuration

set -e

echo "🔧 Building Native Image for DevUtils..."

# Get the JAR file
JAR_FILE=$(find target -name "devtools-*.jar" ! -name "*sources.jar" ! -name "*javadoc.jar" | head -n 1)

if [ -z "$JAR_FILE" ]; then
    echo "❌ No JAR file found. Run 'mvn clean package' first."
    exit 1
fi

echo "📦 Found JAR: $JAR_FILE"

# Platform detection
OS_NAME=$(uname -s)
case "$OS_NAME" in
    Darwin*)
        PLATFORM="mac"
        OUTPUT_NAME="DevUtils-macOS"
        ;;
    MINGW*|CYGWIN*|MSYS*)
        PLATFORM="win"
        OUTPUT_NAME="DevUtils-Windows.exe"
        ;;
    Linux*)
        PLATFORM="linux"
        OUTPUT_NAME="DevUtils-Linux"
        ;;
    *)
        echo "❌ Unsupported platform: $OS_NAME"
        exit 1
        ;;
esac

echo "🎯 Building for platform: $PLATFORM"

# Build native image
native-image \
    --no-fallback \
    --initialize-at-build-time \
    -H:+ReportExceptionStackTraces \
    -H:ReflectionConfigurationFiles=src/main/resources/META-INF/native-image/reflect-config.json \
    -H:ResourceConfigurationFiles=src/main/resources/META-INF/native-image/resource-config.json \
    --add-opens=java.base/java.util=ALL-UNNAMED \
    --add-opens=java.base/java.lang=ALL-UNNAMED \
    -Djavafx.platform=$PLATFORM \
    -jar "$JAR_FILE" \
    -o "target/$OUTPUT_NAME"

echo "✅ Native image built successfully: target/$OUTPUT_NAME"
echo "🚀 You can now run it with: ./target/$OUTPUT_NAME"
