import argparse
import time
import paho.mqtt.client as mqtt

def main(mqtt_ip):

    def on_connect(client, userdata, flags, rc):
        print("Connected with result code " + str(rc))

    def on_disconnect(client, userdata, rc):
        print("Disconnected with error code " + str(rc))

    client = mqtt.Client()
    client.on_connect = on_connect

    client.connect(mqtt_ip, 1883, 60)

    client.loop_start()

    def follow(thefile):
        thefile.seek(0, 2)
        while True:
            line = thefile.readline()
            if not line:
                time.sleep(0.1)
                continue
            yield line

    logfile = open("/tmp/zipgateway.log")
    loglines = follow(logfile)
    for line in loglines:
	 if "mqtt " in line:
            line = line.rstrip();
            s = line.split(" ")
            client.publish(s[1], s[2])

if __name__ == '__main__':
    main(mqtt_ip="localhost")
