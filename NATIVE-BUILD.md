# GraalVM Native Image Build Guide

This guide explains how to build DevUtils as a native binary using GraalVM.

## Prerequisites

### Local Build Requirements
- **Liberica NIK (JDK 25)** - Download from [BellSoft](https://bell-sw.com/pages/downloads/native-image-kit/) or use [SDKMAN!](https://sdkman.io/)
- **Native Image tool** - Included in Liberica NIK Full
- **Maven 3.6+**
- **Visual Studio Build Tools** (Windows only) - For native-image compilation
- **Xcode Command Line Tools** (macOS only) - Run `xcode-select --install`

## Installation

### 1. Install Liberica NIK

#### Using SDKMAN! (Linux/macOS)
```bash
# Install Liberica NIK (JDK 25)
sdk install java 25.0.0.r25-nik
sdk use java 25.0.0.r25-nik
```

#### Manual Installation
1. Download Liberica NIK Full (with JavaFX) from [BellSoft](https://bell-sw.com/pages/downloads/native-image-kit/)
2. Extract and set `JAVA_HOME` to the NIK directory
3. Add `$JAVA_HOME/bin` to your `PATH`

### 2. Verify Installation

```bash
java -version
native-image --version
```

## Building Native Image

### Option 1: Using Build Scripts

**Windows:**
```bash
build-native.bat
```

**Linux/macOS:**
```bash
chmod +x build-native.sh
./build-native.sh
```

### Option 2: Manual Build

```bash
# First, build the JAR
mvn clean package -DskipTests

# Then build native image
# Windows:
build-native.bat

# Linux/macOS:
./build-native.sh
```

### Option 3: Direct native-image Command

```bash
mvn clean package -DskipTests

native-image \
  --no-fallback \
  --initialize-at-build-time \
  -H:+ReportExceptionStackTraces \
  -H:ReflectionConfigurationFiles=src/main/resources/META-INF/native-image/reflect-config.json \
  -H:ResourceConfigurationFiles=src/main/resources/META-INF/native-image/resource-config.json \
  --add-opens=java.base/java.util=ALL-UNNAMED \
  --add-opens=java.base/java.lang=ALL-UNNAMED \
  -Djavafx.platform=win \
  -jar target/devtools-1.0-SNAPSHOT.jar \
  -o target/DevUtils
```

## Configuration Files

### reflect-config.json
Defines reflection access for classes used at runtime via reflection (Jackson, JavaFX, etc.)

### resource-config.json
Specifies which resources to include in the native image (CSS, FXML, images, etc.)

## Troubleshooting

### Common Issues

#### 1. "Classes that should be initialized at run time got initialized during image building"
**Solution:** Add `--initialize-at-run-time=<package>` to the native-image command

#### 2. "Unsupported features" error
**Solution:** Add problematic classes to `--initialize-at-build-time` or use `--report-unsupported-elements-at-runtime`

#### 3. Missing resources at runtime
**Solution:** Update `resource-config.json` with the missing resource patterns

#### 4. Reflection access errors
**Solution:** Add the class to `reflect-config.json` with appropriate access flags

### Debugging Native Image Builds

To see more details during the build:
```bash
native-image --verbose ...
```

To generate a report of unused configuration:
```bash
native-image -H:PrintAnalysisCallTree=true ...
```

## GitHub Actions

The workflow automatically builds native binaries for Windows and macOS on:
- Push to `main`/`master` branch
- Tag pushes (e.g., `v1.0.0`)
- Manual trigger via `workflow_dispatch`

### Creating a Release

1. Tag your commit:
```bash
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin v1.0.0
```

2. The workflow will automatically:
   - Build native binaries for Windows and macOS
   - Create a GitHub release with the binaries
   - Attach release notes from commit messages

### Artifacts

Even without tags, the workflow uploads build artifacts that can be downloaded from the Actions tab.

## Performance Notes

- **First build**: Takes 5-15 minutes depending on system
- **Subsequent builds**: Faster due to caching
- **Binary size**: ~50-100MB (includes JVM and dependencies)
- **Startup time**: Near-instant (no JVM startup overhead)

## Limitations

Native images have some limitations compared to running on JVM:
- No dynamic class loading (all classes must be known at build time)
- Reflection requires explicit configuration
- Some Java features are limited (e.g., JVMCI, JVMTI)
- Larger binary size compared to JAR files

## References

- [GraalVM Native Image Documentation](https://www.graalvm.org/latest/reference-manual/native-image/)
- [JavaFX with GraalVM](https://github.com/gluonhq/substrate)
- [Native Image Configuration](https://www.graalvm.org/latest/reference-manual/native-image/Overview/)
