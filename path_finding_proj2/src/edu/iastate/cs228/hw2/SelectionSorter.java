package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.Comparator;
import java.lang.IllegalArgumentException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements selection sort.
 *
 */

public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	Point[] Points;
	private boolean comAngle = false;

	/**
	 * The two constructors below invoke their corresponding superclass
	 * constructors. They also set the instance variables algorithm and
	 * outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		// TODO
		super(pts);
		this.Points = super.points;
	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 */
	public SelectionSorter(String inputFileName) throws FileNotFoundException {
		// TODO
		super(inputFileName);

		this.Points = new Point[super.points.length];
		for (int i = 0; i < super.points.length; i++) {
			this.Points[i] = super.points[i];
		}

	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 *
	 * @param order
	 *            1 by x-coordinate 2 by polar angle
	 *
	 */
	@Override
	public void sort(int order) {
		// TODO

		super.start();
		super.setComparator(order);
		this.comAngle = super.sortByAngle;

		Comparator<Point> com = null;

		if (comAngle) {
			com = (PolarAngleComparator) super.pointComparator;
		} else {
			com = (a, b) -> a.compareTo(b);
		}

		for (int i = 0; i < super.points.length - 1; i++) {

			int first = i;

			for (int j = i + 1; j < points.length; j++) {

				if (com.compare(points[j], points[first]) < 0) {
					first = j;
				}

			}
			Point smaller = points[i];
			points[i] = points[first];
			points[first] = smaller;

		}
		super.stop();

	}
}
