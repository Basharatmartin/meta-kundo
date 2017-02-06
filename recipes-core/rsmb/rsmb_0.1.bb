# 6lbr border router recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 14.11.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"

PR = "r0"

SRCREV ?= "465b33afb8811f001499e11993a85e3b7dc19147"

SRC_URI = "git://gitolite@redmine.kundoxt.de:/rsmb.git;protocol=ssh"


RSMB_VER = "1.3"

##DEPENDS =" bridge-utils ncurses "
S = "${WORKDIR}/git"


#INSANE_SKIP_${PN} = "already-stripped"

#inherit update-rc.d update-alternatives



do_compile () {
	
	oe_runmake GCC="${CC}"
}


#FILES_${PN} += " \
#		${bindir} \
#		${libdir}/6lbr	\
#		${libdir}/6lbr/bin \
#		"

COMPATIBLE_MACHINE = "(odroid-c2)"
