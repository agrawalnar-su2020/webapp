#!/bin/bash
cd /var
sudo java -jar webapps-0.0.1-SNAPSHOT.war > /dev/null 2> /dev/null < /dev/null &
