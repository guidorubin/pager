#!/usr/bin/env bash


if [ ! -n "$version.properties" ]
then
    echo "You need define the $version.properties variable in App Center"
    exit
fi

# This is the path to the google-services.json file, Update 'Android' to be the
# correct path to the file relative to the root of your repo
VERSION_PROPERTIES_FILE="version.properties"

if [ -e "$VERSION_PROPERTIES_FILE" ]
then
    echo "Updating google-services.json"
    echo $APPCENTER_BUILD_ID > $VERSION_PROPERTIES_FILE

    echo "File content:"
    cat $VERSION_PROPERTIES_FILE
fi