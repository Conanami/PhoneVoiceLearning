package org.fuxin.autolearn;

import org.fuxin.autolearn.C.Operator;
import org.fuxin.autolearn.C.Type;

/***
 * 返回的结果类
 * @author Administrator
 *
 */
public class WaveFileResult {
	public WaveFileResult(String path) {
		setFilepath(path);
		setType(C.Type.Undo);
	}
	public String getFilepath() {
		return filepath;
	}
	private void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	private String filepath;  //文件名
	private String phonenumber; //电话号码
	private Type type;        //类型
	private Operator oper;    //运营商
	@Override
	public String toString() {
		return phonenumber +","+ filepath + "," + oper + "," + type;
	}
	public Operator getOper() {
		return oper;
	}
	public void setOper(Operator oper) {
		this.oper = oper;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	
}
