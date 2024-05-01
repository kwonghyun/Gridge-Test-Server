#!/bin/bash

BUILD_JAR="/home/ubuntu/sns/build/libs/demo-0.0.1-SNAPSHOT.jar"



nohup java -jar -Dspring.profiles.active=prod $BUILD_JAR > /home/ubuntu/sns/nohup.out 2>&1 &

