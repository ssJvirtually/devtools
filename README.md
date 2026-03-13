# DevUtils

A lightweight, developer-focused utility application built with JavaFX. DevUtils provides a suite of essential tools for daily development tasks, including formatting, conversion, and decoding.

## Demo

<div align="center">
  <video src="assets/DevUtils-demo-reel.mp4" width="800" controls></video>
</div>

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

- **Java JDK 17** or higher.
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
