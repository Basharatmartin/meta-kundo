#!/bin/bash

/home/marty/zwarelocal-aarch64/install.sh /home/marty/zwarelocal
#chown marty:root -R /home/marty/zwarelocal/
echo 'installation is done'

echo 'removing unnecessary dirs ...'

rm -r /home/marty/zwarelocal-aarch64

echo 'disabling zwarelocal-install service'

#systemctl stop zwarelocal-install
systemctl disable zwarelocal-install
rm $0
