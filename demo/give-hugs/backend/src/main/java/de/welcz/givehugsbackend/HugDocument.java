package de.welcz.givehugsbackend;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
  ObjectId id;
  String from;
  LocalDateTime timeStamp;
}
