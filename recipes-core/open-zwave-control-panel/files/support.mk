diff --git a/cpp/build/support.mk b/cpp/build/support.mk
index 3f74c7e..66dc42f 100644
--- a/cpp/build/support.mk
+++ b/cpp/build/support.mk
@@ -43,6 +43,15 @@ endif
# version number to use on the shared library
VERSION := $(VERSION_MAJ).$(VERSION_MIN)

+# using setting from bitbake
+ifeq ($(BITBAKE_ENV),1)
+CC := $(CC)
+CXX := $(CXX)
+LD := $(CXX)
+AR := $(AR)
+RANLIB := $(RANLIB)
+else
+
# support Cross Compiling options
ifeq ($(UNAME),FreeBSD)
# Actually hide behind c++ which works for both clang based 10.0 and earlier(?)
@@ -61,6 +70,8 @@ else
AR := $(CROSS_COMPILE)ar rc
RANLIB := $(CROSS_COMPILE)ranlib
endif
+
+endif
SED := sed

@@ -96,9 +107,15 @@ else
pkgconfigdir ?= $(shell pkg-config --variable pc_path pkg-config | awk '{split(
endif

+ifeq ($(BITBAKE_ENV),1)
+sysconfdir := $(PREFIX)/etc/openzwave/
+includedir := $(PREFIX)/include/openzwave/
+docdir := $(PREFIX)/share/doc/openzwave-$(VERSION).$(VERSION_REV)
+else
sysconfdir ?= $(PREFIX)/etc/openzwave/
includedir ?= $(PREFIX)/include/openzwave/
docdir ?= $(PREFIX)/share/doc/openzwave-$(VERSION).$(VERSION_REV)
+endif

top_builddir ?= $(CURDIR)
export top_builddir
