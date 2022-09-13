# MiniSim
Minimal N-bodies simulation written with Java + JavaFX + Gradle

## Local development
This command represents the entire development worflow:
```bash
./gradlew spotlessApply build jacocoTestReport createExe run
```

To run mutation testing, use
```bash
./gradlew pitest
```