# Troubleshooting

Problems encountered while following the tutorial.

## Build - Error downloading dependency

When building build.sbt with new dependencies.

Example error log

```
sbt.librarymanagement.ResolveException: Error downloading org.apache.spark:spark-core:3.0.1
```

**Problem**: Scala version in build.sbt is not compatible with the dependency version.

Hint: Check the file not found from mvnrepository (url ends with "scala version/lib version".pom).

## Run - cannot access class sun.nio.ch.DirectBuffer

Example error log

```
Exception in thread "main" java.lang.IllegalAccessError: class org.apache.spark.storage.StorageUtils$ (in unnamed module @0x5b7a7f33) cannot access class sun.nio.ch.DirectBuffer (in module java.base) because module java.base does not export sun.nio.ch to unnamed module
```
**Problem**: Missing JVM configuration. Java 17 linked problem (class is not public anymore).

To run in IntelliJ, add this to the JVM options in the Run configuration:
```
--add-exports java.base/sun.nio.ch=ALL-UNNAMED
```
To run with sbt, add the [./.jvmopts]("./.jvmopts") file.

## Run - too many arguments / java.io.InvalidObjectException: ReflectiveOperationException during deserialization

**Problem**: Wrong Scala version in build.sbt

Example error log (probably not relevant/side effects of the problem)
```
Caused by: java.lang.IllegalArgumentException: too many arguments
...
Driver stacktrace:
INFO DAGScheduler: ResultStage 0 (csv at Main.scala:13) failed in 0.208 s due to Job aborted due to stage failure: 
Task 0 in stage 0.0 failed 1 times, most recent failure: Lost task 0.0 in stage 0.0 (TID 0) (**** executor driver): 
java.io.InvalidObjectException: ReflectiveOperationException during deserialization
	at java.base/java.lang.invoke.SerializedLambda.readResolve(SerializedLambda.java:280)
```