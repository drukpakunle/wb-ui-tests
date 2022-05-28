#!/bin/sh

echo "Installation of libraries..."
apt-get install -y unzip
echo "Installation of libraries complete"

echo "================================= MAVEN LIBRARIES DOWNLOAD BEGIN ========================================="

echo "Download .m2 from dropbox"
mkdir -p ./target
export FS_FILE_NAME='.m2.zip'
sh ./scripts/dropbox-download.sh
echo "Downloaded .m2 from dropbox complete"

echo "Unzip .m2..."
unzip -o -qq ./target/.m2
echo "Unzip .m2 complete"

echo "================================= MAVEN LIBRARIES DOWNLOAD END ==========================================="
