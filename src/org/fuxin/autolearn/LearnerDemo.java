package org.fuxin.autolearn;


public class LearnerDemo {
	
	public static void main(String[] args)
	{
		FuOutput.sop("Learning Starting...");
		long start = System.currentTimeMillis();
		
		//String trainpath="c:\\sound20\\yd400\\";
		String trainfile = "c:\\zn0308\\t006\\t006.xls";
		TrainList trainlist = ReadTrainFile.read(trainfile);
		
		//先把学习文件列表读入
		Learner learner= new Learner(trainlist);
		learner.learn();
		
		//依次学习，如果不能把当前文件识别对，则把当前文件做成样本
		//直到最后一个文件
		//返回训练集的学习结果
		//返回样本列表结果
		FuOutput.writeToFile(learner.standlist, "standlist");
		FuOutput.writeToFile(learner.resultlist, "rlist");
		
		long end = System.currentTimeMillis();
	    FuOutput.sop((end-start)+"ms");
		
	}
}
