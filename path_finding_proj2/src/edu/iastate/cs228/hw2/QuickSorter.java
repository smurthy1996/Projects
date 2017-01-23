package edu.iastate.cs228.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import java.lang.IllegalArgumentException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the
 * lecture.
 *
 */

public class QuickSorter extends AbstractSorter {
	Point[] ptsBck;
	private boolean comAngle = false;
	// Other private instance variables if you need ...

	/**
	 * The two constructors below invoke their corresponding superclass
	 * constructors. They also set the instance variables algorithm and
	 * outputFileName in the superclass.
	 */

	/**
	 * Constructor accepts an input array of points.
	 * 
	 * @param pts
	 *            input array of integers
	 */
	public QuickSorter(Point[] pts) {
		// TODO
		super(pts);
		this.ptsBck = super.points;

	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 */
	public QuickSorter(String inputFileName) throws FileNotFoundException {
		super(inputFileName);

		ptsBck = new Point[super.points.length];
		for (int i = 0; i < super.points.length; i++) {
			ptsBck[i] = super.points[i];
		}

	}

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.
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
		quickSortRec(0, ptsBck.length - 1);
		super.stop();

	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 *            starting index of the subarray
	 * @param last
	 *            ending index of the subarray
	 */
	private void quickSortRec(int first, int last) {

		if (first >= last)
			return;

		int pivot = partition(first, last, ptsBck);

		// if(first < pivot -1)
		quickSortRec(first, pivot - 1);
		// if(pivot < last)
		quickSortRec(pivot, last);

		// TODO
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Point[] tbs) {

		Comparator<Point> com = null;

		if (comAngle) {
			com = (PolarAngleComparator) super.pointComparator;
		} else {
			com = (a, b) -> a.compareTo(b);
		}

		Point pivot = tbs[(first + last) / 2];

		while (first <= last) {
			// System.out.println("fewfwe");
			while (com.compare(tbs[first], pivot) < 0) {
				first++;
			}
			while (last > 0 && com.compare(tbs[last], pivot) > 0) {
				last--;
			}
			if (first <= last) {
				swap(first, last);
				first++;
				last--;
			}

		}
		return first;

	}
	// private static void swap(Point[] strArr, int i, int j)
	// {
	// Point temp = strArr[i];
	// strArr[i] = strArr[j];
	// strArr[j] = temp;
	// }

	// Other private methods in case you need ...
}
