#!/bin/bash
echo "#!/bin/bash" > /tmp/tempFile.sh
echo "cd /var" >> /tmp/tempFile.sh
echo "sudo java -jar webapps-0.0.1-SNAPSHOT.war" >> /tmp/tempFile.sh
cd /tmp/
./tempFile.sh > run.out 2> run.err &