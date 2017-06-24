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

        while(true){
            try {
                Thread.sleep(1000);
                sensorTriggerPin.high(); // Make trigger pin HIGH
                java.util.concurrent.TimeUnit.MICROSECONDS.sleep(10); // Delay for 10 microseconds
                sensorTriggerPin.low(); //Make trigger pin LOW

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH

                }
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW

                }
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.

                System.out.println("Distance :"+((((endTime-startTime)/1e3)/2) / 29.1) +" cm"); //Printing out the distance in cm
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}