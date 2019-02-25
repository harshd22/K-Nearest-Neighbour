
public class IRIS implements Comparable<IRIS>{

	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private String classType;
	private double euclidianDistance;

	IRIS(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String classType) {

		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.classType = classType;
	}
	
	@Override
	public int compareTo(IRIS o) {
		if(this.euclidianDistance > o.getEuclidianDistance()) {
			return 1;
		}else if(this.euclidianDistance < o.getEuclidianDistance()) {
			return -1;
		}
		
		return 0;
	}

	public double getSepalLength() {
		return sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public String getClassType() {
		return classType;
	}

	public double getEuclidianDistance() {
		return euclidianDistance;
	}

	public void setEuclidianDistance(double euclidianDistance) {
		this.euclidianDistance = euclidianDistance;
	}



}
