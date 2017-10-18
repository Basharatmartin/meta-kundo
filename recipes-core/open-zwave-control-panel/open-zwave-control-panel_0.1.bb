# Open-zwave-control-panel recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 22.02.2017
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://license/Apache-License-2.0.txt;md5=5dbc052533cb6e0e47352828d40f42f2"
SRC_URI[md5sum] = "f2f6c51af1ef731f3215a4b4492addf1"

PR = "r0"

BRANCH ?= "master"
SRCREV ?= "ba258fa43c5db2aa4a92bdabf693b9aadb488184"

SRC_URI = "git://github.com/OpenZWave/open-zwave-control-panel.git"
SRC_URI += "file://Makefile.patch \
	   "


ZWAVE_CON_VER = "1.0"

DEPENDS ="libmicrohttpd udev"
INSANE_SKIP_${PN} = "ldflags already-stripped split-strip"

S = "${WORKDIR}/git"
PV = "${BRANCH}+git${SRCPV}"


do_compile () {
	cd ${S}
	##oe_runmake  BITBAKE_ENV=1 CC="${CC}" CXX="${CXX}" LD="${CXX}" AR="${AR}" RANLIB="${RANLIB}" 
	make  BITBAKE_ENV=1 CC="${CC}" CXX="${CXX}" LD="${CXX}" AR="${AR}" RANLIB="${RANLIB}" 
}


do_install () {
	install -dm 0755 ${D}${sysconfdir}/openzwave-control
	install -dm 0755 ${D}${bindir}
#	install -dm 0755 ${D}${libdir}
	

	install -m 0755 ${S}/ozwcp ${D}${bindir}/
#	install -m 0755 ${S}/ozw_config ${D}${bindir}/
#	install -m 0755 ${S}/libopenzwave.so.1.4 ${D}${libdir}/

#	cp -r ${S}/config/* ${D}${sysconfdir}/openzwave/
#	cd  ${D}${libdir}/
	install -m 0755 ${S}/ozwcp ${D}${sysconfdir}/openzwave-control/
	ln -sd ../openzwave/config ${D}${sysconfdir}/openzwave-control/

}

FILES_${PN} += " \
		${bindir} \
		${sysconfdir}/openzwave-control \
		"

