# wiringpi2 recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 16.10.2017

HOMEPAGE = "https://projects.drogon.net/raspberry-pi/wiringpi/"
SECTION = "devel/libs"
#LICENSE = "LGPLv3+"
#LIC_FILES_CHKSUM = "file://README;md5=995ad8cec39ff3c8187fd7ea069e94f8"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS_${PN} = "python"
PR = "r0"

SRCREV = "620be8c3264ed23a7d45d0a473ffeec3c2a4bc5e"

SRC_URI = "git://github.com/Gadgetoid/WiringPi2-Python.git"
SRC_URI += "				\
	   file://Makefile.patch	\
	   file://wiringPi.c.patch	\
	   "

SRC_URI[md5sum] = "dbcfff0b6dfc9bc4671a50c2fa4754bc"
SRC_URI[sha256sum] = "320c6a9c8f126d2cb4dde642c5e6249b046ffb2e817190216e8d51ad6f341875"


COMPATIBLE_MACHINE = "odroid-c2"

S = "${WORKDIR}/git"

inherit setuptools

# need to export these variables for python-config to work
#export BUILD_SYS
#export HOST_SYS
#export STAGING_INCDIR
#export STAGING_LIBDIR

#BBCLASSEXTEND = "native"


#do_install_append() {
#  rm -f ${D}${libdir}/python*/site-packages/site.py*
#}
