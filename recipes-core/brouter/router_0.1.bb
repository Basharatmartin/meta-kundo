# 6lbr border router  
# 
#
#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
#SUMMARY = "BorderRouter"
#SECTION = "base"
#LICENSE = "MIT"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"


FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"
PR = "r0"

BRANCH ?= "6lbr1.4"
SRCREV ?= "04af8159f789bf44825f01d85d6d59e1f1dbe8ef"

SRC_URI = "git://gitolite@redmine.kundoxt.de:/6lbr.git;protocol=ssh;branch=${BRANCH}"
SRC_URI += "file://Makefile.native.patch \
	    file://Makefile.cooja.patch \
	    file://Makefile.x86.patch \
	   "

BROUTER_VER = "1.4"

DEPENDS ="bridge-utils ncurses"
#S = "${WORKDIR}/git/examples/6lbr"
S = "${WORKDIR}/git"

PV = "${BROUTER_VER}+git${SRCPV}"


inherit update-rc.d update-alternatives


do_compile() {
	cd ${S}/examples/6lbr
	make LDFLAGS="${LDFLAGS} --sysroot=/opt/yocto/poky/build-odroid/tmp/sysroots/odroid-c2/" all plugins tools
	make install && make plugins-install
}

COMPATIBLE_MACHINE = "(odroid-c2)"
INITSCRIPT_NAME = "6lbr"
INITSCRIPT_PARAMS = "start 39 S . stop 39 0 6 1 ."
