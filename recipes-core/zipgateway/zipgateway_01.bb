# zipgateway recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 10.07.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"
BRANCH ?= "zipgateway_boni"
SRCREV ?= "0b3c2eccbf9863370cd49ca1f6f1cff585146930"

SRC_URI = "git://gitolite@redmine.kundoxt.de:/zipgateway.git;protocol=ssh;branch=${BRANCH}"
SRC_URI += "				\
	   file://zipgateway.service 	\
	   "
DEPENDS +=" libusb1 openssl python-native "
RDEPENDS_${PN} ?= "bash"

S = "${WORKDIR}/git/zipgateway-2.61.0-Source/usr/local"
B = "${WORKDIR}/build/"
C = "${WORKDIR}/build/_CPack_Packages/Linux/DEB/zipgateway-..0-Linux-aarch64/"

inherit pkgconfig cmake setuptools systemd
SYSTEMD_PACKAGES = "zipgateway"
SYSTEMD_SERVICE_${PN} = "zipgateway.service"

INSANE_SKIP_${PN} = "useless-rpaths rpaths"

do_compile(){

	cd ${B}
	${MAKE}
	make package
}

do_install(){

	install -dm 0600 ${D}${sysconfdir}/init.d
	install -dm 0600 ${D}${sysconfdir}/systemd/system

	install -dm 0600 ${D}${sbindir}
	install -dm 0644 ${D}${prefix}/local/etc
	install -dm 0644 ${D}${prefix}/local/man/man3
	install -dm 0644 ${D}${prefix}/local/zipgw-scripts
	install -dm 0644 ${D}${prefix}/local/zipgw-scripts/ifdown.d
	install -dm 0644 ${D}${prefix}/local/zipgw-scripts/ifup.d

	cp -r ${C}/usr/sbin/* ${D}${sbindir}
	cp -r ${C}/usr/etc/* ${D}${prefix}/local/etc/
	cp -r ${C}/usr/local/man/* ${D}${prefix}/local/man/
	cp -r ${C}/etc/* ${D}${sysconfdir}/	

	cp ${WORKDIR}/git/Z-Wave_Odroidc2_Binaries/usr/local/zipgw-scripts/ifup.d/50bridge.sh ${D}${prefix}/local/zipgw-scripts/ifup.d/
	cp ${WORKDIR}/git/Z-Wave_Odroidc2_Binaries/usr/local/zipgw-scripts/ifdown.d/50bridge.sh ${D}${prefix}/local/zipgw-scripts/ifdown.d/	

	install -m 0644 ${WORKDIR}/zipgateway.service ${D}${sysconfdir}/systemd/system/
}

FILES_${PN} += " \
		${prefix} 	\
		${sbindir} 	\
	       "

