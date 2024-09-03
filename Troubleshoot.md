# Troubleshooting

Problems encountered while following the tutorial.

## Build - Error downloading dependency

When building build.sbt with new dependencies.

Example error log

```
sbt.librarymanagement.ResolveException: Error downloading org.apache.spark:spark-core:3.0.1
```

Problem: Scala version in build.sbt is not compatible with the dependency version.

Hint: Check the file not found from mvnrepository (url ends with "scala version/lib version".pom).