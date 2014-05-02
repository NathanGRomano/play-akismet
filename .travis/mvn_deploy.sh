#!/bin/bash
echo "Current branch: $TRAVIS_BRANCH."
if [ "$TRAVIS_BRANCH" = "master" ]
then
echo "Deploying to maven as branch is master. $TRAVIS_BRANCH"
mvn deploy --settings .travis/settings.xml
else
echo "NOT deploying to maven repo."
fi
