package be.vdab.frituurfrida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Random;
import java.util.random.RandomGeneratorFactory;

@Component
@SessionScope
public class ZoekDeFriet implements Serializable {
    //start met kleine letter
    private static final long serialVersionUID = 1L;
    private static final int AANTAL_DEUREN = 7;
    private final Deur[] deuren = new Deur[AANTAL_DEUREN];

    public ZoekDeFriet() {
        //start het spel op wanneer de ZoekDeFriet bean geïnjecteerd wordt
        reset();
    }

    public void reset() {
        var indexMetFriet = new Random().nextInt(AANTAL_DEUREN);
        for (var index = 0; index != AANTAL_DEUREN; index++) {
            //als index == willekeurig getal, zet dan de "metFriet" toestand naar true
            deuren[index] = new Deur(index, index == indexMetFriet);
        }
    }

    public void openDeur(int index) {
        deuren[index].open();
    }

    public Deur[] getDeuren() {
        return deuren;
    }
}
