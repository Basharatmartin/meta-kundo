# runtime - marty functions recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 11.12.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

#SRCREV ?= "b90502609da0209ede28adece0fde9cc809bc911"
SRCREV ?= "dfb96273af151934e16c37e838b529ceeb063987"
SRC_URI = "git://gitolite@redmine.kundoxt.de:/private.git;protocol=ssh"
PR = "r0"

S = "${WORKDIR}/git"

RDEPENDS_marty ?= "expect bash perl"

#INSANE_SKIP_${PN} = "already-stripped"
#do_unpack[noexec] = "1"

do_install () {

	install -d -m 755 ${D}${localstatedir}
	install -d -m 755 ${D}${localstatedir}/lock/

	#install -d -m 755 ${D}/home/marty
	#install -d -m 755 ${D}/home/marty/runtime_rel
	# install -d ${D}${base_libdir}/lsb
	#install -m 0755 ${S}/abc.txt ${D}${base_libdir}/lsb/
	#cp ${S}/marty.tar.gz ${D}${base_libdir}/lsb/
	#install -m 0666 ${S}/marty.tar.gz ${D}${base_libdir}/lsb/
	# tar czvf ${D}${base_libdir}/lsb/marty.tar.gz ./.
	# tar czvf ${D}/home/marty/marty.tar.gz ./.
	#addgroup marty
	cp -r ${S}/current/./.  ${D}/
	#cp ./.gitignore ${D}/home/marty/runtime/.gitignore
	rm ${D}/home/marty/runtime_rel/lib/native/librxtxSerial.so
	rm ${D}/home/marty/runtime_rel/lib/native/librxtxSerial-2.2pre1.so
	#chown marty:users -R ${D}/home/marty
	chmod 775 ${D}${localstatedir}/lock/
	#chgrp lock ${D}${localstatedir}/lock/
	
}

FILES_${PN} += " \
		/home/marty/runtime_rel/*		\
		/home/marty/runtime_rel/.settings/*	\
		/home/marty/runtime_rel/.idea/*		\
		/home/marty/runtime_rel/.gitignore	\
		/home/marty/runtime_rel/.classpath	\
		/home/marty/runtime_rel/.project	\
		/home/marty/runtime_rel/.factorypath	\
		/home/marty/runtime_rel/.idea/.name	\
		/run/lock/				\
		"

