# RSMB recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 14.11.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# LICENSE = "MIT"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"

PR = "r0"
SRCREV ?= "465b33afb8811f001499e11993a85e3b7dc19147"
SRC_URI = "git://gitolite@redmine.kundoxt.de:/rsmb.git;protocol=ssh"
SRC_URI += "file://Makefile.patch 	\
	    file://route.sh 		\
	    file://broker_cfg.patch	\
	   "

RSMB_VER = "1.3"
RDEPENDS_rsmb ?= "bash"
##DEPENDS =" bridge-utils ncurses "
S = "${WORKDIR}/git"


INSANE_SKIP_${PN} = "ldflags already-stripped split-strip"
# ERROR_QA ?= "split-strip"

do_compile () {
	
	# oe_runmake GCC="${CC}"
	make GCC="${CC}"  
}

do_install () {

	install -d -m 755 ${D}/home/
	install -d -m 755 ${D}/home/marty
	install -m 755 ${WORKDIR}/route.sh ${D}/home/marty/route.sh
	install -d -m 755 ${D}/home/marty/rsmb/
	cp -r ./. ${D}/home/marty/rsmb/
        chown -R marty:marty ${D}/home/marty
        
}

FILES_${PN} += " \
		/home/marty/		\
		/home/marty/rsmb/*	\
		/home/marty/rsmb/.git/*	\
		/home/marty/rsmb/.pc/*	\
		/home/marty/rsmb/.pc/.quilt_patches	\
		/home/marty/rsmb/.pc/.version		\
		/home/marty/rsmb/.pc/.quilt_series	\
		/home/marty/rsmb/.gitignore		\
	       "


