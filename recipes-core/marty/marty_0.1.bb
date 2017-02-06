# runtime - marty functions recipe for yocto compiled image for Odroid C2
# Created: Basharat Martin
# Date: 11.12.2016
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b2276b027815460f098d51494e2ff4f1"
SRCREV ?= "b90502609da0209ede28adece0fde9cc809bc911"
SRC_URI = "git://gitolite@redmine.kundoxt.de:/runtime.git;protocol=ssh"
PR = "r0"

#SRC_URI = "\
#	    file://marty.tar.gz \
#	   "


S = "${WORKDIR}/git"

# DEPENDS = "expect"
RDEPENDS_marty ?= "expect bash"

inherit useradd
# inherit extrausers

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "-g 880 sudo"
USERADD_PARAM_${PN} = "-u 1200 -G dialout -G sudo -N -m -d /home/marty -r -s /bin/bash -P 'martymarty' marty"
# USERADD_PARAM_${PN} = "-u 1000 -d /home/marty -s /bin/bash marty"
# USERMOD_PARAM_${PN} += "-aG sudo marty \
#			-aG dialout marty \	
#		       "

# INHERIT += "extrausers"
# EXTRA_USERS_PARAMS = "\
#		usermod -aG sudo marty;\
#		usermod -aG dialout marty;\
#		"

#INSANE_SKIP_${PN} = "already-stripped"

#do_unpack[noexec] = "1"

do_install () {

	install -d -m 755 ${D}/home/
	install -d -m 755 ${D}/home/marty
	install -d -m 755 ${D}/home/marty/runtime
	# install -d ${D}${base_libdir}/lsb
	#install -m 0755 ${S}/abc.txt ${D}${base_libdir}/lsb/
	#cp ${S}/marty.tar.gz ${D}${base_libdir}/lsb/
	#install -m 0666 ${S}/marty.tar.gz ${D}${base_libdir}/lsb/
	# tar czvf ${D}${base_libdir}/lsb/marty.tar.gz ./.
	# tar czvf ${D}/home/marty/marty.tar.gz ./.
	cp -r ./. ${D}/home/marty/runtime/
	#cp ./.gitignore ${D}/home/marty/runtime/.gitignore
	rm ${D}/home/marty/runtime/lib/native/librxtxSerial.so
	rm ${D}/home/marty/runtime/lib/native/librxtxSerial-2.2pre1.so
	chown -R marty:marty ${D}/home/marty
	
}

FILES_${PN} += " \
		#${base_libdir}/lsb	\
		/home/marty/runtime/*	\
		/home/marty/runtime/.git/*	\
		/home/marty/runtime/.settings/*	\
		/home/marty/runtime/.idea/*	\
		/home/marty/runtime/.gitignore	\
		/home/marty/runtime/.classpath	\
		/home/marty/runtime/.project	\
		/home/marty/runtime/.factorypath	\
		/home/marty/runtime/.idea/.name	\
		"

