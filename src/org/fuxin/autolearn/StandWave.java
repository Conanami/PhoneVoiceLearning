package org.fuxin.autolearn;

import java.util.Arrays;

import org.fuxin.autolearn.C.Operator;
import org.fuxin.autolearn.C.Type;



public class StandWave {
	public String sourcenumber;
	public String sourcefile;
	public int startindex;
	public int endindex;
	public int[] data = null;  
	public double threshold;
	private Operator operator;
	private Type type;
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "sourcenumber=" + sourcenumber + ", sourcefile="
				+ sourcefile + ", startindex=" + startindex + ", endindex="
				+ endindex  //", data=" + Arrays.toString(data) 
				+ ", threshold="
				+ threshold + ", operator=" + operator + ", type=" + type ;
	}
	
}
