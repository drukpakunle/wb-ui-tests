#!/bin/bash

echo "Installation of libraries..."
apt-get install -y zip unzip jq rsync allure
echo "Installation of libraries complete"

echo "================================= BUILD PAGES BEGIN ========================================="

rm -rf public
mkdir -p public

rm -rf ./target/.public
mkdir -p ./target/.public

echo "Download reports cache from dropbox..."
export FS_FILE_NAME='public.zip'
sh ./scripts/dropbox-download.sh
export FS_FILE_NAME='public-ru.zip'
sh ./scripts/dropbox-download.sh
export FS_FILE_NAME='public-by.zip'
sh ./scripts/dropbox-download.sh
export FS_FILE_NAME='public-am.zip'
sh ./scripts/dropbox-download.sh
export FS_FILE_NAME='public-kg.zip'
sh ./scripts/dropbox-download.sh
export FS_FILE_NAME='public-kz.zip'
sh ./scripts/dropbox-download.sh
echo "Reports cache downloaded"

echo "Unzip reports cache..."
unzip -oo ./target/'*.zip'
echo "Unzip reports complete"

rsync -r report/ public
rsync -r .public/* public

params=$(
  cat <<EOF
[]
EOF
)

echo "Build history.json..."
for pipelineDir in $(find public -type d -regex '.*/[0-9]*'); do
  echo pipelineDir: $pipelineDir
  [ -e "$pipelineDir/params.json" ] && params=$(echo $params | jq ". + $(jq '.' "$pipelineDir/params.json")")
done

jq '.' <<<$params

echo "History JSON validation..."
if [[ $(echo $params | jq type) == "\"array\"" ]]; then
  echo "JSON is valid. Update history.json..."
  jq -n "$params" >.public/history.json
  rsync -r .public/* public

  publicZipFilePath="./target/public.zip"

  echo "Zip public folder to $publicZipFilePath"
  zip -o -r -qq "$publicZipFilePath" public
  echo "Zip public folder complete"

  echo "Upload reports cache to dropbox..."
  export FS_FILE_PATH=$publicZipFilePath
  export FS_UPLOAD_PATH='public.zip'
  sh ./scripts/dropbox-upload.sh
  echo "Reports cache uploaded"

  historyJson=$(jq '.' "public/history.json")
  echo historyJson:
  jq '.' <<<$historyJson
else
  echo "JSON is not valid. Build report was interrupted"
  jq '.' <<<$params
fi

echo "Allure Report Link: " http://mobile.git.wildberries.ru/site-tests
echo "=================================== BUILD PAGES END ==========================================="
