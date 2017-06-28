
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

        sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_DOWN); // Echo pin as INPUT
        final  GpioPinDigitalOutput burgerPin = gpio.provisionDigitalOutputPin(provider, MCP23017Pin.GPIO_A0,"A0");
        while(true){
            try {
                Thread.sleep(500);
                sensorTriggerPin.high(); // Make trigger pin HIGH
                java.util.concurrent.TimeUnit.MICROSECONDS.sleep(10); // Delay for 10 microseconds
                sensorTriggerPin.low(); //Make trigger pin LOW

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH

                }
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW

                }
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.

                double distance = ((((endTime-startTime)/1e3)/2) / 29.1);
                System.out.println("Distance :"+ distance +" cm"); //Printing out the distance in cm

                if(distance<30)  burgerPin.high();
                else burgerPin.low();
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}