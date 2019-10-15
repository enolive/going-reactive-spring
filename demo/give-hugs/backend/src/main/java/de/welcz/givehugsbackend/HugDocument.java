package de.welcz.givehugsbackend;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "hugs")
@Value
class HugDocument {
  @JsonSerialize(using = ToStringSerializer.class)
  @Id
  @Schema(type = "string", example = "5da5a36fadf59351d2ca2469")
  ObjectId id;
  @Schema(example = "Chris")
  String from;
  LocalDateTime timeStamp;
}
