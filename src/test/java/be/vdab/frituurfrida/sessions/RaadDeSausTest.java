package be.vdab.frituurfrida.sessions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


class RaadDeSausTest {

    private RaadDeSaus raadDeSaus;

    @BeforeEach
    public void beforeEach() {
        raadDeSaus = new RaadDeSaus();
        raadDeSaus.reset("lol");
    }

    @Test
    void bijEenResetBevatPuntjesEnkelPuntjes() {
//        System.out.println(raadDeSaus.getPuntjes());
        assertThat(raadDeSaus.getPuntjes()).isEqualTo("...");
        assertThat(raadDeSaus.getSaus()).isEqualTo("lol");
        assertThat(raadDeSaus.isGewonnen()).isFalse();
        assertThat(raadDeSaus.isVerloren()).isFalse();
        assertThat(raadDeSaus.getVerkeerdePogingen()).isZero();
    }
    @Test
    void JuisteLetterRaden() {
        raadDeSaus.doeEenGok('o');
        assertThat(String.valueOf(raadDeSaus.getPuntjes())).isEqualTo(".o.");
        assertThat(raadDeSaus.isGewonnen()).isFalse();
        assertThat(raadDeSaus.isVerloren()).isFalse();
        assertThat(raadDeSaus.getVerkeerdePogingen()).isZero();
    }

    @Test
    void VerkeerdeLetterRaden() {
        raadDeSaus.doeEenGok('a');
        assertThat(raadDeSaus.getVerkeerdePogingen()).isOne();
        assertThat(raadDeSaus.isGewonnen()).isFalse();
        assertThat(raadDeSaus.isVerloren()).isFalse();
    }

    @Test
    void TienFoutievePogingenIsEenVerliezer() {
        for (var poging = 1; poging <= 10; poging++) {
            raadDeSaus.doeEenGok('@');
        }
        assertThat(raadDeSaus.isVerloren()).isTrue();
        assertThat(raadDeSaus.isGewonnen()).isFalse();
    }

    @Test
    void GeenSterretjesMeerIsEenWinnaar() {
        raadDeSaus.doeEenGok('l');
        raadDeSaus.doeEenGok('o');
        assertThat(raadDeSaus.isGewonnen()).isTrue();
        assertThat(raadDeSaus.isVerloren()).isFalse();
    }

}
