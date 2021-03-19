### Quartz with Spring boot, MongoDB
Quartz is a job scheduling library that can be integrated into a wide variety of Java applications. Quartz is generally used for enterprise class applications to support process workflow, system management actions and to provide timely services within the applications.

```text
What is Quartz Job Scheduling library ?
Quartz is open source job scheduling library that can be integrate with Java application. 
It can be used to create simple and complex schedules for executing tens, hundred or thousand of jobs.
Jobs whose tasks are defined as standard Java Components that may execute virtually anything you may program them to do.
```

```text
Quartz can help on following points:
1. Job Scheduling to run when a given Trigger occurs at certain time of day, of the week/month/year.
2. not on certain days listed within a registered Calendar (such as business holidays)
2. Providing reminder services within an application.
2. Driving Process Workflow
```

```text
Job Scheduling:
Jobs are given names by their creator and can also be organized into named groups. Triggers may also be given names and placed into groups, 
in order to easily organize them within the scheduler. Jobs can be added to the scheduler once, but registered with multiple Triggers. Within an enterprise Java environment, 
Jobs can perform their work as part of a distributed (XA) transaction.
. at a certain time of day (to the millisecond)
. on certain days of the week
. on certain days of the month
. on certain days of the year
. not on certain days listed within a registered Calendar (such as business holidays)
. repeated a specific number of times
. repeated until a specific time/date
. repeated indefinitely
. repeated with a delay interval
```
```text
Job Execution
. Jobs can be any Java class that implements the simple Job interface, leaving infinite possibilities for the work your Jobs can perform
. Job class instances can be instantiated by Quartz, or by your application’s framework.
. When a Trigger occurs, the scheduler notifies zero or more Java objects implementing the JobListener and TriggerListener interfaces
. listeners can be simple Java objects, or EJBs, or JMS publishers, etc. These listeners are also notified after the Job has executed.
. As Jobs are completed, they return a JobCompletionCode which informs the scheduler of success or failure. The JobCompletionCode can also instruct the scheduler of any actions it should take based on the success/fail code - such as immediate re-execution of the Job
```

```text
Job Persistence
. The design of Quartz includes a JobStore interface that can be implemented to provide various mechanisms for the storage of jobs.
. With the use of the included JDBCJobStore, all Jobs and Triggers configured as "non-volatile" are stored in a relational database via JDBC.
. With the use of the included RAMJobStore, all Jobs and Triggers are stored in RAM and therefore do not persist between program executions - but this has the advantage of not requiring an external database.
```

```text
Transactions
. Quartz can participate in JTA transactions, via the use of JobStoreCMT (a subclass of JDBCJobStore).
. Quartz can manage JTA transactions (begin and commit them) around the execution of a Job, so that the work performed by the Job automatically happens within a JTA transaction
```

```text
Clustering
. Fail-over.
. Load balancing.
. Quartz’s built-in clustering features rely upon database persistence via JDBCJobStore (described above).
. Terracotta extensions to Quartz provide clustering capabilities without the need for a backing database.
```

```text
Listeners & Plug-Ins
. Applications can catch scheduling events to monitor or control job/trigger behavior by implementing one or more listener interfaces.
. The Plug-In mechanism can be used add functionality to Quartz, such keeping a history of job executions, or loading job and trigger definitions from a file.
. Quartz ships with a number of "factory built" plug-ins and listeners.
```