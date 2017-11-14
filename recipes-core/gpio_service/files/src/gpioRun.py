#!/usr/bin/env python
import json, signal, sys, time
import globvars as g
from gpioControl import OdroidGpio


def main(argv):
    try:
        with open('/home/marty/gpio_service/lib/gpioControl.conf.json') as json_data:
            config_file = json.load(json_data)
        gpio = OdroidGpio(config_file)
        gpio.start()
    except (IOError, OSError) as e:
        print e
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
