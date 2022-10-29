package org.fuxin.autolearn;



public class C {
	
	//判断少于多少是噪音
	public static int smallnoise=60;
	//WAV文件最大值ֵ
	public static int maxwave = 32768;
	
	//移动的号段
	public static String[] ydprefix={"134","135","136","137",
			"138","139","150","151","152",
			"157","158","159","182","183",
			"184","187","178","188","147"};
	//联通的号段
	public static String[] ltprefix={"130","131","132","145",
			"155","156","176","185","186"};

	//电信的号段
	public static String[] dxprefix={"133","153","177","180","181","189"};
	
	//0301调试新增加的样本
	
	
	
	public enum Operator
	{
		Unknown,Yd,Lt,Dx
	}
	
	public enum Type
	{
		Zc,Kh,Tj,Gj,Unknown,Undo
	}

	public static Type toType(String type) {
		switch(type.toLowerCase())
		{
		case "kh":
			return Type.Kh;
		case "tj":
			return Type.Tj;
		case "gj":
			return Type.Gj;
		default:
			return Type.Zc;
		}
	}
	
}
