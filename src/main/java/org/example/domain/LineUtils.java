package org.example.domain;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.example.domain.geometry.Coordinate;


public class LineUtils {
  //точки по координате X где должен быть установлен центр окружности
  private static final int LEFT_EDGE_PROJECTION = 35;
  private static final int MIDDLE_PROJECTION = 80;
  private static final int RIGHT_EDGE_PROJECTION = 125;

  // Координаты начала и конца сторон прямоугольника по координатам
  private static final int START_COORDINATE_X = 10;
  private static final int END_COORDINATE_X = 150;
  private static final int START_COORDINATE_Y = 110;
  private static final int END_COORDINATE_Y = 500;

  //Максимально допустимый радиус окружности для центральной проекции. Что такое проекция в данном контексте?
  //Проекция это произвольная величина по координате X, по которой мы центрируем окружности,
  //что бы они у нас не были беспорядочно разбросаны по прямоугольнику. Как считаем?
  //Конечная точка координаты X - Начальная точка координаты X - 1. Отнимаем один что бы окружность
  //не налезала на края прямоугольника
  //TODO исправить доку
  private static final int MAX_ALLOWABLE_RADIUS = (END_COORDINATE_X - START_COORDINATE_X) / 2 - 1;

  private static final int MAX_ALLOWABLE_RADIUS_FOR_LEFT_SIDE_PROJECTION =
      LEFT_EDGE_PROJECTION - START_COORDINATE_X - 1;

  private static final int MAX_ALLOWABLE_RADIUS_FOR_RIGHT_SIDE_PROJECTION =
      END_COORDINATE_X - RIGHT_EDGE_PROJECTION + START_COORDINATE_X - 1;

  private static final int SIDE_WITH_LEAST_AVAILABLE_RADIUS =
      Integer.min(MAX_ALLOWABLE_RADIUS_FOR_LEFT_SIDE_PROJECTION, MAX_ALLOWABLE_RADIUS_FOR_RIGHT_SIDE_PROJECTION);

  private static final int SIDE_WITH_GREATEST_AVAILABLE_RADIUS =
      Integer.max(MAX_ALLOWABLE_RADIUS_FOR_LEFT_SIDE_PROJECTION, MAX_ALLOWABLE_RADIUS_FOR_RIGHT_SIDE_PROJECTION);

  private final Queue<Coordinate> availableCoordinates = new ArrayDeque<>();
  private List<Integer> availableXProjection = new ArrayList<>();

  public void choseAvailableXProjection(int radius) {
    if (radius > MAX_ALLOWABLE_RADIUS) {
      throw new RuntimeException(""); //TODO add msg
    }

    if (radius > SIDE_WITH_GREATEST_AVAILABLE_RADIUS) {
      availableXProjection = List.of(MIDDLE_PROJECTION);
      return;
    }
    if (radius > SIDE_WITH_LEAST_AVAILABLE_RADIUS) {
      availableXProjection = new ArrayList<>(List.of(
          Integer.max(LEFT_EDGE_PROJECTION, RIGHT_EDGE_PROJECTION),
          MIDDLE_PROJECTION
      ));
      availableXProjection.sort(Integer::compareTo);
    }
    if (radius <= SIDE_WITH_LEAST_AVAILABLE_RADIUS) {
      availableXProjection = List.of(
        LEFT_EDGE_PROJECTION,
        MIDDLE_PROJECTION,
        RIGHT_EDGE_PROJECTION
      );
    }
  }

  public List<SvgCircle> prepareSvgCircles(Inlet inlet) {
    calculateAvailablePoints(inlet.getRadius());
    if (availableCoordinates.size() < inlet.getCount()) {
      throw new RuntimeException("The number of shapes exceeds the number of allowed coordinates");
    }
    List<SvgCircle> result = new ArrayList<>();

    int inletCount = inlet.getCount();
    while (inletCount >= 1) {
      Coordinate coordinate = availableCoordinates.remove();
      result.add(new SvgCircle(
          "Circle_" + inletCount,
          inlet,
          Color.GRAY,
          coordinate.x,
          coordinate.y
      ));
      inletCount--;
    }
    return result;
  }

  private int calculateMinYCoordinate(int radius) {
    return START_COORDINATE_Y + radius + 5;
  }

  private int calculateMaxYCoordinate(int radius) {
    return END_COORDINATE_Y - radius - 5;
  }

  private int getNextYCoordinate(int x1, int x2, int minY, int radius) {
    int diameter = 2 * radius;
    int distance = diameter + 4;
    int squareDistance = distance * distance;
    int squareX1X2Difference = (x1 - x2) * (x1 - x2);
    int squareDistanceMinusSquareX1X2Diff = squareDistance - squareX1X2Difference;

    return (int) Math.sqrt(squareDistanceMinusSquareX1X2Diff < 0 ? squareDistanceMinusSquareX1X2Diff * -1 : squareDistanceMinusSquareX1X2Diff) + minY;
  }

  private void calculateAvailablePoints(int radius) {
    int minY = calculateMinYCoordinate(radius);
    int maxY = calculateMaxYCoordinate(radius);
    this.choseAvailableXProjection(radius);

    while (minY < maxY) {
      if (availableXProjection.size() == 1) {
        int availableProjection = availableXProjection.get(0);
        availableCoordinates.add(Coordinate.builder()
            .x(availableProjection)
            .y(minY)
            .build());
        minY = this.getNextYCoordinate(availableProjection, availableProjection, minY, radius);
      }
      if (availableXProjection.size() == 2) {
        for (int i : availableXProjection) {
          availableCoordinates.add(Coordinate.builder()
              .x(i)
              .y(minY)
              .build());
          minY = this.getNextYCoordinate(availableXProjection.get(0), availableXProjection.get(1), minY, radius);
          if (minY > maxY) break;
        }
      }
      if (availableXProjection.size() >= 3) {
        int nextY = this.getNextYCoordinate(availableXProjection.get(0), availableXProjection.get(1), minY, radius);
        for (int i = 0; i < availableXProjection.size(); i++) {
          availableCoordinates.add(Coordinate.builder()
              .x(availableXProjection.get(i))
              .y(i % 2 != 0 ? nextY : minY)
              .build());
        }
        minY = minY + radius * 2 + 4;
      }
    }
  }

}
