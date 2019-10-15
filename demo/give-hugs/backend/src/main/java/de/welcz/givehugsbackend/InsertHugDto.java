package de.welcz.givehugsbackend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
class InsertHugDto {
  @Schema(example = "Chris")
  String from;
}
