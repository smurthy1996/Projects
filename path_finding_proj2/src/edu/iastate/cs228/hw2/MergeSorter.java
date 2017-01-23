package edu.iastate.cs228.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.lang.IllegalArgumentException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	private Point[] ptsCpy;
	private boolean comAngle = false;
	private Thread t1;

	/**
	 * The two constructors below invoke their corresponding superclass
	 * constructors. They also set the instance variables algorithm and
	 * outputFileName in the superclass.
	 */

	/**
	 * Constructor accepts an input array of points. in the array.
	 * 
	 * @param pts
	 *            input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		ptsCpy = new Point[pts.length];

		ptsCpy = super.points;

	}

	/**
	 * Constructor reads points from a file.
	 * 
	 * @param inputFileName
	 *            name of the input file
	 * @throws FileNotFoundException
	 */
	public MergeSorter(String inputFileName) throws FileNotFoundException {
		super(inputFileName);
		// TODO
		ptsCpy = new Point[super.points.length];
		for (int i = 0; i < super.points.length; i++) {
			ptsCpy[i] = super.points[i];
		}

	}

	/**
	 * Perform mergesort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 * @param order
	 *            1 by x-coordinate 2 by polar angle
	 *
	 */
	@Override
	public void sort(int order) {
		super.start();
		super.setComparator(order);
		this.comAngle = super.sortByAngle;
		this.mergeSortRec(ptsCpy);
		super.stop();
		if (t1 != null)
			t1.stop();
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[]
	 * of points. One way is to make copies of the two halves of pts[],
	 * recursively call mergeSort on them, and merge the two sorted subarrays
	 * into pts[].
	 * 
	 * @param pts
	 *            point array
	 */
	private void mergeSortRec(Point[] pts) {
		// t1 = new Thread(new Runnable() {
		// public void run() {
		// if (pts.length >= 2) {
		// Point[] left = new Point[pts.length / 2];
		// Point[] right = new Point[pts.length - (pts.length / 2)];
		// for (int i = 0; i < left.length; i++) {
		// left[i] = pts[i];
		// }
		// for (int i = 0; i < right.length; i++) {
		// right[i] = pts[i + (pts.length / 2)];
		// }
		// mergeSortRec(left);
		// mergeSortRec(right);
		// merge(pts, left, right);
		// }
		// }
		// });
		// t1.start();

		if (pts.length >= 2) {
			Point[] left = new Point[pts.length / 2];
			Point[] right = new Point[pts.length - (pts.length / 2)];

			for (int i = 0; i < left.length; i++) {
				left[i] = pts[i];
			}

			for (int i = 0; i < right.length; i++) {
				right[i] = pts[i + (pts.length / 2)];
			}

			mergeSortRec(left);
			mergeSortRec(right);
			merge(pts, left, right);
		}

	}

	public void merge(Point[] pts, Point[] LeftMerge, Point[] RightMerge) {
		int a = 0;
		int b = 0;
		PolarAngleComparator com = null;
		int compareRes = 0;

		if (comAngle) {
			com = (PolarAngleComparator) super.pointComparator;
		}

		for (int i = 0; i < pts.length; i++) {
			if (comAngle) {
				// System.out.println("Iteration:"+a+":"+b);
				if (b < RightMerge.length && (a < LeftMerge.length))
					compareRes = com.compare(LeftMerge[a], RightMerge[b]);
			} else {
				// System.out.println("Iteration:"+a+":"+b);
				if (b < RightMerge.length && (a < LeftMerge.length))
					compareRes = LeftMerge[a].compareTo(RightMerge[b]);
			}

			if (b >= RightMerge.length || (a < LeftMerge.length && compareRes < 0)) {
				pts[i] = LeftMerge[a];
				a++;
			} else {
				pts[i] = RightMerge[b];
				b++;
			}
		}
	}

	// Other private methods in case you need ...

}
