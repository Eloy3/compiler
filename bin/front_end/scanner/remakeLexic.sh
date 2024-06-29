#!/bin/bash

# Define the JFlex specification file and the generated Java file
JFLEX_FILE="Lexic.flex"
JAVA_FILE="Lexic.java"

# Remove the existing Java file if it exists
if [ -f "$JAVA_FILE" ]; then
    rm "$JAVA_FILE"
    echo "Eliminated the file $JAVA_FILE"
else
    echo "The file $JAVA_FILE does not exist."
fi

# Execute JFlex
jflex "$JFLEX_FILE"


