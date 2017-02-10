# meta-kundo :

this is for odroid-c2
for test purpose !

Building the Yocto Qemu Image

follow the steps please

On Debian / Ubuntu

* Requirements:

1. 	sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib  \
	build-essential chrpath socat libsdl1.2-dev xterm make \
	xsltproc docbook-utils fop dblatex xmlto autoconf automake libtool \
	libglib2.0-dev python-gtk2 bsdmainutils screen


* Prepare things for Yocto Qemu Image

2.	sudo install -o $(id -u) -g $(id -g) -d /opt/yocto
	cd /opt/yocto 
	git clone --branch (morty or Krogoth) git://git.yoctoproject.org/poky
	cd /opt/yocto/poky
	git clone --branch ytram https://github.com/Basharatmartin/meta-kundo.git (recipes by KundoXT)
	source oe-init-build-env qemuarm

3. 	cd /opt/yocto/poky/qemuarm
	add the following line into the file ./conf/bblayers.conf
	/opt/yocto/poky/meta-kundo \ (in this way, you can use the recipes from kundoxt e.g runtime, brouter/router, zwave etc)
	

	

