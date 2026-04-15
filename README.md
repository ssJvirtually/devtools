# DevUtils

A lightweight, developer-focused utility application built with JavaFX. DevUtils provides a suite of essential tools for daily development tasks, including formatting, conversion, and decoding.

## Demo

![DevUtils Demo](assets/DevUtils-demo-reel.gif)

## Features


- **JSON Formatter**: Prettify and validate JSON strings with syntax highlighting.
- **YAML to JSON**: Convert YAML configurations to JSON format seamlessly.
- **Base64 Decode**: Quickly decode Base64 encoded strings.
- **JWT Debugger**: Inspect and debug JSON Web Tokens.
- **URL Encode/Decode**: Handle URL encoding and decoding for web development.

## Project Structure

- **JavaFX**: Modern UI components and layouts.
- **RichTextFX**: Enhanced text editing with syntax highlighting support.
- **Jackson**: High-performance JSON and YAML processing.
- **Maven**: Dependency management and build automation.

## Prerequisites

- **Java JDK 25** or higher.
- **Maven 3.6** or higher.

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ssJvirtually/devtools.git
   cd devtools
   ```

2. **Build the project**:
   Use Maven to download dependencies and compile the source code.
   ```bash
   mvn clean install
   ```

## Running the Project

You can run the application directly using the JavaFX Maven plugin:

```bash
mvn javafx:run
```

Alternatively, you can run the shaded JAR file generated in the `target` directory:

```bash
java -jar target/devtools-1.0-SNAPSHOT.jar
```

## Testing

Run the included unit tests to ensure everything is working correctly:

```bash
mvn test
```

## CI/CD & Releases

This project uses GitHub Actions to automatically build and release native binaries.

### Automatic Builds

On every push to `main` or `master` branch:
- JAR file is built and uploaded as an artifact
- Native binaries are built for Windows and macOS (if GraalVM build succeeds)

### Creating a Release

To create a release with native binaries:

1. **Tag your commit**:
   ```bash
   git tag -a v1.0.0 -m "Release v1.0.0"
   git push origin v1.0.0
   ```

2. **GitHub Actions will automatically**:
   - Build native binaries for Windows and macOS using GraalVM
   - Create a GitHub Release with all artifacts
   - Generate release notes from commit messages

### Manual Trigger

You can also trigger the build manually from the Actions tab:
1. Go to **Actions** → **Build and Release**
2. Click **Run workflow**
3. Select the branch
4. Click **Run workflow**

### Downloading Builds

**From Artifacts** (before release):
1. Go to **Actions** tab
2. Click on the latest workflow run
3. Download artifacts from the bottom of the page

**From Releases** (after tagging):
1. Go to **Releases** section
2. Download from the latest release

## Native Builds

For information about building native binaries locally, see [NATIVE-BUILD.md](NATIVE-BUILD.md).

### Prerequisites for Native Builds

- **Windows**: Visual Studio Build Tools 2022
- **macOS**: Xcode Command Line Tools (`xcode-select --install`)
- **Linux**: GCC and development libraries
