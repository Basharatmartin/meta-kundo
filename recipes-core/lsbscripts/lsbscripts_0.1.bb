# lsb-init functions recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 11.12.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

SRC_URI += "file://init-functions \
	    file://init-functions.d/20-left-info-blocks \
	    file://init-functions.d/40-systemd \
	    file://cpufrequtils 	\
	    file://bridge.network 	\
	    file://usbnet.network 	\
	    file://firstrun 		\
	    file://reboot 		\
	    file://resize2fs 		\
	    file://firstrun.service 	\
	    file://ssh.service 		\
	    file://mongod 		\
	    file://mongod.service 	\
	    file://mongodb.env 		\
	    file://mongodb.conf 	\
	    file://70-usb-modeswitch.rules \
	    file://basecon.service 	\
	    file://zipgateway.service 	\
	    file://gpio.service		\
	    file://odroid-auto-bridge.service	\
	    file://zwarelocal-install.service   \
	    file://zwarelocal-install.sh        \
	    file://zware-service.service        \
	    file://python-zip-listener.py       \
	    file://listener.service       	\
	   "

S = "${WORKDIR}"
RDEPENDS_lsbscripts ?= "bash"

inherit systemd	
SYSTEMD_PACKAGES = "lsbscripts"
SYSTEMD_SERVICE_${PN} = "firstrun.service ssh.service					\
			 mongod.service basecon.service					\
			 zipgateway.service gpio.service odroid-auto-bridge.service	\
			 zware-service.service zwarelocal-install.service 		\
			 listener.service						\
			"

do_install () {
	install -d ${D}${sysconfdir}/init.d

	install -d ${D}${sysconfdir}/systemd
	install -d ${D}${sysconfdir}/systemd/system
	install -d ${D}${sysconfdir}/systemd/network
	install -d ${D}${base_libdir}/lsb
	install -d ${D}${base_libdir}/lsb/init-functions.d
	install -d ${D}${base_libdir}/udev/
	install -d ${D}${base_libdir}/udev/rules.d
	install -d ${D}/home/
	install -d ${D}/home/marty/scripts

	install -m 0755 ${S}/firstrun ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/reboot ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/resize2fs ${D}${sysconfdir}/init.d/
	install -m 0644 ${S}/mongodb.env ${D}${sysconfdir}/
	install -m 0644 ${S}/mongodb.conf ${D}${sysconfdir}/
	install -m 0755 ${S}/mongod ${D}${sysconfdir}/init.d/

	install -m 0755 ${S}/cpufrequtils ${D}${sysconfdir}/init.d/
	install -m 0655 ${S}/init-functions ${D}${base_libdir}/lsb/
	install -m 0655 ${S}/init-functions.d/20-left-info-blocks ${D}${base_libdir}/lsb/init-functions.d/
	install -m 0655 ${S}/init-functions.d/40-systemd ${D}${base_libdir}/lsb/init-functions.d/
	#install -m 0644 ${S}/wired.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${S}/bridge.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${S}/usbnet.network ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${S}/firstrun.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/ssh.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/mongod.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/70-usb-modeswitch.rules ${D}${base_libdir}/udev/rules.d/
	install -m 0644 ${S}/mongod.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/basecon.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/zipgateway.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/gpio.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/odroid-auto-bridge.service ${D}${sysconfdir}/systemd/system/
        install -m 0644 ${S}/zwarelocal-install.service ${D}${sysconfdir}/systemd/system/
	install -m 0755 ${S}/zwarelocal-install.sh ${D}/home/marty/
	install -m 0644 ${S}/zware-service.service ${D}${sysconfdir}/systemd/system/
	install -m 0644 ${S}/python-zip-listener.py ${D}/home/marty/scripts/
	install -m 0644 ${S}/listener.service ${D}${sysconfdir}/systemd/system/

	#chown -R marty:users ${D}/home/marty/

}

FILES_${PN} += " \
		/home/marty				\
		/home/marty/zwarelocal-install.sh	\
		${base_libdir}/lsb			\
		${base_libdir}/lsb/init-functions.d	\
		"
