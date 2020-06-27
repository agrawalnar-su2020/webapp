#!/bin/bash
sudo kill -9 $(lsof -t -i:8080)
sudo kill -9 $(lsof -t -i:8080)
sudo rm -rf /app/webapps-0.0.1-SNAPSHOT.war