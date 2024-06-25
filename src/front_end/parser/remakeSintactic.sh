#!/bin/bash

# Define paths relative to the parser directory
CUP_JAR=../../../lib/java-cup-11b.jar
CUP_FILE=./Sintactic.cup
OUTPUT_DIR=.

# Navigate to the directory containing this script
cd "$(dirname "$0")"

# Run CUP
java -jar $CUP_JAR -parser Parser -symbols ParserSym -destdir $OUTPUT_DIR $CUP_FILE

echo "Files generated in $OUTPUT_DIR"

