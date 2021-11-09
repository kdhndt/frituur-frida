package be.vdab.frituurfrida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@SessionScope
@Component
public class RaadDeSaus implements Serializable {
    private final static long serialVersionUID = 1L;
    private String saus;
    private StringBuilder puntjes;
    private int verkeerdePogingen;
    private final static int MAX_VERKEERDE_POGINGEN = 10;

    //reset method (her)start het spel en vraagt om een saus, zo kun je via je controller een willekeurige saus in je session steken
    public void reset(String saus) {
        this.saus = saus;
        puntjes = new StringBuilder(".".repeat(saus.length()));
        verkeerdePogingen = 0;
    }

    public void doeEenGok(Character letter) {
/*        var letterIndex = saus.indexOf(letter);
        //indexOf returnt -1 als er geen voorkomen is
        if (letterIndex == -1) {
            verkeerdePogingen++;
        } else {
            do {
                puntjes.setCharAt(letterIndex, letter);
                //indexOf geeft enkel het eerste voorkomen terug, dus ga op zoek achter extra voorkomens na de eerste
                letterIndex = saus.indexOf(letter, letterIndex + 1);
            //doe dit zolang er letters overeenkomen
            } while (letterIndex != -1);
        }*/
        if (saus.contains(Character.toString(letter))) {
            for (var i = 0; i != saus.length(); i++) {
                if (saus.charAt(i) == letter) {
                    puntjes.setCharAt(i, letter);
                }
            }
        } else {
            verkeerdePogingen++;
        }
    }

    public boolean isGewonnen() {
        //er zijn geen puntjes meer in je String betekent dat het woord geraden is
        return puntjes.indexOf(".") == -1;
    }

    public boolean isVerloren() {
        return verkeerdePogingen >= MAX_VERKEERDE_POGINGEN;
    }

    public String getSaus() {
        return saus;
    }

    //vorm hier puntjes om naar een String ipv meerdere keren in je test
    public String getPuntjes() {
        return puntjes.toString();
    }

    public int getVerkeerdePogingen() {
        return verkeerdePogingen;
    }


}
