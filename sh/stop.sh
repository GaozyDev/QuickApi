# vim stop.sh
#!/bin/sh
PID=$(cat /home/pi/Service/quickapi.pid)
kill -9 $PID

# chmod +x ./*.sh