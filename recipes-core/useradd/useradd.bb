# Useradd recipe for yocto compiled image for Odroid C2
# copied sample from meta yocto
# Created: Basharat Martin
# Date: 17.02.2017

SUMMARY = "inherit useradd"
DESCRIPTION = "This recipe serves as an example for using features from useradd.bbclass"
SECTION = "example"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://bashrc \
	   "

inherit useradd
USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "-g 880 sudo"
USERADD_PARAM_${PN} = "-u 1200 -G sudo -N -m -d /home/marty -r -s /bin/bash -P 'martymarty' marty"

do_install () {

        install -d -m 755 ${D}/home/
        install -d -m 755 ${D}/home/marty
	install -m 775 ${WORKDIR}/bashrc ${D}/home/marty/.bashrc
	chown -R marty:users ${D}/home/marty

}

FILES_${PN} += " \
                /home/marty/   \
                "

# INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
