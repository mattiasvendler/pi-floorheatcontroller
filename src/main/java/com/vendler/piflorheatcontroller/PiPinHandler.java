package com.vendler.piflorheatcontroller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.pi4j.io.gpio.PinState.HIGH;
import static com.pi4j.io.gpio.RaspiPin.*;

@Component
public class PiPinHandler {
    final GpioController gpioController = GpioFactory.getInstance();
    GpioPinDigitalOutput pin[];
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public PiPinHandler() {
        pin = new GpioPinDigitalOutput[8];
        pin[0] = gpioController.provisionDigitalOutputPin(GPIO_00, "Pin0", HIGH);
        pin[1] = gpioController.provisionDigitalOutputPin(GPIO_01, "Pin1", HIGH);
        pin[2] = gpioController.provisionDigitalOutputPin(GPIO_02, "Pin2", HIGH);
        pin[3] = gpioController.provisionDigitalOutputPin(GPIO_03, "Pin3", HIGH);
        pin[4] = gpioController.provisionDigitalOutputPin(GPIO_04, "Pin4", HIGH);
        pin[5] = gpioController.provisionDigitalOutputPin(GPIO_05, "Pin5", HIGH);
        pin[6] = gpioController.provisionDigitalOutputPin(GPIO_06, "Pin6", HIGH);
        pin[7] = gpioController.provisionDigitalOutputPin(GPIO_07, "Pin7", HIGH);

        for (int i =0 ; i< pin.length; i++) {
            logger.info(String.format("Configuring pin %s", pin[i].getName()));
            pin[i].setPullResistance(PinPullResistance.PULL_DOWN);
            pin[i].low();
        }
    }

    public synchronized void high(int pinNumber) {

        if(pinNumber < pin.length) {
            if (pin[pinNumber].isHigh()) {
                return;
            }
            logger.info(String.format("Pin %s is set to high. PinNummer %d",pin[pinNumber].getName(), pinNumber));
            pin[pinNumber].high();
        }else {
            logger.warn(String.format("Trying to set pin high to a pin that do not exists %d", pinNumber));
        }
    }

    public synchronized void low(int pinNumber) {
        if (pinNumber < pin.length) {
            if (pin[pinNumber].isLow()) {
                return;
            }
            logger.info(String.format("Pin %s is set to low. PinNumber %d", pin[pinNumber].getName(), pinNumber));
            pin[pinNumber].low();
        } else {
            logger.warn(String.format("Trying to set pin low to a pin that do not exists %d", pinNumber));
        }
    }
}
