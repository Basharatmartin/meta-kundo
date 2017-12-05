#!/usr/bin/python
import subprocess, threading, time
import paho.mqtt.client as mqtt
import wiringpi2 as gpio
import globvars as g


class OdroidGpio(threading.Thread):

    def __init__(self, config_file):
        threading.Thread.__init__(self)
        self._mqttc = mqtt.Client("mqttGPIOs")
        self.config_file = config_file
        self.service_leds = [0, 2, 3, 4]    # 0=Blue, 2=Green, 3=Yellow, 4=Red
        self.mqtt_status = False
        self.gpio_init()
        self.start_time, self.stop_time, self.elapsed_time, self.pressed = (0, 0, 0, 0)
        self.mqtt_received = ""

        self.event_map = {
            "service_button": self.service_mode
        }

    def gpio_init(self):
        '''
        Description: Initialize the GPIOs, service_leds|service_button
        :return:
        '''
        print ('gpio: Initializing GPIOs')
        # setup wiringPi2
        gpio.wiringPiSetup()
        # service_button input direction
        gpio.pinMode(self.config_file['buttons']['service_button']['gpio'], 0)
        # service_mode_led output direction
        for i in range(len(self.service_leds)):
            gpio.pinMode(self.service_leds[i], 1)
        # turn off service_mode_led
        print ('gpio: Turn-off LEDs: {}'.format(self.service_leds))
        for i in range(len(self.service_leds)):
            gpio.digitalWrite(self.service_leds[i], 0)
        # turn on betrieb led
        print ('gpio: Turn-on Betrieb LED: {}'.format(self.service_leds[1]))
        gpio.digitalWrite(self.service_leds[1], 1)

    def mqtt_connect(self):
        '''
        Description: MQTT establishes connection
        :return:
        '''
        print ('gpio: Initiating MQTT connection')
        self._mqttc.on_message = self.mqtt_on_message
        self._mqttc.on_connect = self.mqtt_on_connect
        self._mqttc.on_subscribe = self.mqtt_on_subscribe
        self._mqttc.on_disconnect = self.mqtt_on_disconnect
        self._mqttc.connect(self.config_file["mqtt.ip"], self.config_file["mqtt.port"], 60)
        self._mqttc.subscribe(self.config_file["mqtt.subscribe"] + "#")
        self._mqttc.loop_start()
        self.mqtt_status = True

    def run(self):
        '''
        Description: - Establish MQTT Connection
                     - Check Service Button is pressed or not
                        - if Service Button is pressed or deactivate service mode is received, then Manage remoteAccess
                     - on-exit, close MQTT Connection
        :return:
        '''
        self.mqtt_connect()
        time.sleep(1)
        print ('gpio: Entering waiting state for incoming messages')

        while not g.exit_flag:
            time.sleep(0.3)
            for event in self.config_file["events"]:
                if event in self.event_map:
                    func = self.event_map.get(event)
                    if hasattr(func, '__call__'):
                        func()
                        self.remote_access()
        print ('gpio: Processing a stop signal')
        self.close()

    def service_mode(self):
        '''
        Description: Monitor Service Button. Get the number of times pressed and the elapsed time
        :return:
        '''
        service_button = self.config_file['buttons']['service_button']['gpio']
        time_format = self.config_file["buttons"]['service_button']["timeFormat"]

        # check if service button is pressed
        if gpio.digitalRead(service_button) == 0:
            print ('gpio: Service mode button: {} pressed detected'.format(service_button))
            self.pressed += 1

            # get start time
            self.start_time = time.time()
            # wait until the service button is released
            while gpio.digitalRead(service_button) == 0:
                pass

            # get end time
            self.stop_time = time.time()
            # elapsed time in seconds or milliseconds
            self.elapsed_time = self.stop_time - self.start_time if time_format == "sec" else int((self.stop_time - self.start_time)*1000)
            print ('gpio: Elapsed time: {}{}'.format(self.elapsed_time, time_format))

    def close(self):
        '''
        Description: stop mqtt loop and disconnect from broker
        :return:
        '''
        if self.mqtt_status:
            self._mqttc.disconnect()

    def mqtt_on_connect(self, mqttc, obj, flags, rc):
        print ("gpio: mqtt connected with result code " + str(rc))

    def mqtt_on_disconnect(self, client, userdata, rc):
        if rc != 0:
            print ("gpio: mqtt unexpected disconnection with error code " + str(rc))
        else:
            print ("gpio: mqtt disconnected with error code " + str(rc))
        self._mqttc.loop_stop()

    def mqtt_on_message(self, mqttc, obj, msg):
        '''
        Description: MQTT callback function on received messages equal to subscription
        :param mqttc:
        :param obj:
        :param msg: topic, payload
        :return:
        '''
        print ("gpio: mqtt received. topic={}, payload={}" .format(msg.topic, msg.payload))
        try:
            if int(msg.payload) in range(2) and str(msg.payload).isdigit():
                self.mqtt_received = msg.topic + " " + msg.payload
                # turn on service led
                if (self.config_file["mqtt.subscribe"] + "0") in msg.topic:
                    gpio.digitalWrite(self.service_leds[0], int(msg.payload))
                # turn on other leds
                elif int(str(msg.topic)[-1]) in range(2, 5):
                    gpio.digitalWrite(self.service_leds[int(str(msg.topic)[-1])-1], int(msg.payload))
            else:
                raise Exception('Payload not a digit')
        except Exception as e:
            print e

    def mqtt_on_subscribe(self, mqttc, obj, mid, granted_qos):
        pass

    def remote_access(self):
        '''
        Description: Manage remoteAccess
            Service Button pressed 1x = activate remoteAccess'  -> enable-start-verify, MQTT publish topic+duration
            Service Button pressed 2x = deactivate remoteAccess -> stop-disable-verify, MQTT publish topic+duration, and/or deactivate service mode
        :return:
        '''
        try:
            topic = (self.config_file["mqtt.publish"] + str(self.config_file['buttons']['service_button']['gpio']))

            # pressed 1x = activate ssh, MQTT publish button pressed
            if self.pressed == 1:
                print ("gpio: Enabling remote access")
                subprocess.call("/bin/sh /home/marty/gpio_service/src/remoteAccess.sh enable", shell=True)
                subprocess.call("/bin/sh /home/marty/gpio_service/src/remoteAccess.sh start", shell=True)
                ret = subprocess.call("/bin/sh /home/marty/gpio_service/src/remoteAccess.sh verify", shell=True)

                # MQTT publish button pressed
                self._mqttc.publish(topic=topic, payload=self.elapsed_time)
                self.pressed += 1
            # pressed 2x = deactivate ssh, or deactivate service mode received
            elif self.pressed == 3 or self.mqtt_received == '/native/in/gpio/value/kxt/plain/0 0':
                print ("gpio: Disabling remote access")
                subprocess.call("/bin/sh /home/marty/gpio_service/src/remoteAccess.sh stop", shell=True)
                subprocess.call("/bin/sh /home/marty/gpio_service/src/remoteAccess.sh disable", shell=True)
                ret = subprocess.call("/bin/sh /home/marty/gpio_service/src/remoteAccess.sh verify", shell=True)

                # deactivate service mode received
                if self.mqtt_received != '/native/in/gpio/value/kxt/plain/0 0':
                    # MQTT publish button pressed
                    self._mqttc.publish(topic=topic, payload=self.elapsed_time)

                # reset button clicks counter and mqtt previous received
                self.pressed, self.mqtt_received = (0, '')
            else:
                ret = ''
            return ret
        except Exception as e:
            print e

