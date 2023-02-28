package org.example;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.domain.SvgCircle;
import org.example.domain.Figure;
import org.example.domain.Inlet;
import org.example.domain.InletType;
import org.example.domain.LineUtils;
import org.example.domain.SvgLine;

//
import org.example.domain.Line;
import org.example.domain.SceneState;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Collection;
import java.util.List;

import javax.swing.text.Segment;

public class Main {
	private static final String MARKUP_PATH = "D:\\java_projects\\new\\conv_j17";
	private static final String FILE_NAME = "\\example.svg";
	private static final XmlMapper xmlMapper = new XmlMapper();

	public static void main(String[] args) throws FileNotFoundException {


		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);
			Collection<SvgLine>  lines = figure.getLines();
			LinkedList<Line> ilines = new LinkedList<>();
			for(var l: lines)
				ilines.add(new Line(l.getX1(),l.getY1(),l.getX2(),l.getY2()));
			

			SceneState sceneState = new SceneState(List.of(35 /*, 80, 125*/),ilines);


			var inletType = new InletType("default inlet",20,0);
			sceneState.addCircle(inletType);

			drawAxis(sceneState);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		//addInlet(Collections.singletonList(new Inlet(20, 20)));
	}


	private static void drawAxis(SceneState sceneState) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);
			List<SvgLine> axisX = new ArrayList<>();

			sceneState.getAxes().forEach((k, v) -> {System.out.println("Diego");});

			sceneState.getAxes().forEach((k, v) -> { //iterate over the map in sceneState
				Arrays.stream(v).forEach(axis -> { //iterate over the Axis[] - sceneState.map.values
					//LinkedList<Segment> 
					var segments = axis.getSegments();
					System.out.println(segments.size());
					int x = axis.getX();
					segments.forEach(s ->
						axisX.add(new SvgLine(x, x, s.getP1(), s.getP2(), "black", "3", null, null))
					);
				});
				figure.getLines().addAll(axisX);
				//figure.setLines(axisX);
			});

			writeInFile(figure);
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


	private static void writeInFile(Figure figure) {

		try (FileWriter writer = new FileWriter(MARKUP_PATH + "\\result.svg", false)) {
			String result = xmlMapper.writeValueAsString(figure);
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}