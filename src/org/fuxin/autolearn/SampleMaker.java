package org.fuxin.autolearn;


public class SampleMaker {

	public static StandWave makesample(TrainFile tf) {
		// TODO Auto-generated method stub
		StandWave rtsw=null;
		WaveFileReader w1=new WaveFileReader(tf.file);
		//长度不够，不能做样本
		if(w1.getDataLen()<45000) return rtsw;
		
		if(w1.getDataLen()<68000 && w1.getDataLen()>=45000) 
		{
			rtsw=new StandWave();
			rtsw.startindex=34000;
			rtsw.endindex =45000;
		}
		else if(w1.getDataLen()>=68000)
		{
			rtsw=new StandWave();
			rtsw.startindex=56000;
			rtsw.endindex =67000;
		}
		
		rtsw.data = ArrayUtil.Cut(w1.getData()[0], rtsw.startindex, rtsw.endindex-rtsw.startindex);
		
		//实在没啥声音的不能做样本
		if(MathUtil.getMax(rtsw.data, 0, rtsw.data.length)<C.smallnoise*3)
			return null;
		rtsw.sourcefile=tf.file;
		rtsw.setOperator(tf.operator);
		rtsw.setType(tf.mtype);
		//默认阈值
		rtsw.threshold=2200;
		
		
		rtsw.sourcenumber=tf.phonenumber;
		FuOutput.sop(rtsw);
		
		return rtsw;
	}

}
