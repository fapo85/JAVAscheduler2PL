package scheduler2PL;

public class Anweisung
{
    private final AnweisungsTyp typ;
    private final Process process;
    private final Variable variable;
    private Status status = Status.OPEN;

    public Anweisung(String anweisung)
    {
        typ = parseAnweisungsTyp(anweisung);
        process = Process.getAProcess(parseProcessId(anweisung));
        if (typ == AnweisungsTyp.R | typ == AnweisungsTyp.W)
        {
            variable = parseVariable(anweisung);
        }
        else
        {
            variable = null;
        }
    }

    public static Variable parseVariable(String anweisung)
    {
        return Variable.getAVariable(anweisung.charAt(anweisung.length() - 2));
    }

    public static int parseProcessId(String anweisung)
    {
        return Integer.parseInt("" + anweisung.charAt(1));
    }

    public static AnweisungsTyp parseAnweisungsTyp(String anweisung)
    {
        switch (anweisung.toUpperCase().charAt(0))
        {
        case 'R':
            return AnweisungsTyp.R;
        case 'W':
            return AnweisungsTyp.W;
        case 'C':
            return AnweisungsTyp.C;
        default:
            System.err.println("Type nicht erkannt bei String: " + anweisung);
            return AnweisungsTyp.E;
        }
    }

    public AnweisungsTyp getAnweisungsTyp()
    {
        return typ;
    }

    public Process getProcess()
    {
        return process;
    }

    public Variable getVariable()
    {
        return variable;
    }

    @Override
    public String toString()
    {
        StringBuilder r = new StringBuilder();
        switch (typ)
        {
        case R:
            r.append("r");
            break;
        case W:
            r.append("w");
            break;
        case C:
            r.append("c");
            break;
        case E:
            return "Die Anweisung war Fehlerhaft.";
        }
        r.append(process.toString());
        if (variable != null)
        {
            r.append('(');
            r.append(variable.toString());
            r.append(')');
        }
        return r.toString();
    }

    public boolean work(boolean debugPrint)
    {
        if (status == Status.DONE)
        {
            return false;
        }
        return process.work(this, debugPrint);
    }

    public Status getStatus()
    {
        if (status == Status.DONE)
        {
            return Status.DONE;
        }
        return process.getStatus();
    }

    public void setDone()
    {
        status = Status.DONE;
    }

    public static String toStringArray(Anweisung[] anweisungen)
    {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < anweisungen.length; i++)
        {
            if (i != 0)
            {
                r.append(", ");
            }
            r.append(anweisungen[i].toString());
        }
        return r.toString();
    }

    public static void printArray(Anweisung[] anweisungen)
    {
        System.out.println(toStringArray(anweisungen));
    }
}
