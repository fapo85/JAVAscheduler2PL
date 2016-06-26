/**
 * 
 */
package scheduler2PL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Fabian Spottog www.f-a-p-o.de
 */
public class Main
{

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        boolean weiter = true;
        do
        {
            Scheduler scheduler = null;
            String str = readline();
            switch (str.trim().toLowerCase())
            {
            case "exit":
                weiter = false;
                break;
            case "aufgabe4":
            case "aufgabe 4":
                scheduler = new Scheduler(Initials.A4);
                break;
            default:
                scheduler = new Scheduler(str);
            }
            if (scheduler != null)
            {
                System.out.println("wird zu:");
                scheduler.printScheduler();
            }
        }
        while (weiter);
    }

    public static String readline() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("nächster Scheduler: ");
        return br.readLine();
    }

}
