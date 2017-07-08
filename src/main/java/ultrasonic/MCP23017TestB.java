package ultrasonic;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
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
        final MCP23017GpioProvider provider = new MCP23017GpioProvider(I2CBus.BUS_1, 0x21);
        final GpioPinDigitalOutput burgerPin = gpio.provisionDigitalOutputPin(provider, MCP23017Pin.GPIO_B0, PinState.LOW);
        GpioPinDigitalInput input = gpio.provisionDigitalInputPin(provider, MCP23017Pin.GPIO_A0);
        input.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if(event.getState().isHigh()){
                    burgerPin.high();
                }else burgerPin.low();
                System.out.println(event.getEdge().getValue());
            }
        });

    }
}
