package edu.iastate.cs228.hw2;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Perform the four sorting algorithms over each sequence of
	 * integers, comparing points by x-coordinate or by polar angle with respect
	 * to the lowest point.
	 * 
	 * @param args
	 **/
	private static Point[] Points;
	private static String fName;

	/*
	 * WORKING SEED VALUES FOR > 200 VECTORS -9970 -6139 -6379 -1337
	 */
	public static void main(String[] args) {
		// TODO
		//
		// Conducts multiple sorting rounds. In each round, performs the
		// following:
		//
		// a) If asked to sort random points, calls generateRandomPoints() to
		// initialize an array
		// of random points.
		// b) Reassigns to elements in the array sorters[] (declared below) the
		// references to the
		// four newly created objects of SelectionSort, InsertionSort, MergeSort
		// and QuickSort.
		// c) Based on the input point order, carries out the four sorting
		// algorithms in one for
		// loop that iterates over the array sorters[], to sort the randomly
		// generated points
		// or points from an input file.
		// d) Meanwhile, prints out the table of runtime statistics.
		//
		// A sample scenario is given in Section 2 of the project description.
		//
		AbstractSorter[] sorters = new AbstractSorter[4];

		// Random ranSeed = new Random();
		// int seed =
		// ranSeed.nextInt(Integer.MAX_VALUE-ranSeed.nextInt(Integer.MAX_VALUE));
		boolean isFileInput = false;
		Random ran = new Random();// seed);
		Scanner scanny = new Scanner(System.in);

		System.out.println("Please choose a way to import/randomly assign the points 1) Random 2)File Import");

		String impSel = scanny.next();

		while (!(impSel.isEmpty())) {

			if (impSel.contains("1")) {
				System.out.println("How many random points would you like to generate?");
				String pts = scanny.next();

				if (Integer.parseInt(pts) < 0)
					System.exit(1);
				Points = generateRandomPoints(Integer.parseInt(pts), ran);
				break;
			} else {
				isFileInput = true;
				System.out.println("Enter name of the file? ");
				String f2Name = scanny.next();
				fName = f2Name;
				break;
			}
		}
		System.out.println("Seed Used: " + (ran.nextInt(Integer.MAX_VALUE) - ran.nextInt(Integer.MAX_VALUE)));
		// Random Points Generated

		System.out.println("\n");
		if (isFileInput == false) {
			sorters[0] = new MergeSorter(Points);
			sorters[1] = new QuickSorter(Points);
			sorters[2] = new InsertionSorter(Points);
			sorters[3] = new SelectionSorter(Points);
		} else {
			try {
				sorters[0] = new MergeSorter(fName);
				Points = sorters[0].points;
				sorters[1] = new QuickSorter(fName);
				sorters[2] = new InsertionSorter(fName);
				sorters[3] = new SelectionSorter(fName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		sorters[0].algorithm = "Merge Sort";
		sorters[1].algorithm = "Quick Sort";
		sorters[2].algorithm = "Insertion Sort";
		sorters[3].algorithm = "Selection Sort";

		// scanny.close();

		for (int i = 0; i < Points.length; i++) {
			System.out.print(Points[i].toString() + "," + " \t");
			if (!(i == 0) && (i + 1) % 5 == 0)
				System.out.println();
		}
		System.out.println("\n");

		System.out.println(
				"Sorting comparison: 1) Sort all 2) Merge 3) Selection 4) Insertion 5) Quick 6) Stats 7) Draw 8) Draw_With_Stats <- Choose a combo");
		Scanner input = new Scanner(System.in);
		System.out.println("Pick any combination except for 12,13,14,15 or it will exit the system");
		String in = input.next();
		if (in.contains("9") || (in.contains("1")
				&& (in.contains("2") || in.contains("3") || in.contains("4") || in.contains("5")))) {
			System.out.println("Picked all the sorts and then individual one, why would you even?");
			System.exit(1);

		}

		System.out.println("What Order? 1) by x_Distance 2) Polar Angle");

		String ord = input.next();
		while (!(in.isEmpty())) {

			int split = Integer.parseInt(in);

			if (in.contains("1")) {

				for (AbstractSorter sor : sorters) {
                    sor.sort(Integer.parseInt(ord));
                }
            }
			if (in.contains("2")) {

				sorters[0].sort(Integer.parseInt(ord));

			}
			if (in.contains("3")) {
				sorters[3].sort(Integer.parseInt(ord));
			}
			if (in.contains("4")) {

				sorters[2].sort(Integer.parseInt(ord));
			}
			if (in.contains("5")) {
				sorters[1].sort(Integer.parseInt(ord));
			}
			if (in.contains("6")) {
				System.out.println("\n_________________________________STATS__________________________________");
				if (in.contains("1")) {
					for (AbstractSorter sor : sorters) {
						sor.sort(Integer.parseInt(ord));
						System.out.println(sor.stats());
					}
				}
				if (in.contains("2")) {

					System.out.println(sorters[0].stats());

				}
				if (in.contains("3")) {
					System.out.println(sorters[3].stats());
				}
				if (in.contains("4")) {

					System.out.println(sorters[2].stats());
				}
				if (in.contains("5")) {
					System.out.println(sorters[1].stats());
				}
				System.out.println("______________________________END_STATS__________________________________\n");
			}
			if (in.contains("7")) {
				System.out.println("\n_________________________________PLOT_POINTS__________________________________");
				if (in.contains("1")) {
					for (AbstractSorter sor : sorters) {
						sor.sort(Integer.parseInt(ord));
						sor.draw();
					}

				}
				if (in.contains("2")) {

					System.out.println(sorters[0].stats());

				}
				if (in.contains("3")) {
					System.out.println(sorters[3].stats());
				}
				if (in.contains("4")) {

					System.out.println(sorters[2].stats());
				}
				if (in.contains("5")) {
					System.out.println(sorters[1].stats());
				}

			}
			if (in.contains("8")) {
				System.out.println(
						"\n_________________________________STATS/PLOT_POINTS__________________________________");
				if (in.contains("1")) {
					for (AbstractSorter sor : sorters) {
						sor.sort(Integer.parseInt(ord));
						System.out.println(sor.stats());
						for (int i = 0; i < Points.length; i++) {
							System.out.print(sor.points[i].toString() + "," + " \t");
							if (!(i == 0) && (i + 1) % 5 == 0)
								System.out.println();
						}
						System.out.println("\n");
						sor.draw();
					}
				}
				if (in.contains("2")) {

					System.out.println(sorters[0].stats());
					sorters[0].draw();
					for (int i = 0; i < Points.length; i++) {
						System.out.print(sorters[3].points[i].toString() + "," + " \t");
						if (!(i == 0) && (i + 1) % 5 == 0)
							System.out.println();
					}
					System.out.println("\n");

				}
				if (in.contains("3")) {
					System.out.println(sorters[3].stats());
					sorters[3].draw();
					for (int i = 0; i < Points.length; i++) {
						System.out.print(sorters[3].points[i].toString() + "," + " \t");
						if (!(i == 0) && (i + 1) % 5 == 0)
							System.out.println();
					}
					System.out.println("\n");
				}
				if (in.contains("4")) {

					System.out.println(sorters[2].stats());
					sorters[2].draw();
					for (int i = 0; i < Points.length; i++) {
						System.out.print(sorters[2].points[i].toString() + "," + " \t");
						if (!(i == 0) && (i + 1) % 5 == 0)
							System.out.println();
					}
					System.out.println("\n");
				}
				if (in.contains("5")) {
					System.out.println(sorters[1].stats());
					sorters[1].draw();
					for (int i = 0; i < Points.length; i++) {
						System.out.print(sorters[1].points[i].toString() + "," + " \t");
						if (!(i == 0) && (i + 1) % 5 == 0)
							System.out.println();
					}
					System.out.println("\n");
				}

			}
			System.out.println("\n");

			System.out.println(
					"Sorting comparison:0)Populate array random 1) Sort all 2) Merge 3) Selection 4) Insertion 5) Quick 6) Stats 7) Draw 8) Draw_With_Stats 9)EXIT<- Choose a combo");
			in = input.next();

			if (in.contains("9")) {
				System.out.println("QUITTING");
				try {
					sorters[0].writePointsToFile();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(1);
			}
			if (in.contains("0")) {
				System.out.println("RePopulating array with random values");
				Points = null;
				System.out.println("Enter random sampeling number");
				String num = input.next();
				Points = generateRandomPoints(Integer.parseInt(num), ran);
				System.out.println("______________________________REPOPULATION_DONE_______________________________");
				for (int i = 0; i < Points.length; i++) {
					System.out.print(Points[i].toString() + " \t");
					if (i % 10 == 0)
						System.out.println();
				}
				System.out.println("______________________________REPOPULATION_DONE_______________________________");
				continue;
			}

			System.out.println("\nWhat Order? 1) by x_Distance 2) Polar Angle");
			ord = input.next();

		}
		input.close();

		// Within a sorting round, have each sorter object call the sort and
		// draw() methods
		// in the AbstractSorter class. You can visualize the result of each
		// sort. (Windows
		// have to be closed manually before rerun.) Also, print out the
		// statistics table
		// (cf. Section 2).

	}

	/**
	 * This method generates a given number of random points to initialize
	 * randomPoints[]. The coordinates of these points are pseudo-random numbers
	 * within the range [-50,50] � [-50,50]. Please refer to Section 3 on how
	 * such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts
	 *            number of points
	 * @param rand
	 *            Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException
	 *             if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		Point[] points = new Point[numPts];

		for (int i = 0; i < numPts; i++) {

			points[i] = new Point(50 - rand.nextInt(100), 50 - rand.nextInt(100));

		}

		return points;
		// TODO
	}
}
