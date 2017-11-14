# runtime - marty functions recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 11.12.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

#SRCREV ?= "dfb96273af151934e16c37e838b529ceeb063987"

TAG ?= "0.9.9.3"
SRC_URI = "git://gitolite@redmine.kundoxt.de:/releases_boot.git;protocol=ssh;tag=${TAG}"
PR = "r0"

S = "${WORKDIR}/git"

RDEPENDS_marty ?= "expect bash perl"

#INSANE_SKIP_${PN} = "already-stripped"
#do_unpack[noexec] = "1"

do_install () {

	install -d -m 0755 ${D}/home/marty/
	cp -r ${S}/runtime ${D}/home/marty/
	#chown marty:marty -R ${D}/home/marty/runtime/
}

FILES_${PN} += " \
		/home/marty/runtime/		\
		"

