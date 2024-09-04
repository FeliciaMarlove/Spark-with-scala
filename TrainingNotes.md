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

Suffixing with ```% test``` => package is not accessible in non-test classes.

## Spark

= Distributed processing engine. Usually used on top of a Data Lake. SQL-like API to access the data (also writing).
Master-Slave architecture. 1 Driver on the master node for scheduling tasks on the cluster + many workers for the workload (+ Cluster manager -> instantiate the Driver and the Workers and manage resource allocation in the cluster).
Driver processes the code we wrote then plans and distributes tasks among the workers.
=> might seem slow in local development => really shines when there are parallelization capabilities and big files to handle.

## Partitioning

Base for parallelization. Aim at equally sized partitions to distribute the load (but often challenging because of the nature of real-life data => on of the major challenges regarding performance).
Same size = each worker will take the same time to complete their tasks. 

## Lazy evaluation

Transformations simply add a step to the AST (lazy), actions triggers the execution of the AST => result is only evaluate when we want to see it.