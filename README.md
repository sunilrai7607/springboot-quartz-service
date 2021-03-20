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

```java
@Component
@Slf4j
public class EmailNotificationJob implements Job {

    private final EmailNotificationService emailNotificationService;

    @Autowired
    public EmailNotificationJob(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Job ** {} ** fired @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
        emailNotificationService.emailReminder();
        log.info("Next job scheduled @ {}", jobExecutionContext.getNextFireTime());
    }
}
```
```java
@Configuration
@EnableScheduling
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob().ofType(EmailNotificationJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Email_Detail")
                .withDescription("Invoke Email Notification...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Email_Detail")
                .withDescription("Invoke Email Notification")
                .withSchedule(
                        simpleSchedule().repeatForever().withIntervalInMinutes(1)
                )
                .build();
    }
}
```

```java
@Service
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService{
    @Override
    public void emailReminder() {
        log.info("Email Reminder trigger by service");
    }
}
```

```yaml
spring:
  quartz:
    job-store-type: memory

```

```commandline
 [           main] org.quartz.core.QuartzScheduler          : Scheduler meta-data: Quartz Scheduler (v2.3.2) 'quartzScheduler' with instanceId 'NON_CLUSTERED'
  Scheduler class: 'org.quartz.core.QuartzScheduler' - running locally.
  NOT STARTED.
  Currently in standby mode.
  Number of jobs executed: 0
  Using thread pool 'org.quartz.simpl.SimpleThreadPool' - with 10 threads.
  Using job-store 'org.quartz.simpl.RAMJobStore' - which does not support persistence. and is not clustered.
 Starting Quartz Scheduler now
 [           main] org.quartz.core.QuartzScheduler          : Scheduler quartzScheduler_$_NON_CLUSTERED started.
 [eduler_Worker-1] c.s.b.n.job.EmailNotificationJob         : Job ** Qrtz_Email_Detail ** fired @ Fri Mar 19 20:17:04 EDT 2021
 [eduler_Worker-1] c.s.b.n.s.EmailNotificationServiceImpl   : Email Reminder trigger by service
 [eduler_Worker-1] c.s.b.n.job.EmailNotificationJob         : Next job scheduled @ Fri Mar 19 20:18:03 EDT 2021
 [           main] com.sbr.batch.notification.Main          : Started Main in 2.487 seconds (JVM running for 2.862)
 [eduler_Worker-2] c.s.b.n.job.EmailNotificationJob         : Job ** Qrtz_Email_Detail ** fired @ Fri Mar 19 20:18:03 EDT 2021
 [eduler_Worker-2] c.s.b.n.s.EmailNotificationServiceImpl   : Email Reminder trigger by service
[eduler_Worker-2] c.s.b.n.job.EmailNotificationJob         : Next job scheduled @ Fri Mar 19 20:19:03 EDT 2021
```