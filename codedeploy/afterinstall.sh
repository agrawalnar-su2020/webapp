#!/bin/bash
cd /var
nohup java -jar webapps-0.0.1-SNAPSHOT.war 1> /home/ubuntu/applogs/webapp.out 2>&1 </dev/null &
cd ~
sleep 240
