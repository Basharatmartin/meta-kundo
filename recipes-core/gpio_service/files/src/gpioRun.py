#!/usr/bin/env python
import json, signal, sys, time
from gpioControl import OdroidGpio
import globvars as g


def main(argv):
    try:
        print ('\ngpio: Starting... GPIO SERVICE')
        with open('/home/marty/gpio_service/lib/gpioControl.conf.json') as json_data:
            print ('gpio: Opening... config file: ../gpioControl.conf.json')
            config_file = json.load(json_data)
        gpio = OdroidGpio(config_file)
        print('gpio: starting main Thread')
        gpio.start()
    except (IOError, OSError) as e:
        print(e)
        sys.exit(0)


def graceful_exit(signum, frame):
    print ('gpio: Quit. Closing Threads with signum {}'.format(signum))
    g.exit_flag = True
    sys.exit(0)


if __name__ == "__main__":

    signal.signal(signal.SIGINT, graceful_exit)
    signal.signal(signal.SIGTERM, graceful_exit)

    main(sys.argv)

    while True:
        time.sleep(0.5)
