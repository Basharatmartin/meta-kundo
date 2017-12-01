# zipgateway recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 10.07.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"
BRANCH ?= "zipgateway_boni"
#SRCREV ?= "0b3c2eccbf9863370cd49ca1f6f1cff585146930"
#SRCREV ?= "afb156fb27e55edf4eb5d9126afc5106df1ad3b3"

#SRC_URI = "git://gitolite@redmine.kundoxt.de:/zipgateway.git;protocol=ssh;branch=${BRANCH}"
SRC_URI += "				\
	   file://zipgateway.tar	\
	   "


DEPENDS += "libusb1 openssl python-native"
RDEPENDS_${PN} ?= "bash useradd"

S = "${WORKDIR}/zipgateway"

#INSANE_SKIP_${PN} = ""

do_install(){

	install -dm 0600 ${D}${sysconfdir}/init.d

	install -dm 0600 ${D}${bindir}
	install -dm 0644 ${D}${prefix}/local/sbin
	install -dm 0644 ${D}${prefix}/local/etc
	install -dm 0644 ${D}${prefix}/local/man/man3

	cp -r ${S}/usr/local/sbin/* ${D}${prefix}/local/sbin
	cp -r ${S}/usr/local/etc/* ${D}${prefix}/local/etc/
	cp -r ${S}/usr/local/man/* ${D}${prefix}/local/man/

	#install -m 0644 -D ${S}/usr/local/sbin/* ${D}${prefix}/local/sbin
	#install -m 0644 -D ${S}/usr/local/etc/* ${D}${prefix}/local/etc/
	#install -m 0644 -D ${S}/usr/local/man/man3/*.* ${D}${prefix}/local/man/man3/

	#install -m 0644  ${S}/etc/init.d/zipgateway ${D}${sysconfdir}/init.d/	
	cp ${S}/etc/init.d/zipgateway ${D}${sysconfdir}/init.d/	

	#install -m 0666 ${S}/usr/bin/odroid-auto-bridge ${D}${bindir}
	cp ${S}/usr/bin/odroid-auto-bridge ${D}${bindir}
	#chown -R marty ${D}${prefix}/local
}

FILES_${PN} += " \
		${prefix} 	\
	       "


