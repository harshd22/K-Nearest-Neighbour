import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class KNN {

	List<IRIS> testSet;
	List<IRIS> trainingSet;
	File training;
	File test;
	private double sepalLengthRange;
	private double sepalWidthRange;
	private double petalLengthRange;
	private double petalWidthRange;
	private int k;
	private int right = 0;
	private int wrong = 0;
	private double accuracy;

	KNN(File training , File test) {
		
		this.training = training;
		this.test = test;
		testSet = new ArrayList<>();
		trainingSet = new ArrayList<>();
		parseFileData(training, trainingSet);
		parseFileData(test, testSet);
		findRange();
		getK();
		nearestNeigbour();
		System.out.println("Right : " + right);
		System.out.println("Wrong : " + wrong);
		accuracy = (double) (right * 100) / testSet.size();
		System.out.println("Accuracy : " + accuracy);

	}

	private void nearestNeigbour() {
		for (IRIS testIris : testSet) {

			ArrayList<IRIS> nearestNeighbours = new ArrayList<IRIS>();
			// System.out.print(testIris.getClassType()+" : ");
			for (IRIS trainingIris : trainingSet) {
				trainingIris.setEuclidianDistance(euclideanDistance(testIris, trainingIris));
				nearestNeighbours.add(trainingIris);

			}
			kNearestNeighbour(nearestNeighbours, testIris);
		}
	}

	private void kNearestNeighbour(List<IRIS> nearestNeighbours, IRIS testIris) {

		List<IRIS> Knn = new ArrayList<>();
		Collections.sort(nearestNeighbours);
		// System.out.print(nearestNeighbours.get(0).getClassType());
		// System.out.println();
		for (int index = 0; index < k; index++) {
			Knn.add(nearestNeighbours.get(index));

		}

		String dominantClass = findDominantClass(Knn);

		if (testIris.getClassType().equals(dominantClass)) {
			System.out.println(testIris.getClassType() + " : Prediction is right");
			right++;
		} else {
			System.out.println(testIris.getClassType() + " : Prediction is Wrong");
			wrong++;
		}

	}

	private String findDominantClass(List<IRIS> list) {

		int setosa = 0;
		int versicolor = 0;
		int virginica = 0;

		for (IRIS knn : list) {
			if (knn.getClassType().contains("setosa")) {
				setosa++;
			} else if (knn.getClassType().contains("versicolor")) {
				versicolor++;
			} else if (knn.getClassType().contains("virginica")) {
				virginica++;
			}
		}

		if (setosa > versicolor && setosa > virginica) {
			return "Iris-setosa";
		} else if (versicolor > setosa && versicolor > virginica) {
			return "Iris-versicolor";
		} else
			return "Iris-virginica";

	}

	private void getK() {
		Scanner s = new Scanner(System.in);
		System.out.println("Please input the Value of K");
		k = s.nextInt();

	}

	private void findRange() {

		double minSLR = Double.MAX_VALUE;
		double maxSLR = Double.MIN_VALUE;
		double minSWR = Double.MAX_VALUE;
		double maxSWR = Double.MIN_VALUE;
		double minPLR = Double.MAX_VALUE;
		double maxPLR = Double.MIN_VALUE;
		double minPWR = Double.MAX_VALUE;
		double maxPWR = Double.MIN_VALUE;

		for (IRIS iris : testSet) {

			minSLR = Math.min(iris.getSepalLength(), minSLR);
			minSWR = Math.min(iris.getSepalWidth(), minSWR);
			minPLR = Math.min(iris.getPetalLength(), minPLR);
			minPWR = Math.min(iris.getPetalWidth(), minPWR);
			maxSLR = Math.max(iris.getSepalLength(), maxSLR);
			maxSWR = Math.max(iris.getSepalWidth(), maxSWR);
			maxPLR = Math.max(iris.getPetalLength(), maxPLR);
			maxPWR = Math.max(iris.getPetalWidth(), maxPWR);

		}

		sepalLengthRange = maxSLR - minSLR;
		sepalWidthRange = maxSWR - minSWR;
		petalLengthRange = maxPLR - minPLR;
		petalWidthRange = maxPWR - minPWR;
	}

	private void parseFileData(File file, List<IRIS> Set) {

		try {
			Scanner s = new Scanner(new BufferedReader(new FileReader(file)));
			while (s.hasNext()) {

				double sepalLength = s.nextDouble();
				double sepalWidth = s.nextDouble();
				double petalLength = s.nextDouble();
				double petalWidth = s.nextDouble();
				String classType = s.next();

				Set.add(new IRIS(sepalLength, sepalWidth, petalLength, petalWidth, classType));

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	private File fileSelector(File file) {

		JFileChooser chooser = new JFileChooser();
		// Sets the file filter.
		chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Select File");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		return file;

	}

	private double euclideanDistance(IRIS testIris, IRIS trainIris) {

		return Math.sqrt((Math.pow(testIris.getPetalLength() - trainIris.getPetalLength(), 2) / petalLengthRange)
				+ (Math.pow(testIris.getPetalWidth() - trainIris.getPetalWidth(), 2) / petalWidthRange)
				+ (Math.pow(testIris.getSepalLength() - trainIris.getSepalLength(), 2) / sepalLengthRange)
				+ (Math.pow(testIris.getSepalWidth() - trainIris.getSepalWidth(), 2) / sepalWidthRange));

	}
	
	public static void main(String[] args) {
		
		File training = new File(args[0]);
		File test = new File (args[1]);
		new KNN(new File(args[0]) , new File(args[1]));
	}

}
