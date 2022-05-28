#!/bin/sh

echo "================================= DROPBOX UPLOAD BEGIN ========================================="

echo "Upload file from $FS_FILE_PATH to dropbox $FS_UPLOAD_PATH"

curl -X POST https://content.dropboxapi.com/2/files/upload \
  --header "Authorization: Bearer $ENV_DBX_TOKEN" \
  --header "Dropbox-API-Arg: {\"path\": \"/$FS_UPLOAD_PATH\",\"mode\": \"overwrite\",\"autorename\": true,\"mute\": false}" \
  --header "Content-Type: application/octet-stream" \
  --data-binary @$FS_FILE_PATH

echo "================================= DROPBOX UPLOAD END ==========================================="
