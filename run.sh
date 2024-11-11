#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -ne 4 ]; then
    echo "Usage: ./run.sh -f <filename> -d <YYYY-MM-DD>"
    exit 1
fi

# Parse arguments
while getopts "f:d:" opt; do
    case $opt in
        f) filename="$OPTARG" ;;
        d) date="$OPTARG" ;;
        *) echo "Usage: ./run.sh -f <filename> -d <YYYY-MM-DD>"; exit 1 ;;
    esac
done

# Run the Java program
java -jar target/cookieanalyzer-1.0-SNAPSHOT.jar -f "$filename" -d "$date"
