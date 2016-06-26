package scheduler2PL;

public class Scheduler
{
    private Anweisung[] anweisungen;

    public Scheduler(String s)
    {
        this(s.split(" "));
    }

    public Scheduler(String[] sa)
    {
        anweisungen = new Anweisung[sa.length];
        for (int i = 0; i < sa.length; i++)
        {
            anweisungen[i] = new Anweisung(sa[i]);
        }
        Anweisung.printArray(anweisungen);
    }

    public Scheduler(Anweisung[] anweisungen)
    {
        this.anweisungen = anweisungen;
        Anweisung.printArray(anweisungen);
    }

    public static String sts(Status s)
    {
        switch (s)
        {
        case DONE:
            return "DONE";
        case OPEN:
            return "OPEN";
        case LOCKED:
            return "LOCKED";
        default:
            return "UNKNOW";
        }
    }

    public String getScheduler(boolean debugPrint)
    {
        boolean etwasgetan;
        StringBuilder done = new StringBuilder();
        weiter: do
        {
            etwasgetan = false;
            for (int i = 0; i < anweisungen.length; i++)
            {
                if (anweisungen[i].work(debugPrint))
                {
                    if (done.length() > 1)
                    {
                        done.append(", ");
                    }
                    done.append(anweisungen[i].toString());
                    etwasgetan = true;
                    continue weiter;
                }
                else
                {
                    Status s = anweisungen[i].getStatus();
                    if (s != Status.DONE)
                    {
                        if (debugPrint)
                        {
                            done.append("Verschoben: " + anweisungen[i].toString() + " Status: " + sts(s) + "\n");
                        }
                    }
                }
            }
        }
        while (etwasgetan);
        return done.toString();
    }

    public void printScheduler(boolean debugPrint)
    {
        System.out.println(getScheduler(debugPrint));
    }

    public void printScheduler()
    {
        printScheduler(false);
    }
}
