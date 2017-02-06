# lsb-init functions recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 11.12.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"
PR = "r0"

SRC_URI += "file://init-functions \
	    file://init-functions.d/20-left-info-blocks \
	    file://init-functions.d/40-systemd \
	    file://cpufrequtils \
<<<<<<< HEAD
	    file://wired.network \
	    file://bridge.network \
	    file://usbnet.network \
	    file://firstrun \
	    file://reboot \
	    file://resize2fs \
	    file://firstrun.service \
	    file://lte_log \
	    file://lte_log.service \
	    file://70-usb-modeswitch.rules \
=======
>>>>>>> ytram
	   "

S = "${WORKDIR}"

<<<<<<< HEAD
inherit systemd	
SYSTEMD_PACKAGES = "lsbscripts"
SYSTEMD_SERVICE_${PN} = "firstrun.service lte_log.service"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	#install -d ${D}${sysconfdir}/rcS.d
	#install -d ${D}${sysconfdir}/rc1.d

	install -d ${D}${sysconfdir}/systemd
	install -d ${D}${sysconfdir}/systemd/system
	install -d ${D}${sysconfdir}/systemd/network
	install -d ${D}${base_libdir}/lsb
	install -d ${D}${base_libdir}/lsb/init-functions.d
	install -d ${D}${base_libdir}/udev/
	install -d ${D}${base_libdir}/udev/rules.d


	install -m 0755 ${S}/firstrun ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/reboot ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/resize2fs ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/lte_log ${D}${sysconfdir}/init.d/

	install -m 0655 ${S}/cpufrequtils ${D}${sysconfdir}/init.d/
	install -m 0655 ${S}/init-functions ${D}${base_libdir}/lsb/
	install -m 0655 ${S}/init-functions.d/20-left-info-blocks ${D}${base_libdir}/lsb/init-functions.d/
	install -m 0655 ${S}/init-functions.d/40-systemd ${D}${base_libdir}/lsb/init-functions.d/
	install -m 0655 ${S}/wired.network ${D}${sysconfdir}/systemd/network/
	install -m 0655 ${S}/bridge.network ${D}${sysconfdir}/systemd/network/
	install -m 0655 ${S}/usbnet.network ${D}${sysconfdir}/systemd/network/
	install -m 0755 ${S}/firstrun.service ${D}${sysconfdir}/systemd/system/
	install -m 0755 ${S}/lte_log.service ${D}${sysconfdir}/systemd/system/
	install -m 0655 ${S}/70-usb-modeswitch.rules ${D}${base_libdir}/udev/rules.d/

	#ln -sf ../init.d/firstrun  ${D}${sysconfdir}/rcS.d/S90firstrun
	#ln -sf ../init.d/firstrun  ${D}${sysconfdir}/rc1.d/K90firstrun
	#ln -sf ../init.d/firstrun  ${D}${sysconfdir}/rc2.d/S90firstrun
	#ln -sf ../init.d/firstrun  ${D}${sysconfdir}/rc3.d/S90firstrun
	#ln -sf ../init.d/firstrun  ${D}${sysconfdir}/rc4.d/S90firstrun
	#ln -sf ../init.d/firstrun  ${D}${sysconfdir}/rc5.d/K90firstrun
=======
do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${base_libdir}/lsb
	install -d ${D}${base_libdir}/lsb/init-functions.d

	install -m 0755 ${S}/cpufrequtils ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/init-functions ${D}${base_libdir}/lsb/
	install -m 0755 ${S}/init-functions.d/20-left-info-blocks ${D}${base_libdir}/lsb/init-functions.d/
	install -m 0755 ${S}/init-functions.d/40-systemd ${D}${base_libdir}/lsb/init-functions.d/
	
>>>>>>> ytram
}

FILES_${PN} += " \
		${base_libdir}/lsb	\
		${base_libdir}/lsb/init-functions.d \
		"
<<<<<<< HEAD
#INITSCRIPT_PACKAGES = "lsbscripts"
#INITSCRIPT_NAME = "firstrun"
#INITSCRIPT_PARAMS = "defaults"
=======

>>>>>>> ytram
