#!/usr/bin/env python
# -*- coding:utf-8 -*-

import sys
import os
import codecs
import platform
import subprocess

DIR_HOME = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))


def isWindow():
    return platform.system() == 'Windows'


if isWindow():
    codecs.register(lambda name: name == 'cp65001' and codecs.lookup('utf-8') or None)
    CLASS_PATH = "%s/lib/*" % DIR_HOME
else:
    CLASS_PATH = "%s/lib/*:." % DIR_HOME

LOGBACK_FILE = "%s/conf/logback.xml" % DIR_HOME

DEFAULT_JVM = "-Xms1g -Xmx1g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%s/log" % DIR_HOME

DEFAULT_PROPERTY_CONF = "-Dfile.encoding=UTF-8 -Dlogback.statusListenerClass=ch.qos.logback.core.status" \
                        ".NopStatusListener -Djava.security.egd=file:///dev/urandom  -Dlogback.configurationFile=%s" \
                        % LOGBACK_FILE

REMOTE_DEBUG_CONFIG = "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=9999"

ENGINE_COMMAND = "java -server %s %s -classpath %s com.fulln.me.class1031.options.CustomerOptions -mode acc -id 1 " \
                 "-job path" % (
                     DEFAULT_PROPERTY_CONF, DEFAULT_JVM, CLASS_PATH)

if __name__ == '__main__':
    print(CLASS_PATH)
    child_process = subprocess.Popen(ENGINE_COMMAND, shell=True)
    print(ENGINE_COMMAND)
    (stdout, stderr) = child_process.communicate()
    sys.exit(child_process.returncode)
