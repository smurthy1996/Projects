package edu.iastate.cs228.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.IllegalArgumentException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

public class InsertionSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	private Point[] ptsCpy;
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
	public InsertionSorter(Point[] pts) {
		// TODO
		// TODO
		super(pts);
		this.ptsCpy = super.points;

	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 */
	public InsertionSorter(String inputFileName) throws FileNotFoundException {
		super(inputFileName);
		// TODO
		ptsCpy = new Point[super.points.length];
		for (int i = 0; i < super.points.length; i++) {
			ptsCpy[i] = super.points[i];
		}
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 * @param order
	 *            1 by x-coordinate 2 by polar angle
	 */
	@Override
	public void sort(int order) {
		// TODO

		super.start();
		super.setComparator(order);
		this.comAngle = super.sortByAngle;
		this.dosort(ptsCpy);
		super.stop();

	}

	public Point[] dosort(Point[] arrayInput) {
		Point temp;
		PolarAngleComparator com = null;
		int compareRes = 0;

		if (comAngle) {
			com = (PolarAngleComparator) super.pointComparator;
		}

		for (int i = 1; i < arrayInput.length; i++) {
			for (int j = i; j > 0; j--) {
				if (this.comAngle) {
					compareRes = com.compare(arrayInput[j], arrayInput[j - 1]);
				} else {
					compareRes = arrayInput[j].compareTo(arrayInput[j - 1]);
				}
				if (compareRes < 0) {
					temp = arrayInput[j];
					arrayInput[j] = arrayInput[j - 1];
					arrayInput[j - 1] = temp;
				}
			}
		}
		return arrayInput;

	}
}
