# 6lbr border router recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 14.11.2016
#

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

DEPENDS =" bridge-utils ncurses bash"
S = "${WORKDIR}/git"
PV = "${BROUTER_VER}+git${SRCPV}"
6lbr = "${S}/examples/6lbr"

inherit update-rc.d update-alternatives

do_compile () {
	cd ${S}/examples/6lbr
	make LDFLAGS="${LDFLAGS} --sysroot=/opt/yocto/poky/build-odroid/tmp/sysroots/odroid-c2/" all &&
	cd ${6lbr}/plugins/dummy/
	make LDFLAGS="${LDFLAGS} --sysroot=/opt/yocto/poky/build-odroid/tmp/sysroots/odroid-c2/" &&
	cd ${6lbr}/plugins/lwm2m-client/
	make LDFLAGS="${LDFLAGS} --sysroot=/opt/yocto/poky/build-odroid/tmp/sysroots/odroid-c2/"
}


do_install () {
	install -dm 0755 ${D}${sysconfdir}/6lbr			
	install -dm 0755 ${D}${libdir}/6lbr/bin
	install -dm 0755 ${D}${libdir}/6lbr/plugins
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/init.d

	cp -r ${6lbr}/package/etc/6lbr/* ${D}${sysconfdir}/6lbr/
	cp -r  ${6lbr}/package/usr/lib/* ${D}${libdir}/

	install -m 0755 ${6lbr}/package/etc/init.d/* ${D}${sysconfdir}/init.d/
	install -m 0755 ${6lbr}/package/usr/bin/* ${D}${bindir}/
	install -m 0755	${6lbr}/bin/* ${D}${libdir}/6lbr/bin/
	install -m 0755 ${6lbr}/tools/nvm_tool ${D}${libdir}/6lbr/bin	
	install -m 0755 ${6lbr}/plugins/dummy/dummy.so ${D}${libdir}/6lbr/plugins/
	install -m 0755 ${6lbr}/plugins/lwm2m-client/lwm2m.so ${D}${libdir}/6lbr/plugins/
}

FILES_${PN} += " \
		${bindir} \
		${libdir}/6lbr	\
		${libdir}/6lbr/plugins	\
		${libdir}/6lbr/bin \
		"

COMPATIBLE_MACHINE = "(odroid-c2)"
INITSCRIPT_NAME = "6lbr"
INITSCRIPT_PARAMS = "start 39 S . stop 39 0 6 1 ."

