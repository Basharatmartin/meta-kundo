#!/bin/bash

if ping6 `cat /var/log/6lbr.ip` -c 1; then
        rm *.dmp
        sudo route -A inet6 add aaaa::/64 gw `cat /var/log/6lbr.ip`
	exit 0
else
        exit 1
fi

