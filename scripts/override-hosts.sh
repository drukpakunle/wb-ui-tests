#!/bin/bash

if [[ $ENV_OVERRIDE_HOST == "true" ]]; then
  apt-get install -y iputils-ping

  echo "================================= OVERRIDE HOSTS BEGIN ========================================="

  hostsFilePath='/etc/hosts'

  cmd=$(
    cat <<EOF
      echo $ENV_IP \
      www.wildberries.ru \
      www.wildberries.by \
      by.wildberries.ru \
      www.wildberries.am \
      am.wildberries.ru \
      www.wildberries.kg \
      kg.wildberries.ru \
      www.wildberries.kz \
      kz.wildberries.ru \
      >>$hostsFilePath
EOF
  )

  echo "executing command: $cmd ...."
  sh -c -e "$cmd"
  echo "executing complete"

  echo "Hosts overriding testing..."
  ping "www.wildberries.ru" -c 1
  ping "by.wildberries.ru" -c 1
  ping "am.wildberries.ru" -c 1
  ping "kg.wildberries.ru" -c 1
  ping "kz.wildberries.ru" -c 1

  echo "hosts file data:"
  cat "$hostsFilePath"
  echo "Hosts overriding testing complete"

  echo "================================= OVERRIDE HOSTS END ==========================================="
else
  echo "Run without hosts overriding"
fi
