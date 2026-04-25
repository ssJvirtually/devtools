# GitHub Actions Setup Guide

This guide explains how to set up and use the automated build and release system.

## Overview

This project includes GitHub Actions workflows that automatically:
- ✅ Build JAR files and native binaries when a **GitHub Release** is created
- ✅ Build native binaries for Windows and macOS using GraalVM
- ✅ Attach all artifacts to the GitHub Release automatically

## Files Created

### GitHub Workflows
- `.github/workflows/release.yml` - Main workflow that builds and releases

### GraalVM Configuration
- `src/main/resources/META-INF/native-image/reflect-config.json` - Reflection access config
- `src/main/resources/META-INF/native-image/resource-config.json` - Resource inclusion config
- `src/main/resources/META-INF/native-image/jni-config.json` - JNI access config

### Build Scripts (Optional Local Use)
- `build-native.sh` - Linux/macOS build script
- `build-native.bat` - Windows build script

### Documentation
- `NATIVE-BUILD.md` - Detailed native build guide

## First-Time Setup

### 1. Push the Workflow

```bash
git add .
git commit -m "Add GitHub Actions workflow for native builds"
git push origin main
```

### 2. Verify the Workflow

1. Go to your repository on GitHub
2. Click the **Actions** tab
3. Select the **Build and Release** workflow
4. Click **Run workflow** to test it manually
5. Wait for it to complete

### 3. Download the First Build

1. Click on the completed workflow run
2. Scroll to the **Artifacts** section
3. Download:
   - `devtools-jar` - The JAR file
   - `DevUtils-windows-x64` - Windows native binary (if build succeeded)
   - `DevUtils-macos-x64` - macOS native binary (if build succeeded)

## Creating Your First Release

### Step 1: Tag Your Commit

```bash
# Make sure you're on the commit you want to release
git tag -a v1.0.0 -m "Initial release - DevUtils v1.0.0"
git push origin v1.0.0
```

### Step 2: Wait for the Workflow

1. Go to **Actions** tab
2. Watch the **Build and Release** workflow
3. It will:
   - Build the JAR
   - Build native binaries (Windows & macOS)
   - Create a GitHub Release automatically

### Step 3: Check the Release

1. Go to **Releases** section (right sidebar)
2. You'll see the new release with:
   - Auto-generated release notes
   - All build artifacts attached

## Troubleshooting

### Workflow Not Starting

**Problem**: No workflow runs after push

**Solutions**:
1. Check that `.github/workflows/release.yml` exists
2. Go to **Settings** → **Actions** → **General**
3. Ensure "Allow all actions and reusable workflows" is selected
4. Check the workflow file syntax: `act --dryrun` (if you have `act` installed)

### GraalVM Build Failing

**Problem**: Native image build fails with errors

**Common Solutions**:

#### 1. Reflection Errors
```
ERROR: Could not find class ...
```
**Fix**: Add the class to `src/main/resources/META-INF/native-image/reflect-config.json`

#### 2. Resource Not Found
```
ERROR: Resource not found: /styles/theme.css
```
**Fix**: Add the resource pattern to `src/main/resources/META-INF/native-image/resource-config.json`

#### 3. JNI Errors
```
ERROR: Unsupported feature: JNI
```
**Fix**: Update `src/main/resources/META-INF/native-image/jni-config.json`

#### 4. Initialization Errors
```
ERROR: Classes that should be initialized at run time got initialized during image building
```
**Fix**: Add `--initialize-at-run-time=<package>` to the native-image command in the workflow

### Release Not Created

**Problem**: Tag pushed but no release created

**Check**:
1. Tag format must start with `v` (e.g., `v1.0.0`, `v2.1.3`)
2. Workflow must complete successfully
3. Check workflow logs for any errors

## Customization

### Change the Build Version

Edit `pom.xml`:
```xml
<version>1.0.0</version>
```

### Add More Native Image Options

Edit `.github/workflows/release.yml` and add options to the `native-image` command:

```yaml
- name: Build Native Image
  run: |
    native-image \
      --no-fallback \
      --verbose \
      --report-unsupported-elements-at-runtime \
      ...
```

### Include Additional Resources

Edit `src/main/resources/META-INF/native-image/resource-config.json`:
```json
{
  "resources": [
    {
      "pattern": ".*\\.your-extension$"
    }
  ]
}
```

## Monitoring Builds

### View Workflow Status

- **Green checkmark** ✅ - Build succeeded
- **Red X** ❌ - Build failed (check logs)
- **Yellow circle** 🟡 - Build in progress

### Get Notifications

1. Go to **Settings** → **Notifications**
2. Choose how you want to be notified
3. You'll get alerts for failed builds

## Best Practices

### 1. Test Locally First
```bash
mvn clean package
java -jar target/devtools-1.0-SNAPSHOT.jar
```

### 2. Use Semantic Versioning
- `v1.0.0` - Major release
- `v1.1.0` - Minor features added
- `v1.0.1` - Bug fixes only

### 3. Write Good Commit Messages
Release notes are auto-generated from commits, so write clear messages:
```
✅ Add JSON formatter with syntax highlighting
❌ fix stuff
```

### 4. Test Native Builds
After release, download and test the native binaries to ensure they work.

### 5. Keep Configuration Updated
When you add new classes or resources, update the GraalVM config files.

## Advanced: Manual Workflow Dispatch

You can trigger builds manually:

1. Go to **Actions** → **Build and Release**
2. Click **Run workflow** dropdown
3. Select branch
4. Click **Run workflow**

This is useful for:
- Testing builds without pushing code
- Rebuilding after fixing issues
- Building feature branches

## Support

If you encounter issues:

1. Check the workflow logs (click on the failed step)
2. Review [NATIVE-BUILD.md](NATIVE-BUILD.md) for GraalVM-specific issues
3. Check [GraalVM Native Image docs](https://www.graalvm.org/reference-manual/native-image/)
4. Search [GitHub Community](https://github.community/)

## Example Workflow Timeline

```
Day 1: Initial Setup
  → Push code to main
  → Workflow runs automatically
  → Download artifacts to test

Day 2: Ready for Release
  → git tag -a v1.0.0 -m "Release v1.0.0"
  → git push origin v1.0.0
  → Wait ~15 minutes
  → Release appears with all binaries

Day 3: Bug Fix
  → Fix bugs and push to main
  → git tag -a v1.0.1 -m "Fix critical bugs"
  → git push origin v1.0.1
  → New release created automatically
```
