package scheduler2PL;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Variable implements Comparable<Variable>
{
    private final char var;
    public static Set<Variable> liste = new HashSet<>();
    public Lock lock = new Lock();

    public Variable(char caracter)
    {
        var = caracter;
    }

    public static Variable getAVariable(char caracter)
    {

        liste.add(new Variable(caracter));
        Iterator<Variable> it = liste.iterator();
        while (it.hasNext())
        {
            Variable curent = it.next();
            if (curent.equals(caracter))
            {
                return curent;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.valueOf(var);
    }

    @Override
    public boolean equals(Object arg)
    {
        if (arg instanceof Variable)
        {
            return var == ((Variable) arg).var;
        }
        else
        {
            return ((Character) var).equals(arg);
        }
    }

    public static int getLengt()
    {
        return liste.size();
    }

    @Override
    public int compareTo(Variable o)
    {
        if (this.equals(o))
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }

    @Override
    public int hashCode()
    {
        return var;
    }

    public static void removeLocks(Process process)
    {
        Iterator<Variable> it = liste.iterator();
        while (it.hasNext())
        {
            Variable curent = it.next();
            curent.lock.remove(process);
        }
    }

}
