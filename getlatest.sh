#!/bin/sh

# Get version manifest and save it for later
curl -s https://launchermeta.mojang.com/mc/game/version_manifest.json | jq '.' > mc_version_manifest.json

# Get current release version number
VER=$(cat mc_version_manifest.json | jq -r '.latest.release')
echo $VER

# Get JSON file containing download info for latest stable release

# First, loop through the versions array to find the id that matches
echo "Looking for latest release in manifest."
for VERSION in $(cat mc_version_manifest.json | jq '.versions')
do
    echo
    echo $VERSION
    echo
    # Check if the id matches the version number
    if [ $VERSION | jq '.id' = $VER ]; then
        echo "Matching entry found!"
        # print the url to a file and exit the loop
        $VERSION | jq '.url' > latest.url
        break
    fi
done

# Use latest.url to get the JSON file fithe the download links
if [ -e latest.url ]; then
    curl -s $(cat latest.url) | jq '.downloads.server.url' | echo
else
    echo "Couldn't find matching file in manifest."
fi