#! /bin/bash

toolDir=$(dirname $0)
libsDir=${toolDir}/libs

libs=$(ls ${toolDir}/*.jar)
for lib in $(ls ${libsDir}); do
  libs=${libs}:${libsDir}/${lib}
done

java -cp "${libs}" com.schubergphilis.cloudstackdb.ApplicationRunner $@
