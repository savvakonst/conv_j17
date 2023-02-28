package org.example;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.domain.SvgCircle;
import org.example.domain.Figure;
import org.example.domain.Inlet;
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
import java.util.Collections;
import java.util.Collection;
import java.util.List;

public class Main {
	private static final String MARKUP_PATH = "D:\\java_projects\\conv_j17";
	private static final String FILE_NAME = "\\example.svg";
	private static final XmlMapper xmlMapper = new XmlMapper();

	public static void main(String[] args) throws FileNotFoundException {


		try {
			String content = new String(Files.readAllBytes(Paths.get(MARKUP_PATH + FILE_NAME)));
			Figure figure = xmlMapper.readValue(content, Figure.class);
			Collection<SvgLine>  lines = figure.getLines();
			List<Line> ilines = new ArrayList<>();
			for(var l: lines)
				ilines.add(new Line(l.getX1(),l.getY1(),l.getX2(),l.getY2()));
			

			SceneState sceneState = new SceneState(List.of(35,80,125),ilines);

			


		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		addInlet(Collections.singletonList(new Inlet(20, 20)));
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