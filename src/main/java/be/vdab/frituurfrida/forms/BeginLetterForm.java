package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public record BeginLetterForm(@NotBlank @NotEmpty String beginletters) {
}
