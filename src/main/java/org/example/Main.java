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



			// creating inlet types 
			var inletType_1 = new InletType("default inlet", 7, 2);
			var inletType_2 = new InletType(" inlet R17", 30, 2);
			var inletType_3 = new InletType(" inlet R20", 18, 2);


			/// also you can create another placement strategy, you need just to implement PlacementStrategyIfs. 
			/// current strategy is the simpliest, i foresee another one simple (simple to implement and understand) strategy. 
			/// But i'm too lazy for implementing it. 
			/// 
			var strategy = new SimpleStrategy();


			// by choosing order of insertion inlet you can control their placement
			for(int k=0 ; k< 3; k++)
				sceneState.addCircle(inletType_2, strategy);

			for(int k=0 ; k< 10; k++)
				sceneState.addCircle(inletType_3, strategy);

			for(int k=0 ; k< 10; k++)
			 	sceneState.addCircle(inletType_1, strategy);

			sceneState.getAxesMap().forEach((k, v) -> { 
				draw(MARKUP_PATH + "\\result_"+k + ".svg",sceneState.getCircles(),v );
			});
			

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

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
				int id = 0;
				circle.forEach(s -> circleX.add(new SvgCircle(s.getInletType().getInletName()+" "+id,s.getRadius(),s.getP().x, s.getP().y,Color.GRAY)));
				figure.setCircles(circleX);
			}

			writeInFile(filename, figure);
		} catch (IOException e) {
			e.printStackTrace();

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


}