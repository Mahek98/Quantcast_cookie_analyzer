# Cookie Analyzer

This project is a simple Java application to process a cookie log file and find the most active cookies for a specific date. The log file contains a list of cookies and their corresponding timestamps. The program identifies the cookies that appeared the most on a given date.

## Features

- Reads a CSV log file with cookie data and timestamps.
- Allows the user to specify a date and find the most active cookies on that date.
- Can run the application via a Java JAR file or a simple shell/batch script for easier execution.

## Prerequisites

- Java 21 or higher installed on your system.
- Maven for building the project (if you're compiling from source).
- CSV log file with the format:
  ```
  cookie,timestamp
  AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
  SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00
  5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00
  ```

## Project Structure

```
.
├── src
│   └── main
│       └── java
│           └── com
│               └── quantcast
│                   └── cookieanalyzer
│                       ├── Cookie.java
│                       ├── CookieLogParser.java
│                       ├── CookieLogProcessor.java
│                       ├── MostActiveCookieApp.java
├── target
│   └── cookieanalyzer-1.0-SNAPSHOT.jar
├── run.sh 
└── pom.xml (Maven configuration file)
```

## How to Run the Application

### **1. Build the JAR File (if you haven't already)** -- however built jar is already added with this projct.

### **2. Run the Application Using Java JAR**

To run the application manually via the command line, use:

```bash
java -jar target/cookieanalyzer-1.0-SNAPSHOT.jar -f <path-to-csv-file> -d <YYYY-MM-DD>
```

#### Example:

```bash
java -jar target/cookieanalyzer-1.0-SNAPSHOT.jar -f cookie_log.csv -d 2018-12-09
```

This will output the most active cookies for the date `2018-12-09`.

### **3. Run the Application Using a Script**

For convenience, you can use the provided shell script (`run.sh`) to run the application.

#### **On Linux**:

1. Make the script executable:

```bash
chmod +x run.sh
```

2. Run the script with the following command:

```bash
./run.sh -f <path-to-csv-file> -d <YYYY-MM-DD>
```

#### Example:

```bash
./run.sh -f cookie_log.csv -d 2018-12-09
```
### **4. Output**

The most active cookies for the given date will be displayed in the console in a human-readable format. For example:

```
       *** Most Active Cookies for 2018-12-09 ***
       
            AtY0laUfhglK3lC7
```

## File Format

The CSV file should have the following format:

```
cookie,timestamp
<cookie-id>,<timestamp>
<cookie-id>,<timestamp>
...
```

- `cookie`: The unique identifier for the cookie.
- `timestamp`: The timestamp of when the cookie was logged, in ISO 8601 format with UTC offset (e.g., `2018-12-09T14:19:00+00:00`).

## Troubleshooting

- **Error reading file**: Ensure the path to the CSV file is correct and accessible.
- **Invalid date format**: Make sure you provide the date in the correct format (`YYYY-MM-DD`), e.g., `2018-12-09`.


## Screenshots of sample outputs have been added to the folder as well.