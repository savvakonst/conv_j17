package org.example.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "circle")
public class Circle {

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  private String id;
  @JacksonXmlProperty(isAttribute = true, localName = "cx")
  private Integer cx;
  @JacksonXmlProperty(isAttribute = true, localName = "cy")
  private Integer cy;
  @JacksonXmlProperty(isAttribute = true, localName = "r")
  private Integer radius;
  @JacksonXmlProperty(isAttribute = true, localName = "fill")
  private String color;

  public Circle(String id, Inlet inlet, Color color, Integer cx, Integer cy) {
    this.id = id;
    this.color = "#" + Integer.toHexString(color.getRGB()).substring(2);
    this.radius = inlet.getRadius();
    this.cx = cx;
    this.cy = cy;
  }
}
