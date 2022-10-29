package org.fuxin.autolearn;

import java.util.ArrayList;

import org.fuxin.autolearn.C.Type;

public class Learner {

	public ArrayList<TrainFile> list;
	public ArrayList<StandWave> standlist;
	public ArrayList<WaveFileResult> resultlist;
	
	public Learner(TrainList trainlist) {
		list = (ArrayList<TrainFile>) trainlist.list;
	}

	/***
	 * 机器学习
	 */
	public void learn() {
		//依次学习，如果不能把当前文件识别对，则把当前文件做成样本
		//直到最后一个文件
		//返回训练集的学习结果
		//返回样本列表结果
		standlist = new ArrayList<StandWave>();
		resultlist = new ArrayList<WaveFileResult>();
		
		//for(int i=0;i<list.size();++i)
		for(int i=0;i<198;++i)
		{
			
			TrainFile tf=list.get(i);
			if(tf.mtype!=Type.Zc)
			{
				WaveFileResult wfr = Classify.filter(tf,standlist);
				//用现有样本集进行比对
				//如果结果与mtype一致
				//则下一个文件
				//如果结果atype与mtype不一致
				//则截取一段加入现有样本集
				if(wfr.getType()!=tf.mtype)
				{
					StandWave sw=SampleMaker.makesample(tf);
					if(sw!=null)
						standlist.add(sw);
				}
			}
			System.out.println("Now:"+i);
		}
		System.out.println("总共生成样本:"+standlist.size());
		finaldo();
		
	}

	private void finaldo() {
		
		for(int i=0;i<list.size();++i)
		{
			TrainFile tf=list.get(i);	
			WaveFileResult wfr = Classify.filter(tf,standlist);
			resultlist.add(wfr);
			System.out.println("Now:"+i);
		}
		
	}

	

}
