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
	   "

S = "${WORKDIR}"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${base_libdir}/lsb
	install -d ${D}${base_libdir}/lsb/init-functions.d

	install -m 0755 ${S}/cpufrequtils ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/init-functions ${D}${base_libdir}/lsb/
	install -m 0755 ${S}/init-functions.d/20-left-info-blocks ${D}${base_libdir}/lsb/init-functions.d/
	install -m 0755 ${S}/init-functions.d/40-systemd ${D}${base_libdir}/lsb/init-functions.d/
	
}

FILES_${PN} += " \
		${base_libdir}/lsb	\
		${base_libdir}/lsb/init-functions.d \
		"

