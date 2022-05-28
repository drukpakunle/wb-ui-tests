#!/bin/bash

aboutUrl="$ENV_URL/_about?json=Y"
timeOutInSeconds=30
waitMaximumTimeInSeconds=600
waitTimeInSeconds=0

getResponseCodeCmd=$(
  cat <<EOF
  curl -o /dev/null -s -w "%{http_code}\n" --insecure "$aboutUrl" --cookie "mobile_client=1"
EOF
)

getVersionCmd=$(
  cat <<EOF
  curl -s --insecure "$aboutUrl" --cookie "mobile_client=1" | jq '.appVersion' | sed 's/\"\(.*\)\"/\1/'
EOF
)

while [ "$(sh -c -e "$getResponseCodeCmd")" != "200" ] && (($waitMaximumTimeInSeconds > $waitTimeInSeconds)); do
  echo "wait..."
  sleep $timeOutInSeconds
  ((waitTimeInSeconds += timeOutInSeconds))
done

if [ "$(sh -c -e "$getResponseCodeCmd")" = "200" ]; then
  sh -c -e "$getVersionCmd"
else
  echo "version not available"
fi
