# wiringpi2 recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 16.10.2017

HOMEPAGE = "https://projects.drogon.net/raspberry-pi/wiringpi/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://wiringpi.tar"

S = "${WORKDIR}/"

INSANE_SKIP_${PN} = "installed-vs-shipped"

do_install () {

	install -dm 0600 ${D}/home/marty/wiringpi
	cp -r ${S}/wiringpi/* ${D}/home/marty/wiringpi/
}

FILES_${PN} +=	"	\
		/home/marty/wiringpi	\
		"

COMPATIBLE_MACHINE = "odroid-c2"

