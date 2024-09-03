# Training notes

Source of the tutorials: https://www.youtube.com/playlist?list=PLeEh_6coH9EpIwsiNiDCW0V-UjNZElF0d

## Install Spark locally on Ubuntu

/!\ Choose the right version

https://medium.com/@patilmailbox4/install-apache-spark-on-ubuntu-ffa151e12e30

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
