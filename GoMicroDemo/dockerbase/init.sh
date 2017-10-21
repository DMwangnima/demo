#!/bin/bash

mysql -u root -e "CREATE DATABASE gomicro "

R=/data/deploy/gomicro
cd $R
for v in `ls vendor`; do
        if [ "$r" != "vendor.json" ]; then
            scp -r vendor/$v /data/services/go/src/
        fi
done
bash build_local.sh all