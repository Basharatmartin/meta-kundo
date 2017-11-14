# GPIO_SERVICE recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 13.10.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

SRC_URI += "				\
	   file://src			\
	   file://lib			\
	   "

S = "${WORKDIR}"

#INSANE_SKIP_${PN} = ""

do_install(){

	install -d -m 0600 ${D}/home/marty
	install -d -m 0600 ${D}/home/marty/gpio_service

	cp -r ${S}/src ${D}/home/marty/gpio_service
	cp -r ${S}/lib ${D}/home/marty/gpio_service/
}

FILES_${PN} += " \
		/home/marty/gpio_service 	\
		/home/marty/gpio_service/src 	\
		/home/marty/gpio_service/lib	\
	       "


