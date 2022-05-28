#!/bin/sh

echo "================================= DROPBOX DOWNLOAD BEGIN ========================================="

echo "Download file $FS_FILE_NAME from dropbox root folder to ./target local folder"

curl -X POST https://content.dropboxapi.com/2/files/download \
  --header "Authorization: Bearer $ENV_DBX_TOKEN" \
  --header "Dropbox-API-Arg: {\"path\": \"/$FS_FILE_NAME\"}" \
  -o ./target/$FS_FILE_NAME

echo "================================= DROPBOX DOWNLOAD END ==========================================="
