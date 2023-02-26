package org.example.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "svg")
public class Figure {

  @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
  private final String xmlns = "http://www.w3.org/2000/svg";
  @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xlink")
  private final String xlink = "http://www.w3.org/1999/xlink";
  @JacksonXmlProperty(isAttribute = true)
  private Integer width;
  @JacksonXmlProperty(isAttribute = true)
  private Integer height;
  @JacksonXmlProperty(isAttribute = true)
  private String viewBox;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "line")
  private Collection<SvgLine> lines;
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "circle")
  private Collection<Circle> circles;
}
