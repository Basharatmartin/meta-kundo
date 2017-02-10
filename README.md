# meta-kundo :

this is for odroid-c2
for test purpose !

Building the Yocto Qemu Image

follow the steps please

On Debian / Ubuntu

* Requirements:

1. 	sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib 
	build-essential chrpath socat libsdl1.2-dev xterm make 
	xsltproc docbook-utils fop dblatex xmlto autoconf automake libtool 
	libglib2.0-dev python-gtk2 bsdmainutils screen


* Prepare things for Yocto Qemu Image

1.	sudo install -o $(id -u) -g $(id -g) -d /opt/yocto
2.	cd /opt/yocto 
3.	git clone --branch (morty or Krogoth) git://git.yoctoproject.org/poky
4.	cd /opt/yocto/poky
5.	git clone --branch ytram https://github.com/Basharatmartin/meta-kundo.git (recipes by KundoXT)
6.	source oe-init-build-env qemuarm

7. 	cd /opt/yocto/poky/qemuarm
8.	add the following line into the file ./conf/bblayers.conf
	/opt/yocto/poky/meta-kundo \ 
	(in this way, you can use the recipes from kundoxt e.g runtime, brouter/router, zwave etc)
	
9.	MACHINE=qemuarm bitbake core-image-minimal (If everything goes fine, you can see an SDcard image in the folder ./tmp/deploy/images/ )

	

