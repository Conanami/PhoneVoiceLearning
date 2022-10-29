package org.fuxin.autolearn;

import jxl.Cell;

import org.fuxin.autolearn.C.Operator;
import org.fuxin.autolearn.C.Type;

public class TrainFile {
	public String phonenumber;
	public String file;
	public Operator operator;
	public Type mtype;
	public Type atype;
	
	public TrainFile(String phonenumber, String file, String operator, String type) {
		this.phonenumber=phonenumber;
		this.file = file;
		this.operator = OpMgr.findOp(phonenumber);
		this.mtype = C.toType(type);
		this.atype = Type.Undo;
	}

	@Override
	public String toString() {
		return "TrainFile [phonenumber=" + phonenumber + ", file=" + file
				+ ", operator=" + operator + ", mtype=" + mtype + ", atype="
				+ atype + "]";
	}
	
}
