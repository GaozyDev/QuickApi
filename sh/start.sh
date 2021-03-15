# vim start.sh
#!/bin/sh
cd /home/pi/Service && java -jar quickapi-0.0.1-SNAPSHOT.jar > /home/pi/Service/log.text &
echo $! > /home/pi/Service/quickapi.pid