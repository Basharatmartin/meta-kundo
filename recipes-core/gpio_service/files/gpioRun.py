#!/usr/bin/env python
import json
import sys, signal, time
import misc.globvars as g
from gpio.gpioControl import OdroidGpio

__author__ = 'Boniface: kundoXT'

__default = {"events": "xx", "mqtt.ip": "localhost", "mqtt.port": "1883", "mqtt.topic": "xx", "gpio.pins": "xx", "gpio.time": "xx"}


def main(argv):
    try:
        with open('/home/marty/gpio_service/gpio/gpioControl.conf.json') as json_data:
            config_file = json.load(json_data)
        _gpio = OdroidGpio(config_file)
        _gpio.start()
    except ValueError:
        print 'gpioControl.conf.json requires JSON format'
        OdroidGpio(__default).send_error(".conf.json_ERROR")
        time.sleep(1)
        sys.exit(0)


def signal_handler(signal, frame):
    print('Quit. Closing Threads...')
    g.EXITFLAG = True
    sys.exit(0)


if __name__ == "__main__":
    signal.signal(signal.SIGINT, signal_handler)
    main(sys.argv[1:])
    while True:
        time.sleep(0.1)
