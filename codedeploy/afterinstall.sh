#!/bin/bash
cd /var
nohup java -jar webapps-0.0.1-SNAPSHOT.war > /dev/null 2> /dev/null < /dev/null &
cd ~
sleep 240
