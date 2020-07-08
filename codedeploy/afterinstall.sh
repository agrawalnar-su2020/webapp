#!/bin/bash
sudo cp /var/restartwebapp.service /etc/systemd/system/
sudo chmod +x /etc/systemd/system/restartwebapp.service
sudo systemctl daemon-reload
sudo systemctl enable restartwebapp.service
sudo systemctl restart restartwebapp.service

cd /var
sudo java -jar webapps-0.0.1-SNAPSHOT.war > /dev/null 2> /dev/null < /dev/null &
cd ~
sleep 240
sudo systemctl restart amazon-cloudwatch-agent
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/amazon-cloudwatch-agent.json \
    -s