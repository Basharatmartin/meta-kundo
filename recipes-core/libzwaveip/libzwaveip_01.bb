# libzwaveip recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 19.07.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"
#BRANCH ?= "master"
#SRCREV ?= "2c140d3fbea8ec4b6672283a47757850bc2b9b6b"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/danieldeusing/libzwaveip.git" 
# SRC_URI += "				\
#	   file://zipgateway.service 	\
#	   "

DEPENDS +=" paho-mqtt libbsd libusb1 openssl python-native"
# RDEPENDS_${PN} ?= "bash"

S = "${WORKDIR}/git/"
B = "${WORKDIR}/build/"

#B = "${WORKDIR}/build/"
#C = "${WORKDIR}/build/_CPack_Packages/Linux/DEB/zipgateway-..0-Linux-aarch64/"

inherit pkgconfig cmake setuptools

#SYSTEMD_PACKAGES = "zipgateway"
#SYSTEMD_SERVICE_${PN} = "zipgateway.service"

#INSANE_SKIP_${PN} = "useless-rpaths rpaths"

do_compile(){

	cd ${B}
	${MAKE}
}


do_install(){

	install -dm 0600 ${D}${sysconfdir}/systemd/system
	install -dm 0600 ${D}${sbindir}
	install -dm 0600 ${D}${libdir}
	install -dm 0644 ${D}${prefix}/local/libzwaveip

	install -m 0655 ${B}/reference_listener ${D}${sbindir}
	install -m 0655 ${B}/reference_client ${D}${sbindir}
	install -m 0644 ${B}/ZWave_custom_cmd_classes.xml ${D}${prefix}/local/libzwaveip/
	install -m 0655 ${B}/examples/reference_apps/tokquote/test_tokquote ${D}${prefix}/local/libzwaveip/
	
	install -m 0644 ${B}/libzwaveip/libzwaveip.a ${D}${libdir}
	 
}

FILES_${PN} += " \
		${prefix} 	\
		${sbindir} 	\
	       "


