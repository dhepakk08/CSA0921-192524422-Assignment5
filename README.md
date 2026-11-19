# CSA09 Programming in Java Assignment 5 - Solution Files

The ZIP contains solution source, Part A pseudocode, SQL setup, test inputs, and blank evidence templates. The analytical report is intentionally excluded.

Before submission, execute each task individually, complete/execute supplied TODO programs, compare outputs, capture genuine screenshots, retain raw measurements, and make at least one progressive Git commit per task. This package does not claim those activities were completed. Fill the evidence templates after your own runs; do not submit blanks as measurements. Create and share repository CSA09-<RegisterNumber>-Assignment5 separately. Add your name, register number, Java version, task run steps, results and working repository link.

Requirements: JDK 11+, MySQL 8 (or transactional JDBC database), and the matching JDBC driver JAR (not included; place it in lib/). Set JDBC_URL, JDBC_USER and JDBC_PASSWORD. Example URL: jdbc:mysql://localhost:3306/csa09_assignment5. Run sql/setup.sql only in a disposable local assignment database. Injection/failure demonstrations must never target production.

Compile file tasks from this folder (PowerShell):
    New-Item -ItemType Directory -Force build | Out-Null
    javac -d build src\\TaskB1\\PassStore.java src\\TaskB1\\BufferedLineCounter.java
    java -cp build TaskB1.PassStore
    java -cp build TaskB1.BufferedLineCounter inputs\\attendance_sample.txt

For JDBC programs, put the driver JAR in lib/:
    $env:CLASSPATH = "build;lib\\*"
    javac -cp $env:CLASSPATH -d build src\\common\\Db.java src\\TaskA3\\*.java src\\TaskA4\\*.java src\\TaskA5\\*.java src\\TaskB2\\*.java src\\TaskB3\\*.java src\\TaskB4\\*.java

Run TaskB2.LoginInjectionDemo, TaskB3.PassMetadataCheck, TaskB3.JdbcInsertBenchmark, TaskB3.TransactionFailureDemo, TaskB4.PassMigration and TaskB4.DatabaseMetadataReport using the same classpath. Run sql/setup.sql first. The benchmark and transaction demonstration clear bus_pass_benchmark, so keep this database disposable.

Contents: PartA/Pseudocode A1-A5; src folders for A3/A4/A5 and B1-B4; sql setup; inputs; evidence templates.
The supplied PassStore starter is simple five-column comma-delimited data and does not implement quoted CSV parsing. Fixtures avoid commas/newlines in fields. A1 pseudocode covers all seven business fields. For production CSV, use a CSV library. Never serialize passwords or live JDBC connections. Times, screenshots, database output, and student details must come from your own work.
