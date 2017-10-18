# GPIO_SERVICE recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 13.10.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

SRC_URI += "				\
	   file://gpioRun.py		\
	   file://misc			\
	   file://gpio			\
	   file://gpio.service		\
	   "


S = "${WORKDIR}"

#INSANE_SKIP_${PN} = "useless-rpaths rpaths"

do_install(){

	install -d -m 0600 ${D}/home/marty
	install -d -m 0600 ${D}/home/marty/gpio_service

	cp ${S}/gpioRun.py ${D}/home/marty/gpio_service
	cp -r ${S}/gpio ${D}/home/marty/gpio_service/
	cp -r ${S}/misc ${D}/home/marty/gpio_service/

}

FILES_${PN} += " \
		/home/marty/gpio_service 	\
		/home/marty/gpio_service/gpio 	\
		/home/marty/gpio_service/misc	\
	       "


