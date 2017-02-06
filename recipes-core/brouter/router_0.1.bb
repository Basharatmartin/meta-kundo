# 6lbr border router recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 14.11.2016

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"
PR = "r0"

BRANCH ?= "6lbr1.4"
#SRCREV ?= "b976ce5907d8e6078e01aacab0ca14864aaa0f02"
#SRCREV ?= "425a62b53b35c7d1fc33300f6c6d226bd6205397"
#SRCREV ?= "f64e3bf8ee1a5b203b003fa032ef62cdfa9b1d86"
#SRCREV ?= "df556ea9f1fee24f795013b550b91e30187c89b9"
SRCREV ?= "f54509e71845f8c88c0e8b23cc616c54fed98837"

SRC_URI = "git://gitolite@redmine.kundoxt.de:/6lbr.git;protocol=ssh;branch=${BRANCH}"
SRC_URI += "file://Makefile.native.patch \
	    file://Makefile.cooja.patch \
	    file://Makefile.x86.patch \
	    file://Makefile.tools.patch \
	    file://nvm_tool \
	    file://6lbr.service \
	   "
BROUTER_VER = "1.4"

DEPENDS =" bridge-utils ncurses "
S = "${WORKDIR}/git"
PV = "${BROUTER_VER}+git${SRCPV}"
6lbr = "${S}/examples/6lbr"

INSANE_SKIP_${PN} = "already-stripped"

inherit systemd
SYSTEMD_PACKAGES = "router"
SYSTEMD_SERVICE_${PN} = "6lbr.service"

#SYSROOT="/opt/yocto/poky/build-odroid/tmp/sysroots/odroid-c2"
#PKG_CONFIG_SYSROOT_DIR="${SYSROOT}"
#PKG_CONFIG_LIBDIR="${SYSROOT}/usr/lib/pkgconfig"
#PKGCONFIG="${PKG_CONFIG_SYSROOT_DIR} ${PKG_CONFIG_LIBDIR}"
#CFLAGS="${PKGCONFIG}"
#LDFLAGS="${PKGCONFIG}"

do_compile () {
	cd ${6lbr}
	oe_runmake V=1 LDFLAGS+="${LDFLAGS} --sysroot=/opt/yocto/poky/build-odroid/tmp/sysroots/odroid-c2" all
}


do_install () {
	install -dm 0755 ${D}${sysconfdir}/6lbr			
	install -dm 0755 ${D}${libdir}/6lbr/bin
	install -dm 0755 ${D}${libdir}/6lbr/plugins
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/systemd
	install -d ${D}${sysconfdir}/systemd/system

	cp -r ${6lbr}/package/etc/6lbr/* ${D}${sysconfdir}/6lbr/
	cp -r ${6lbr}/package/etc/6lbr/6lbr.conf.rpi ${D}${sysconfdir}/6lbr/6lbr.conf
	cp -r  ${6lbr}/package/usr/lib/* ${D}${libdir}/

	install -m 0755 ${6lbr}/package/etc/init.d/* ${D}${sysconfdir}/init.d/
	install -m 0755 ${6lbr}/package/usr/bin/* ${D}${bindir}/
	install -m 0755	${6lbr}/bin/* ${D}${libdir}/6lbr/bin/
	cp ${WORKDIR}/nvm_tool ${D}${libdir}/6lbr/bin	

	install -m 0655 ${WORKDIR}/6lbr.service ${D}${sysconfdir}/systemd/system/	
}

FILES_${PN} += " \
		${bindir} \
		${libdir}/6lbr	\
		${libdir}/6lbr/bin \
		"

COMPATIBLE_MACHINE = "(odroid-c2)"
#INITSCRIPT_NAME = "6lbr"
#INITSCRIPT_PARAMS = "start 39 S 2 3 . stop 39 0 6 1 ."

