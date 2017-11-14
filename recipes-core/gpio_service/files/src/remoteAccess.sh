#!/bin/sh

case $1 in
    start)
        systemctl start ssh.service
        ;;

    stop)
        systemctl stop ssh.service
        ;;

    restart)
        systemctl restart ssh.service
        ;;

    enable)
        systemctl enable ssh.service
        ;;

    disable)
        systemctl disable ssh.service
        ;;

    verify)
        if [ "`systemctl is-active ssh.service`" != "active" ]; then
            exit 1
        else
            exit 0
        fi
        ;;
   *)
      echo "$0 {start|stop|restart|test}"
      exit 0

  esac


