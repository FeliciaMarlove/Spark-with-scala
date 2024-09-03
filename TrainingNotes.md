# Training notes

Source of the tutorials: https://www.youtube.com/playlist?list=PLeEh_6coH9EpIwsiNiDCW0V-UjNZElF0d

## Build

build.sbt for adding dependencies
```
val sparkVersion = "3.5.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
```
can be written:
```
val sparkVersion = "3.5.0"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion
)
```
