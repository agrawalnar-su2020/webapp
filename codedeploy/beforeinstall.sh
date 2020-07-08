#!/bin/bash
sudo cp /var/restartwebapp.service /etc/systemd/system/
sudo chmod +x /etc/systemd/system/restartwebapp.service
sudo systemctl daemon-reload
sudo systemctl enable restartwebapp.service
sudo systemctl restart restartwebapp.service
sudo kill -9 $(sudo lsof -t -i:8080)
sudo kill -9 $(sudo lsof -t -i:8080)
cd /var
sudo chmod 755 afterinstall.sh
sudo rm -f webapps-0.0.1-SNAPSHOT.war
sudo rm -f webapps.log
cd ~
sleep 10