package be.vdab.frituurfrida.sessions;

import java.io.Serial;
import java.io.Serializable;

//vergeet implement niet
public class Deur implements Serializable {
    //id
    private final int index;
    //toestand (neem hier true/false i.p.v. open/gesloten)
    private final boolean metFriet;
    //wordt geïnitialiseerd op false
    private boolean open;
    private static final long serialVersionUID = 1L;

    public Deur(int index, boolean metFriet) {
        this.index = index;
        this.metFriet = metFriet;
    }

    public void open() {
        open = true;
    }

    public int getIndex() {
        return index;
    }

    public boolean isMetFriet() {
        return metFriet;
    }

    public boolean isOpen() {
        return open;
    }
}
