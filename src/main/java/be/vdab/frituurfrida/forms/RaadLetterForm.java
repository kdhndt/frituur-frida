package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotNull;

//char wordt in parameter als "Character" uitgedrukt, niet "char"
public record RaadLetterForm(@NotNull Character letter) {
}
