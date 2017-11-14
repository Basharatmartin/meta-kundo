# zware-local recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 20.07.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "				\
	   file://zware-local.tar	\
	   "
S = "${WORKDIR}"

INSANE_SKIP_${PN} = "already-stripped "


do_install(){

	install -dm 0644 ${D}/home/marty
	cp -r ${S}/zwarelocal-aarch64 ${D}/home/marty/
}

FILES_${PN} += " \
		/home/marty/zwarelocal-aarch64 	\
	       "


