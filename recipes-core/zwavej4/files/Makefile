diff --git a/cpp/build/Makefile b/cpp/build/Makefile
index a632f79..31af00a 100644
--- a/cpp/build/Makefile
+++ b/cpp/build/Makefile
@@ -93,6 +93,10 @@ else
hidapi := $(notdir $(wildcard $(top_srcdir)/cpp/hidapi/linux/*.c)) # we do not
endif

+ifeq ($(BITBAKE_ENV),1)
+ar_option := "rc"
+endif
+
cclasses := $(notdir $(wildcard $(top_srcdir)/cpp/src/command_classes/.cpp))
vclasses := $(notdir $(wildcard $(top_srcdir)/cpp/src/value_classes/.cpp))
pform := $(notdir $(wildcard $(top_srcdir)/cpp/src/platform/*.cpp)) \
@@ -139,7 +143,7 @@ $(LIBDIR)/libopenzwave.a: $(patsubst %.cpp,$(OBJDIR)/%.o,$
$(patsubst %.cpp,$(OBJDIR)/%.o,$(indep)) \
$(OBJDIR)/vers.o
@echo "Linking Static Library"

    @$(AR) $@ $+

      @$(AR) $(ar_option) $@ $+
    @$(RANLIB) $@

    $(LIBDIR)/$(SHARED_LIB_NAME): $(patsubst %.cpp,$(OBJDIR)/%.o,$(tinyxml)) \

