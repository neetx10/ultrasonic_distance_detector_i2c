package ultrasonic;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

/**
 * Created by navdeep on 8/7/17.
 */
public class MCP23017TestB {

    final static GpioController gpio = GpioFactory.getInstance();

    public static void main(String[] args) throws Exception{
        new MCP23017TestB().run();
    }

    public void run() throws IOException, I2CFactory.UnsupportedBusNumberException {
        final MCP23017GpioProvider provider = new MCP23017GpioProvider(I2CBus.BUS_1, 0x20);
        final GpioPinDigitalOutput burgerPin = gpio.provisionDigitalOutputPin(provider, MCP23017Pin.GPIO_B0, PinState.HIGH);
        burgerPin.high();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                burgerPin.low();
            }
        }).start();

    }
}
