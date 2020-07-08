#!/bin/bash
sudo kill -9 $(sudo lsof -t -i:8080)
sudo kill -9 $(sudo lsof -t -i:8080)
cd /var
sudo rm -f webapps-0.0.1-SNAPSHOT.war
sudo rm -f webapps.log
cd ~
sleep 10