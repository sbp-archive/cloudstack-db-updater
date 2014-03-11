#! /bin/bash

installDir=$1

printUsage() {
  echo "Usage: $0 <installation directory>"
}

checkInstallDir() {
  installDir=$1

  if [ -d $installDir ]; then
    echo "Directory $installDir exists"
    echo "Should delete $installDir and install there? (y/n)"
    read answer
    if [ -z $answer ] | [ $answer != "y" ]; then
      echo "Quitting"
      exit
    else
      echo "Will delete existing directory"
      echo -n "In 3..."; sleep 1
      echo -n "2..."; sleep 1
      echo -n "1..."; sleep 1
      echo "Done"
      rm -rf $installDir
    fi
  fi
}

if [ -z $installDir ]; then
  echo "Cannot install software without a target installation directory"
  printUsage
  exit 1
fi


installedLibsDir=${installDir}/libs

mvn clean package
if [ $? -eq 0 ]; then
  checkInstallDir ${installDir}

  mkdir -p ${installedLibsDir}
  cp target/libs/* ${installedLibsDir}
  cp target/*.jar ${installDir}
  cp src/main/scripts/cs-db-updater.sh ${installDir}
  echo "Installation finished."
  exit 0
else
  echo "Build failed"
  exit 2
fi

