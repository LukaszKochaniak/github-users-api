package com.lukkoc.users.infrastructure.config.logging;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@Builder
@ToString
class MessageLog {
  String type;
  String method;
  String url;
  Map<String, String> headers;
  Integer status;
  String data;
  Long processTime;
}
