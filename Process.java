package scheduler2PL;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Process implements Comparable<Process>
{
    int id;
    public static Set<Process> liste = new HashSet<>();
    private Status status = Status.OPEN;
    Anweisung lock;

    public Process(int id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object arg)
    {
        if (arg instanceof Process)
        {
            return id == ((Process) arg).id;
        }
        else
        {
            return ((Integer) id).equals(arg);
        }
    }

    @Override
    public int compareTo(Process o)
    {
        return id - o.id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return String.valueOf(id);
    }

    public static Process getAProcess(int id)
    {

        liste.add(new Process(id));
        Iterator<Process> it = liste.iterator();
        while (it.hasNext())
        {
            Process curent = it.next();
            if (curent.equals(id))
            {
                return curent;
            }
        }
        return null;
    }

    public static int getLength()
    {
        return liste.size();
    }

    public boolean work(Anweisung anweisung, boolean debugPrint)
    {
        if (status == Status.DONE)
        {
            return false;
        }
        if (status == Status.LOCKED && lock != anweisung)
        {
            return false;
        }
        switch (anweisung.getAnweisungsTyp())
        {
        case C:
            Variable.removeLocks(this);
            done(anweisung, debugPrint);
            status = Status.DONE;
            return true;
        case R:
            if (!anweisung.getVariable().lock.ReadLock(this))
            {
                lock(anweisung);
                return false;
            }
            else
            {
                done(anweisung, debugPrint);
                return true;
            }
        case W:
            if (!anweisung.getVariable().lock.WriteLock(this))
            {
                lock(anweisung);
                return false;
            }
            else
            {
                done(anweisung, debugPrint);
                return true;
            }
        case E:
            break;
        default:
            break;
        }
        return true;
    }

    private void done(Anweisung anweisung, boolean debugPrint)
    {
        status = Status.OPEN;
        anweisung.setDone();
        if (debugPrint)
        {
            System.out.println(anweisung);
        }
    }

    private void lock(Anweisung anweisung)
    {
        status = Status.LOCKED;
        lock = anweisung;
    }

    public Status getStatus()
    {
        return status;
    }

}
