# zware recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 20.07.2017

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"
BRANCH ?= "zipgateway_boni"
#SRCREV ?= "edf4b4c4e505891d1876e2adfad8bfdd0dcbd9e6"
#SRCREV ?= "afb156fb27e55edf4eb5d9126afc5106df1ad3b3"

#SRC_URI = "git://gitolite@redmine.kundoxt.de:/zipgateway.git;protocol=ssh;branch=${BRANCH}"
SRC_URI += "				\
	   file://zware_portal.tar.gz \
	   "

#DEPENDS +=" libusb1 openssl python-native "
DEPENDS +=" bash "
RDEPENDS_${PN} ?= "bash"

S = "${WORKDIR}"
#B = "${WORKDIR}/build/"
#C = "${WORKDIR}/build/_CPack_Packages/Linux/DEB/zipgateway-..0-Linux-aarch64/"

#inherit setuptools
#SYSTEMD_PACKAGES = "zipgateway"
#SYSTEMD_SERVICE_${PN} = "zipgateway.service"

#INSANE_SKIP_${PN} = "useless-rpaths rpaths"


#do_configure(){
#
#	#export CC="$CC" 
#	#export CXX="$CXX"
#
#	#cp ${WORKDIR}/config.cfg ${S}
#	#cp ${WORKDIR}/config.cfg ${S}/config_local/
#	
#	#./build/build.sh local odroidc2 release noparallel build 
#	#cd ${B}
#	#${MAKE}
#	#make package
#}

#do_install(){
#
#	install -dm 0600 ${D}${sysconfdir}/init.d
#	install -dm 0600 ${D}${sysconfdir}/systemd/system
#
#	install -dm 0600 ${D}${sbindir}
#	install -dm 0644 ${D}${prefix}/local/etc
#	install -dm 0644 ${D}${prefix}/local/man/man3
#	install -dm 0644 ${D}${prefix}/local/zipgw-scripts
#	install -dm 0644 ${D}${prefix}/local/zipgw-scripts/ifdown.d
#	install -dm 0644 ${D}${prefix}/local/zipgw-scripts/ifup.d
#
#	cp -r ${C}/usr/sbin/* ${D}${sbindir}
#	cp -r ${C}/usr/etc/* ${D}${prefix}/local/etc/
#	cp -r ${C}/usr/local/man/* ${D}${prefix}/local/man/
#	cp -r ${C}/etc/* ${D}${sysconfdir}/	
#
#	cp ${WORKDIR}/git/Z-Wave_Odroidc2_Binaries/usr/local/zipgw-scripts/ifup.d/50bridge.sh ${D}${prefix}/local/zipgw-scripts/ifup.d/
#	cp ${WORKDIR}/git/Z-Wave_Odroidc2_Binaries/usr/local/zipgw-scripts/ifdown.d/50bridge.sh ${D}${prefix}/local/zipgw-scripts/ifdown.d/	
#
#	#	install -m 0644 ${WORKDIR}/zipgateway.service ${D}${sysconfdir}/systemd/system/
#}
#
#FILES_${PN} += " \
#		${prefix} 	\
#		${sbindir} 	\
#	       "
#

