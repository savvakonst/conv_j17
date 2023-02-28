package org.example;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.domain.SvgCircle;
import org.example.domain.Figure;
import org.example.domain.Inlet;
import org.example.domain.LineUtils;
import org.example.domain.SvgLine;
import org.example.domain.geometry.Axis;
import org.example.domain.geometry.Circle;
import org.example.domain.geometry.InletType;
import org.example.domain.geometry.Line;
import org.example.domain.geometry.SceneState;
import org.example.domain.geometry.SimpleStrategy;
import org.example.domain.geometry.Coordinate;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.*;

import java.util.LinkedList;
import java.util.Collection;
import java.util.List;

public class Main {
	private static final String MARKUP_PATH = "D:\\java_projects\\new\\conv_j17";
	private static final String FILE_NAME = "\\example.svg";
	private static final XmlMapper xmlMapper = new XmlMapper();

	public static void main(String[] args) throws FileNotFoundException {

		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);
			Collection<SvgLine> lines = figure.getLines();
			LinkedList<Line> ilines = new LinkedList<>();
			for (var l : lines)
				ilines.add(new Line(l.getX1(), l.getY1(), l.getX2(), l.getY2()));

			SceneState sceneState = new SceneState(List.of(35, 80, 125/**/), ilines);

			var inletType = new InletType("default inlet", 9, 2);
			
			var strategy = new SimpleStrategy();
			for(int k=0 ; k< 10; k++);
				sceneState.addCircle(inletType, strategy);

			
			draw(MARKUP_PATH + "\\result_" + ".svg",sceneState.getCircles(),null );
			drawAxis(sceneState);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// addInlet(Collections.singletonList(new Inlet(20, 20)));
	}

	private static void draw(String filename, Collection<Circle> circle, Axis []axes) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);

			if (axes != null) {
				List<SvgLine> axisX = new ArrayList<>();
				Arrays.stream(axes).forEach(axis -> { 
					var segments = axis.getSegments();
					int x = axis.getX();
					segments.forEach(s -> axisX.add(new SvgLine(x, x, s.getP1(), s.getP2(), "black", "3", null, null)));
				});
				figure.getLines().addAll(axisX);
			}
			
			
			if (circle != null) {
				List<SvgCircle> circleX = new ArrayList<>();
				circle.forEach(s -> circleX.add(new SvgCircle("",s.getRadius(),s.getP().x, s.getP().y,Color.GRAY)));
				figure.setCircles(circleX);
			}

			writeInFile(filename, figure);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private static void drawAxis(SceneState sceneState) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);

			sceneState.getAxesMap().forEach((k, v) -> { // iterate over the map in sceneState
				List<SvgLine> axisX = new ArrayList<>();
				Arrays.stream(v).forEach(axis -> { // iterate over the Axis[] - sceneState.map.values
					// LinkedList<Segment>
					var segments = axis.getSegments();
					int x = axis.getX();
					segments.forEach(s -> axisX.add(new SvgLine(x, x, s.getP1(), s.getP2(), "black", "3", null, null)));
				});
				figure.getLines().addAll(axisX);
				// figure.setLines(axisX);

				writeInFile(figure);
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addInlet(List<Inlet> inlets) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);
			List<SvgCircle> result = new ArrayList<>();
			LineUtils lineUtils = new LineUtils();
			inlets.stream().map(lineUtils::prepareSvgCircles).forEach(result::addAll);
			figure.setCircles(result);

			writeInFile(figure);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void writeInFile(String fname, Figure figure ) {
		try (FileWriter writer = new FileWriter(fname, false)) {
			String result = xmlMapper.writeValueAsString(figure);
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeInFile(Figure figure) {
		writeInFile(MARKUP_PATH + "\\result" + ".svg",figure);
	}
}