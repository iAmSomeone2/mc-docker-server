#!/bin/sh

# Remove old version of release-info-clean
if [ -e json/release-info-clean.json ]; then
    rm json/release-info-clean.json
fi

if [ -e json/release-info.json ]; then
    cat json/release-info.json | jq '.' > json/release-info-clean.json

    # Remove the dirty version of the file
    if [ -e json/release-info-clean.json ]; then
        rm json/release-info.json
    fi
else
    echo "File: json/release-info.json could not be found."
    echo "No changes were made."
fi