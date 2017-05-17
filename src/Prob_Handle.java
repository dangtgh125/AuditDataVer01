import java.util.Vector;

public class Prob_Handle {
	private Vector<Double> _ProbVector;
	private double mean; // trung bình xác suất - mean
	private double var; // phương sai - variance
	private double sd; // độ lệch chuẩn - standard deviation
	@SuppressWarnings("unused")
	private int count; // tổng từ 1--n
	private int n;
	
	public Prob_Handle(){
		mean = 1;
		var = 1;
		sd = 1;
		n = 0;
		count = 0;
	}
	@SuppressWarnings("unchecked")
	public Prob_Handle(Vector<Double> prob){
		_ProbVector = new Vector<Double>(prob.size());
		_ProbVector = (Vector<Double>)prob.clone();
		n = _ProbVector.size();
		count = n*(n+1)/2; // tổng các số từ 1 đến n = n*(n+1)/2
		mean = calulateMean();
		var = calculateVariance();
		sd = Math.sqrt(var);
	}
	
	double calulateMean(){
		double tmp = 0;
		//int count = 0;
		System.out.println("Size: " + _ProbVector.size());
		for(int i = 0; i < _ProbVector.size(); i++){
			tmp = tmp + (i+1)*_ProbVector.get(i);
		}
		return tmp;
	}
	
	double calculateVariance(){
		double tmp = 0;
		for(int i = 0; i< _ProbVector.size(); i++){
			tmp += _ProbVector.get(i) * Math.pow(((i+1)-mean), 2);
		}
		return (tmp);
	}
	
	public double getMean(){
		return mean;
	}
	
	public double getVar(){
		return var;
	}
	
	public double getSD(){
		return sd;
	}
	
	/* alpha là mức ý nghĩa
	 * giá trị alpha là giá trị gốc trong bảng Z
	 * để tính alpha từ bảng Z thì tra 1-alpha
	 * Nếu muốn tính khoảng tin cậy phải nhập vào alpha/2
	 * */
	@SuppressWarnings("null")
	double getZ(double alpha){
		
		if(alpha == 0.005){ //độ tin cậy 99% => alpha = 0.01 => alpha/2 = 0.005 => Z = 2.575
			return 2.575;
		}
		if(alpha == 0.01){ // độ tin cậy 98% => alpha = 0.02 => alpha/2 = 0.01 => Z = 2.33
			return 2.33;
		}
		if(alpha == 0.025){// độ tin cậy 95% => alpha = 0.05 => alpha/2 = 0.025 => Z = 1.96
			return 1.96;
		}
		if(alpha == 0.05){ // độ tin cậy 90% => alpha = 0.1 => alpha/2 = 0.05 => Z = 1.645
			return 1.645;
		}
		if(alpha == 0.1){ // độ tin cậy 80% => alpha = 0.2 => alpha/2 = 0.1 => Z = 1.28
			return 1.28;
		}
		return (Double) null;
	}
}
