# 6lbr border router recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 14.11.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://license/Apache-License-2.0.txt;md5=5dbc052533cb6e0e47352828d40f42f2"
SRC_URI[md5sum] = "f2f6c51af1ef731f3215a4b4492addf1"

PR = "r0"

BRANCH ?= "1.4.6"
SRCREV ?= "b48d900e861b9bc002f29eaf99e66d29a1495d77"

SRC_URI = "git://github.com/OpenZWave/open-zwave.git"


ZWAVE_VER = "1.0"

DEPENDS ="libmicrohttpd"

S = "${WORKDIR}/git"
PV = "${BRANCH}+git${SRCPV}"


do_compile () {
	oe_runmake  BITBAKE_ENV=1 CC="${CC}" CXX="${CXX}" LD="${CXX}" AR="${AR}" RANLIB="${RANLIB}" 
}


do_install () {
	install -dm 0755 ${D}${sysconfdir}/openzwave
	install -dm 0755 ${D}${bindir}
	install -dm 0755 ${D}${libdir}
	

	install -m 0755 ${S}/MinOZW ${D}${bindir}/
	install -m 0755 ${S}/ozw_config ${D}${bindir}/
	install -m 0755 ${S}/libopenzwave.so.1.4 ${D}${libdir}/
	ln -sn ${D}${libdir}/libopenzwave.so.1.4 ${D}${libdir}/libopenzwave.so

	cp -r ${S}/config/* ${D}${sysconfdir}/openzwave/

}

FILES_${PN} += " \
		${bindir} \
		${libdir} \
		${sysconfdir}/openzwave \
		"






