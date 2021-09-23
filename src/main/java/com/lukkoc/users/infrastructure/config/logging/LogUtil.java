package com.lukkoc.users.infrastructure.config.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

class LogUtil {

  static Map<String, String> getHeadersFromRequest(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String, String> headerMap = new LinkedHashMap<>();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = request.getHeader(headerName);
      headerMap.put(headerName, headerValue);
    }
    return headerMap;
  }

  static Map<String, String> getHeadersFromResponse(HttpServletResponse response) {
    Collection<String> headerNames = response.getHeaderNames();
    Map<String, String> headerMap = new LinkedHashMap<>();
    headerNames.forEach(headerName -> {
      String headerValue = response.getHeader(headerName);
      headerMap.put(headerName, headerValue);
    });
    return headerMap;
  }

  static Map<String, String> getFeignHeaders(Map<String, Collection<String>> headers) {
    return headers.entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));
  }

}
