# SipCalc recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 11.08.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

#SRC_URI[md5sum] = "e431c64387f2c8d20e96ad1d7931a845"
#SRC_URI[sha256sum] = "cfd476c667f7a119e49eb5fe8adcfb9d2339bc2e0d4d01a1d64b7c229be56357"
#SRC_URI = "http://www.routemeister.net/projects/sipcalc/files/sipcalc-1.1.6.tar.gz"
#S = "${WORKDIR}/sipcalc-1.1.6"

#INSANE_SKIP_${PN} = "already-stripped"

SRCREV = "ac1dba1dcc033fe3541d7c61b3452d5090fbfc6c"
SRC_URI = "git://github.com/emagii/sipcalc.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools

#do_configure () {
#	sed -i '568s/ac_default_prefix/#ac_default_prefix/' ./configure
#	#./configure --prefix=${D}${prefix} --host='x86_64'
#}
#
#do_compile () {
#	oe_runmake GCC="${CC}" 
#}
#
#do_install () {
#
#	install -d ${D}${prefix}/bin
#	#oe_runmake install
#	install -m 0755 ${S}/src/sipcalc ${D}${prefix}/bin/sipcalc
#}
#
#FILES_${PN} += " \
#		${prefix}/bin \
#		"

COMPATIBLE_MACHINE = "(odroid-c2)"
