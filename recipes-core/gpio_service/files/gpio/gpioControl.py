#!/usr/bin/python
import threading
import time
from misc import globvars as g
import paho.mqtt.client as mqtt
import wiringpi2 as gpio

__author__ = 'Boniface:kundoXT'

gpio.wiringPiSetup()
service_mode_button = 1
service_leds = [0, 2, 3, 4]    # rgb


class OdroidGpio(threading.Thread):

    def __init__(self, config_file):
        threading.Thread.__init__(self)
        self._mqttc = mqtt.Client("mqttGPIOs")
        self.config_file = config_file
        self.mqtt_ip = self.config_file["mqtt.ip"]
        self.mqtt_port = self.config_file["mqtt.port"]
        self.pub_topic = self.config_file["mqtt.publish"]
        self.sub_topic = self.config_file["mqtt.subscribe"]
        self.mqtt_events = self.config_file["events_list"]
        self.mqtt_status = False
        self.gpio_init()

        self.event_map = {
            "service_button": self.service_mode
        }

    @staticmethod
    def gpio_init():
        gpio.pinMode(service_mode_button, 0)       # service_mode_button input direction
        for i in range(len(service_leds)):
            gpio.pinMode(service_leds[i], 1)       # service_mode_led output direction
        for i in range(len(service_leds)):
            gpio.digitalWrite(service_leds[i], 0)  # turn off service_mode_led

        gpio.digitalWrite(service_leds[1], 1)     # turn on betrieb led

    def mqtt_connect(self):
        self._mqttc.on_message = self.mqtt_on_message
        self._mqttc.on_connect = self.mqtt_on_connect
        self._mqttc.on_subscribe = self.mqtt_on_subscribe
        self._mqttc.connect(self.mqtt_ip, self.mqtt_port, 60)
        self._mqttc.subscribe(self.sub_topic + "#")
        self._mqttc.loop_start()
        self.mqtt_status = True

    def run(self):
        # print "MQTT STARTED!"
        self.mqtt_connect()

        while not g.EXITFLAG:
            time.sleep(0.3)
            for _item in self.mqtt_events:
                if _item in self.event_map:
                    _service_button = self.config_file["buttons"][_item]["service.button"]
                    _time_format = self.config_file["buttons"][_item]["time.format"]

                    func = self.event_map[_item]
                    if hasattr(func, '__call__'):
                        func(_service_button, _time_format)
        self.close()

    def service_mode(self, _service_button, _time_format):
        if gpio.digitalRead(_service_button) == 0:
            start_time = time.time()    # get start time
            while gpio.digitalRead(_service_button) == 0:
                pass

            end_time = time.time()      # get end time
            elapse_time = end_time - start_time if _time_format == "sec" else int((end_time - start_time)*1000)
            self._mqttc.publish(topic=(self.pub_topic + str(_service_button)), payload=elapse_time)    # Publish to MQTT

    def close(self):
        self._mqttc.loop_stop()
        self._mqttc.disconnect()

    def send_error(self, msg):
        if not self.mqtt_status:
            self.mqtt_connect()
            time.sleep(1)   # wait for MQTT connection
        self._mqttc.publish(topic=self.pub_topic, payload=msg)

    def mqtt_on_connect(self, mqttc, obj, flags, rc):
        print "MQTT Connection established ..."

    def mqtt_on_message(self, mqttc, obj, msg):
        if int(msg.payload) in range(2) and str(msg.payload).isdigit():
            if (self.sub_topic + "0") in msg.topic:
                gpio.digitalWrite(service_leds[0], int(msg.payload))    # turn on service led
            elif int(int(str(msg.topic)[-1])) in range(2, 5):
                gpio.digitalWrite(service_leds[int(str(msg.topic)[-1])-1], int(msg.payload))    # turn on other leds

    def mqtt_on_subscribe(self, mqttc, obj, mid, granted_qos):
        pass
