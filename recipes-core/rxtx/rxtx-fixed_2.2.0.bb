# rxtx - Java serial lib recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 19.10.2017

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
#LICENSE = "RXTXv2.1"
#LIC_FILES_CHKSUM = "file://COPYING;md5=32303a23463f90b12a7d1dafb8deabf4"

SRC_URI += "				\
	   file://rxtx-2.2.0.tar	\
	   "

S = "${WORKDIR}"

INSANE_SKIP_${PN} = "dev-so staticdev already-stripped" 

do_install(){

	install -dm 0600 ${D}${libdir}
	install -dm 0600 ${D}${datadir}

	cp -r ${S}/usr/lib/* ${D}${libdir}
	cp -r ${S}/usr/share/* ${D}${datadir}
}

FILES_${PN} += " \
		${libdir} 	\
		${datadir} 	\
	       "


