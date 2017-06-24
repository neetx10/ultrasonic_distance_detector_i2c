package ultrasonic;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;


public class Main {
    //GPIO Pins
    private static GpioPinDigitalOutput sensorTriggerPin ;
    private static GpioPinDigitalInput sensorEchoPin ;


    final static GpioController gpio = GpioFactory.getInstance();

    public static void main(String [] args) throws InterruptedException, IOException, I2CFactory.UnsupportedBusNumberException {
        new Main().run();
    }
    public void run() throws InterruptedException, IOException, I2CFactory.UnsupportedBusNumberException {

        final MCP23017GpioProvider provider = new MCP23017GpioProvider(I2CBus.BUS_1, 0x20);

        sensorTriggerPin =  gpio.provisionDigitalOutputPin(provider, MCP23017Pin.GPIO_A0, "trigger"); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(provider, MCP23017Pin.GPIO_A1, "echo"); // Echo pin as INPUT

        sensorTriggerPin.high();
    }
}