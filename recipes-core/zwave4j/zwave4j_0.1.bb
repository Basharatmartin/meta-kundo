# 6lbr border router recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 14.11.2016
#

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d15a25c00014e4b6d8250230ac3a293e"

SRC_URI = "git://github.com/zgmnkv/zwave4j.git"
#SRC_URI += "file://gradle.properties \
#	    file://build.gradle	 \
#	   "
SRC_URI += "file://libzwave4j.so	 \
	   "

SRCREV ?= "10a4abb455f88dcefdf5b3b0794f45796949776b"

S = "${WORKDIR}/git"
PV = "1.0+git${SRCPV}"

INSANE_SKIP_${PN} = "already-stripped"

do_compile () {
	# cp ${WORKDIR}/gradle.properties .
	#./gradlew build
	:
}

do_install () {

	install -dm 0755 ${D}${libdir}
	install -dm 0755 ${D}${libdir}/jni/

	install -m 0755 ${WORKDIR}/libzwave4j.so ${D}${libdir}/jni/
}

FILES_${PN} += " \
                ${libdir}		\
		${libdir}/jni/*		\
					\
		" 
