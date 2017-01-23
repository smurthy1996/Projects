package edu.iastate.cs228.hw2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  
 * @author
 *
 */

import java.util.Comparator;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later the sorted) sequence and records
 * the employed sorting algorithm, the comparison method, and the time spent on
 * sorting.
 *
 */

public abstract class AbstractSorter {
	private boolean running = false;
	private long elapsedTime = 0L;
	private long mostRecentStartTime = -1;
	private boolean started = false;

	protected Point[] points; // Array of points operated on by a sorting
								// algorithm.
								// The number of points is given by
								// points.length.

	protected String algorithm = null; // "selection sort", "insertion sort",
										// "mergesort", or
										// "quicksort". Initialized by a
										// subclass.
										// constructor.
	protected boolean sortByAngle; // true if the last sorting was done by polar
									// angle and
									// false if by x-coordinate

	protected String outputFileName; // "select.txt", "insert.txt", "merge.txt",
										// or "quick.txt"

	protected long sortingTime; // execution time in nanoseconds.

	protected Comparator<Point> pointComparator; // comparator which compares
													// polar angle if
													// sortByAngle == true and
													// x-coordinate if
													// sortByAngle == false
	private PolarAngleComparator pc;

	private Point lowestPoint; // lowest point in the array, or in case of a
								// tie, the
								// leftmost of the lowest points. This point is
								// used
								// as the reference point for polar angle based
								// comparison.

	// Add other protected or private instance variables you may need.

	protected AbstractSorter() {
		// No implementation needed. Provides a default super constructor to
		// subclasses.
		// Removable after implementing SelectionSorter, InsertionSorter,
		// MergeSorter, and QuickSorter.
	}

	/**
	 * This constructor accepts an array of points as input. Copy the points
	 * into the array points[]. Sets the instance variable lowestPoint.
	 * 
	 * @param pts
	 *            input array of points
	 * @throws IllegalArgumentException
	 *             if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		if (pts == null || pts.length < 1)
			throw new IllegalArgumentException("Null array passed");
		// TODO
		points = new Point[pts.length];
		int counter = 0;
		for (Point pt : pts) {
			points[counter] = pt;
			counter = counter + 1;
		}

	}

	/**
	 * This constructor reads points from a file. Sets the instance variables
	 * lowestPoint and outputFileName.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 *             when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException {
		// TODO

		File toRead = new File(inputFileName);
		// Call scanner class to read the file

		if (!toRead.exists())
			throw new FileNotFoundException("Null File");

		Scanner scanFile = new Scanner(toRead);

		// Set up a dynamic ArrayList to store data with dynamic lengths
		ArrayList<Point> toadd = new ArrayList<Point>();

		// While has next checks if there exists a ASCII line

		// I was initili
		while (scanFile.hasNext()) {
			String line = scanFile.nextLine();
			Scanner scanLine = new Scanner(line);

			toadd.add(new Point(scanLine.nextInt(), scanLine.nextInt()));
			scanLine.close();
		}
		scanFile.close();

		if (toadd.size() % 2 != 0)
			throw new InputMismatchException("Odd number of points");

		System.out.println(toadd.size());
		this.points = toadd.toArray(new Point[toadd.size()]);
		int i = 0;

	}

	/**
	 * Sorts the elements in points[].
	 * 
	 * a) in the non-decreasing order of x-coordinate if order == 1 b) in the
	 * non-decreasing order of polar angle w.r.t. lowestPoint if order == 2
	 * (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls
	 * the method setComparator() to set the variable pointComparator and use it
	 * in sorting. Records the sorting time (in nanoseconds) using the
	 * System.nanoTime() method. (Assign the time spent to the variable
	 * sortingTime.)
	 * 
	 * @param order
	 *            1 by x-coordinate 2 by polar angle w.r.t lowestPoint
	 *
	 * @throws IllegalArgumentException
	 *             if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException;

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project
	 * description.
	 */
	public String stats() {
		StringBuilder br = new StringBuilder();

		br.append(algorithm);
		br.append("\t");
		// System.out.println(this.getElapsedTime());
		br.append(this.getElapsedTime() + "s");

		return br.toString();

		// TODO
	}

	public void start() {
		if (running) {
			return;
		}
		elapsedTime = 0;
		mostRecentStartTime = System.nanoTime();
		running = true;
		started = true;
	}

	/***
	 * Stops timer, storing elapsed time.
	 */
	public void stop() {
		if (!running) {
			return;
		}
		elapsedTime += System.nanoTime() - mostRecentStartTime;
		running = false;
	}

	public double getElapsedTime() {
		if (!started) {
			throw new IllegalStateException();
		}
		if (running) {
			return System.nanoTime() - mostRecentStartTime + elapsedTime;
		}
		double timeDiv = (double) (elapsedTime / 1000000000.0);
		BigDecimal bd = new BigDecimal(timeDiv);
		return bd.doubleValue();
	}

	/**
	 * Write points[] to a string. When printed, the points will appear in order
	 * of increasing index with every point occupying a separate line. The x and
	 * y coordinates of the point are displayed on the same line with exactly
	 * one blank space in between.
	 */
	@Override
	public String toString() {
		StringBuilder br = new StringBuilder();

		for (int i = 0; i < points.length; i++) {

			br.append(points[i].toString());
			// br.append("\t ");

			// if(!(i==0)&&(i+1)%5 == 0)
			// br.append("\n");

		}
		return br.toString();

		// TODO
	}

	/**
	 * This method, called after sorting, writes point data into a file by
	 * outputFileName.<br>
	 * The format of data in the file is the same as printed out from
	 * toString().<br>
	 * The file can help you verify the full correctness of a sorting result and
	 * debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException {
		// TODO
		outputFileName = algorithm + ".txt";
		File fOpen = new File(outputFileName);
		PrintWriter fWrite = new PrintWriter(fOpen);
		fWrite.print(this.toString());
		fWrite.flush();
		System.out.println("___________________________________File Writed_________________________________");

		fWrite.close();

	}

	/**
	 * This method is called after sorting for visually check whether the result
	 * is correct. You just need to generate a list of points and a list of
	 * segments, depending on the value of sortByAngle, as detailed in Section
	 * 4.1. Then create a Plot object to call the method myFrame().
	 */
	private int CCW(Point p, Point q, Point r) {
		int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());

		if (val < 0)
			return -1;
		else if (val > 0)
			return +1;
		else
			return 0;
	}

	public void leftHull(Point[] points, ArrayList<Point> next) {

		int n = points.length;
		int leftMost = 0;
		for (int i = 1; i < n; i++)
			if (points[i].getX() < points[leftMost].getX())
				leftMost = i;
		leftMost = 0;
		int p = leftMost, q;
		int qp = 0;
		/** iterate till p becomes leftMost **/
		do {
			/** wrapping **/
			q = (p + 1) % n;
			for (int i = 0; i < n; i++) {
				if (pointComparator.compare(points[p], points[i]) < 0) {
					q = i;
					next.add(points[p]);
					for (int j = 1; j < n; j++) {

						if (pointComparator.compare(points[i], points[j]) < 0) {

							qp = j;
							next.add(points[j]);

						}
					}

				}

			}

			// next.add(points[q]);
			System.out.println(q + ":" + p);
			p = q;

		} while (p != leftMost);

	}

	public ArrayList<Point> convexHull(Point[] points) {
		int n = points.length;
		/** if less than 3 points return **/
		if (n < 3)
			return null;
		ArrayList<Point> next = new ArrayList<Point>();

		/** find the leftmost point **/

		leftHull(points, next);

		leftHull(points, next);

		return next;
	}

	public void draw() {
		// Allways one less segments than points
		int numSegs = points.length - 1; // number of segments to draw

		// Based on Section 4.1, generate the line segments to draw for display
		// of the sorting result.
		// Assign their number to numSegs, and store them in segments[] in the
		// order.
		Segment[] segments = new Segment[numSegs];

		// TODO
		if (sortByAngle == false) {

			for (int i = 1; i < points.length; i++) {
				System.out.println(i - 1);
				segments[i - 1] = new Segment(points[i - 1], points[i]);
			}

		} else {
			ArrayList<Segment> segs = new ArrayList<Segment>();
			ArrayList<Point> pts = new ArrayList<Point>();

			for (int i = 0; i < ((numSegs + 1)); i++) {
				segs.add(new Segment(this.lowestPoint, points[i]));
				pts.add(points[i]);
			}
			pts.add(points[0]);
			for (int i = 1; i < pts.size(); i++) {

				segs.add(new Segment(pts.get(i - 1), pts.get(i)));

			}

			segments = new Segment[segs.size() - 1];
			for (int j = 0; j < segs.size() - 1; j++) {
				segments[j] = segs.get(j);
			}
		}

		// The following statement creates a window to display the sorting
		// result.
		Plot.myFrame(points, segments, getClass().getName());

	}

	/**
	 * Generates a comparator on the fly that compares by polar angle if
	 * sortByAngle == true and by x-coordinate if sortByAngle == false. Set the
	 * protected variable pointComparator to it. Need to create an object of the
	 * PolarAngleComparator class and call the compareTo() method in the Point
	 * class, respectively for the two possible values of sortByAngle.
	 * 
	 * @param order
	 */
	protected void setComparator(int order) {
		if (order == 0) {
			throw new IllegalArgumentException("0 is not a option. ");
		}

		if (order == 1) {
			this.sortByAngle = false;
		} else {
			this.sortByAngle = true;
			if (sortByAngle) {
				// int lowfPnt = points[0].getY();
				// Point ref = new Point(points[0].getX(),points[0].getY());
				// for(int i = 0,j=1; i < points.length && j<points.length;
				// i++,j++){
				// int highfPnt = points[j].getY();
				//
				// if(lowfPnt < highfPnt){
				// continue;
				// }
				// else if(lowfPnt > highfPnt){
				// lowfPnt = highfPnt;
				// ref = points[j];
				// }
				// else{
				// if(points[i].getX() > points[j].getX()){
				// lowfPnt = points[j].getY();
				// ref = points[j];
				// }
				// else{
				// continue;
				// }
				// }
				// }
				// lowestPoint = ref;
				//
				// pointComparator = new PolarAngleComparator(lowestPoint);

				Point lowest = points[0];

				for (int i = 1; i < points.length; i++) {

					Point temp = points[i];

					if (temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
						lowest = temp;
					}
				}

				lowestPoint = lowest;
				pointComparator = new PolarAngleComparator(lowestPoint);
			}

		}
	}

	/**
	 * Swap the two elements indexed at i and j respectively in the array
	 * points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		// TODO
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
}
