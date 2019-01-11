#!/bin/sh

# Get version manifest and save it for later
OLD_VER="0.0.0"
echo "Getting latest Minecraft version mainfest..."
curl -s https://launchermeta.mojang.com/mc/game/version_manifest.json | jq '.' > mc_version_manifest.json

# Check if info for the previous release is present, and load it in if it is.
if [ -e release.json ]; then
    OLD_VER=$(cat release.json | jq -r '.current')
else
    echo "\nERROR: Release file does not exist!"
    echo "A release.json file needs to be added to this directory."
    exit 2
fi

# Use latest.url to get the JSON file with the download links
if [ -e mc_version_manifest.json ]; then
    # Get current release version number
    VER=$(cat mc_version_manifest.json | jq -r '.latest.release')
    # Write latest value to the release.json file.
    echo "\nLatest Minecraft server release is" $VER
    jq -n --arg old "${OLD_VER}" --arg new "${VER}" '{"current": $old, "latest": $new}' > release.json
    exit 0
else
    echo "\nERROR: Version manifest could not be fetched. Update postponed."
    exit 1
fi