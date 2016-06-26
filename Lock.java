package scheduler2PL;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Lock
{
    private Process writeLock = null;
    private Set<Process> readlocks = new HashSet<>();

    private boolean readisLocked()
    {
        return !readlocks.isEmpty();
    }

    private boolean readIsLockedFromOther(Process p)
    {
        if (!readisLocked())
        {
            return false;
        }
        Iterator<Process> it = readlocks.iterator();
        while (it.hasNext())
        {
            Process current = it.next();
            if (!current.equals(p))
            {
                return true;
            }
        }
        return false;
    }

    private boolean writeIsLocked()
    {
        return writeLock != null;
    }

    private boolean writeIsLockedFromOther(Process p)
    {
        if (!writeIsLocked())
        {
            return false;
        }
        return writeLock != p;
    }

    public synchronized void remove(Process p)
    {
        if (writeLock == p)
        {
            writeLock = null;
        }
        readlocks.remove(p);
    }

    public synchronized boolean WriteLock(Process p)
    {
        if (writeIsLockedFromOther(p))
        {
            return false;
        }
        else if (readIsLockedFromOther(p))
        {
            return false;
        }
        else
        {
            writeLock = p;
            return true;
        }
    }

    public synchronized boolean ReadLock(Process p)
    {
        if (writeIsLockedFromOther(p))
        {
            return false;
        }
        else
        {
            readlocks.add(p);
            return true;
        }
    }
}
