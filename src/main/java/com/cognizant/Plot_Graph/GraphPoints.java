package com.cognizant.Plot_Graph;

import hudson.Launcher;
import hudson.EnvVars;
import hudson.Launcher.ProcStarter;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.util.ArgumentListBuilder;
import hudson.util.ChartUtil.NumberOnlyBuildLabel;

import hudson.util.DataSetBuilder;

import hudson.util.ShiftedCategoryAxis;

import java.awt.BasicStroke;
import java.awt.Color;
//import org.apache.http.HttpResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.acegisecurity.providers.dao.salt.SystemWideSaltSource;
//import org.apache.http.HttpResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import hudson.model.Computer;
import hudson.remoting.Channel;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.slaves.Channels;

import java.net.*;

/**
 * This class does the actual execution..
 * 
 */
public class GraphPoints {

	@SuppressWarnings("deprecation")

	public static JFreeChart buildChart(AbstractBuild<?, ?> build, String FilePath)
			throws IOException, InterruptedException {
		JFreeChart lineChart = null;
		System.out.println("in Jfree chart: " + FilePath);
		lineChart = ChartFactory.createLineChart(null, "Build Number", "Number of Severities", createDataset(FilePath),
				PlotOrientation.VERTICAL, true, true, false);
	
		lineChart.setBackgroundPaint(Color.white);

		CategoryPlot plot = lineChart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		//plot.setOutlinePaint(null);
		//plot.setForegroundAlpha(0.8f);
	//	plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);

		//CategoryAxis domainAxis = new ShiftedCategoryAxis(null);
		//plot.setDomainAxis(domainAxis);
		//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		//domainAxis.setLowerMargin(0.0);
		//domainAxis.setUpperMargin(0.0);
		//domainAxis.setCategoryMargin(0.0);

		LineAndShapeRenderer r2 = (LineAndShapeRenderer) plot.getRenderer();

		// plot.setRenderer((CategoryItemRenderer) r2);
		r2.setSeriesPaint(0, Color.RED);
		r2.setSeriesPaint(1, Color.BLUE);
		r2.setSeriesPaint(2, Color.GREEN);
		r2.setSeriesPaint(3, Color.DARK_GRAY);
		// plot.getRenderer().setSeriesPaint(0, Color.RED);
		r2.setSeriesStroke(0, new BasicStroke(2.0f));
		r2.setSeriesStroke(1, new BasicStroke(2.0f));
		r2.setSeriesStroke(2, new BasicStroke(2.0f));
		r2.setSeriesStroke(3, new BasicStroke(2.0f));
		
	//	NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// crop extra space around the graph
		plot.setInsets(new RectangleInsets(0, 0, 0, 5.0));

		return lineChart;
	}

	public static CategoryDataset createDataset(String FilePath) throws IOException {
		// String FilePath1 =GraphBuilder
		// FILENAME="D://values.properties";
		Properties props = new Properties();
		System.out.println("in createDataset: " + FilePath);
		FileInputStream fis = new FileInputStream(FilePath);
		props.load(fis);
		
		Set<Object> keys = props.keySet();
		List<Object> listKeys = new ArrayList<Object>();
		List<Integer> listKeys1 = new ArrayList<Integer>();
		listKeys.addAll(keys);
		
		//TreeMap<K, V>
		for (Object object : listKeys) {
			String obj=object.toString();
			Integer intobj=Integer.parseInt(obj);
			listKeys1.add(intobj);
		}
		System.out.println("***"+listKeys1);
		Collections.sort(listKeys1);
		//Collections.reverse(listKeys1);
		System.out.println("sorted integer list "+listKeys1);
		int sizeOfLoop = keys.size();
		/*System.out.println("Size of loop: " + sizeOfLoop);
		System.out.println("unsorted array of list keys"+listKeys);
Collections.reverse(listKeys);
System.out.println("sorted array of list keys"+listKeys);*/
		//DataSetBuilder builder = new DataSetBuilder();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		/*for (int i = sizeOfLoop; i >= 0; i--) {
		System.out.println("i value from last "+listKeys1.get(i).toString());
		}*/
		if (listKeys1.size() >= 10) {
			for (int i = (listKeys1.size() - 1); i > (listKeys1.size() - 11); i--) {
				String value = props.getProperty(((listKeys1.get(i)).toString()).trim());
				int key = Integer.parseInt((listKeys1.get(i)).toString());
				String[] array = value.split("\\,");
				// System.out.println("Array is: "+array);
				for (int j = 0; j < 4; j++) {
					int high = Integer.parseInt(array[0].trim());
					int med = Integer.parseInt(array[1].trim());
					int low = Integer.parseInt(array[2].trim());
					int neg = Integer.parseInt(array[3].trim());
					dataset.addValue(high, "High", "#" + key);
					// System.out.println("This: "+high+"High"+key);
					dataset.addValue(med, "Medium", "#" + key);
					dataset.addValue(low, "Low", "#" + key);
					dataset.addValue(neg, "Negligible", "#" + key);
				}
				fis.close();

			}
		} else {
			for (int i = 0; i < sizeOfLoop; i++) {
				String value = props.getProperty(((listKeys1.get(i)).toString()).trim());
				int key = Integer.parseInt((listKeys1.get(i)).toString());
				System.out.println(value);
				
				String[] array = value.split("\\,");
				System.out.println("Array is: " + array);
				for (int j = 0; j < 4; j++) {
					int high = Integer.parseInt(array[0].trim());
					int med = Integer.parseInt(array[1].trim());
					int low = Integer.parseInt(array[2].trim());
					int neg = Integer.parseInt(array[3].trim());
					dataset.addValue(high, "High", "#" + key);
					// System.out.println("This: "+high+"High"+key);
					dataset.addValue(med, "Medium", "#" + key);
					dataset.addValue(low, "Low", "#" + key);
					dataset.addValue(neg, "Negligible", "#" + key);
				}
				fis.close();

			}
		}
		System.out.println("Inside datset");

	return dataset;

	}

	// second chart stacked graph
	public static JFreeChart buildChartDelta(AbstractBuild<?, ?> build, String FilePath)
			throws IOException, InterruptedException {
		JFreeChart lineChart = null;
		// System.out.println("FILE NAME IS "+GraphPoints.FILENAME);
		System.out.println("in buildChartDelta: " + FilePath);
		lineChart = ChartFactory.createStackedBarChart(null, "Build Number", "Total Severity Count",
				createDataset1(FilePath), PlotOrientation.VERTICAL, true, true, false);

		lineChart.setBackgroundPaint(Color.white);

		CategoryPlot plot = lineChart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlinePaint(null);
		//plot.setForegroundAlpha(0.8f);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);

		CategoryAxis domainAxis = new ShiftedCategoryAxis(null);
		//plot.setDomainAxis(domainAxis);
	//	domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		//domainAxis.setLowerMargin(0.0);
		//domainAxis.setUpperMargin(0.0);
		//domainAxis.setCategoryMargin(0.0);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// crop extra space around the graph
		plot.setInsets(new RectangleInsets(0, 0, 0, 5.0));
		return lineChart;
	}

	public static CategoryDataset createDataset1(String FilePath) throws IOException {

		// FILENAME="D://values.properties";
		Properties props = new Properties();
		System.out.println("in createDataset1: " + FilePath);
		FileInputStream fis = new FileInputStream(FilePath);
		props.load(fis);
		Set<Object> keys = props.keySet();
		List<Object> listKeys = new ArrayList<Object>();
		listKeys.addAll(keys);
		int sizeOfLoop = keys.size();
		System.out.println("Size of loop: " + sizeOfLoop);

		DataSetBuilder builder = new DataSetBuilder();

		for (int i = 0; i < sizeOfLoop; i++) {
			String value = props.getProperty((String) listKeys.get(i)).trim();
			int key = Integer.parseInt((String) listKeys.get(i));
			String[] array = value.split("\\,");
			// System.out.println("Array is: "+array);
			// for(int j=0;j<array.length;j++){
			int total = Integer.parseInt(array[4].trim());
			builder.add(total, "Total Count", "#" + key);
			// }
			fis.close();

		}

		return builder.build();

	}

}
