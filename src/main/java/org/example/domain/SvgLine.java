package org.example.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "line", namespace = "line")
public class SvgLine {

  @JacksonXmlProperty(isAttribute = true)
  private int x1;
  @JacksonXmlProperty(isAttribute = true)
  private int x2;
  @JacksonXmlProperty(isAttribute = true)
  private int y1;
  @JacksonXmlProperty(isAttribute = true)
  private int y2;
  @JacksonXmlProperty(isAttribute = true)
  private String stroke;
  @JacksonXmlProperty(isAttribute = true, localName = "marker-start")
  private Marker markerStart;
  @JacksonXmlProperty(isAttribute = true, localName = "marker-end")
  private Marker markerEnd;
}
