package org.fuxin.autolearn;

import java.util.ArrayList;


/***
 * 对两段音频进行比对
 * @author Administrator
 *
 */
public class WaveMatcher {
	
	
	/***
	 * 
	 * @param reader
	 * @param sourcefile 
	 * @param data
	 * @param
	 * @return
	 * 0 匹配上
	 * 1 太短
	 * 2 没有匹配上
	 * 3 文件格式不匹配
	 */
	public static int Compare(WaveFileReader reader, int[] standdata, double threshold, String sourcefile) {
		// TODO Auto-generated method stub
		
			return SoundMatch(reader.getData()[0],standdata,threshold,reader.getFilename(),sourcefile);
		
		
	}

	/***
	 * 
	 * @param sample
	 * @param standard
	 * @param d 
	 * 
	 */
	private static int SoundMatch(int[] sample, int[] standard, double d,String filename,String standfile) {
		// TODO Auto-generated method stub
		if(sample.length<standard.length)
		{
			//如果样本比标准的短，则无法判断，返回1
			return 1;
		}
		else
		{
			int meetcnt = 0;
			int[] regu_sta=Regulize(standard);
			
			
			
			ArrayList<String> tofile = new ArrayList<String>();
			int step = 20;
			if(sample.length<24000) step = 10;
			for(int i=32000;i<=sample.length-standard.length;i=i+step)
			{
				String output=String.format("%.2f", ((float)i/8000))+",";
				int[] newsample=ArrayUtil.Cut(sample,i,standard.length);
				int[] regu_new=Regulize(newsample);
				
				
				Eigenvalue ev =SameMatch(regu_new,regu_sta);
				
				output=output+ev.division+","+ev.sample_sqr+","+ev.stand_sqr;
				if(i%100==0)
				{
					//System.out.println(output);
				}
				tofile.add(output);
				
				if(ev.division>8000) 
					step=160;
				else if(ev.division>5000)
					step=80;
				else
					step=20;
				
				
				if(ev.division < d &&
						ev.sample_sqr/ev.stand_sqr<=1.5 
						&& ev.sample_sqr/ev.stand_sqr>=0.7 )
					meetcnt++;
				else
				//专门针对特别响的情况
				/*if(ev.sample_sqr>ev.stand_sqr 
						&& ev.division<d*(ev.sample_sqr/ev.stand_sqr) 
						&& ev.sample_sqr/ev.stand_sqr<1.7)
					meetcnt++;
				
				else*/
					meetcnt=0;
				
				
				if(meetcnt>1) 
				{
					//FuOutput.writeToFile(tofile, "success_"+CutDotWave(filename)+CutDotWave(standfile));
					return 0;
				}
			}
			//如果没有匹配上，则记录下文件
			//FuOutput.writeToFile(tofile, CutDotWave(filename)+CutDotWave(standfile));
		}
		//没有匹配上，就返回2
		return 2;
	}

	private static String CutDotWave(String filename){
	    String tmp = filename.substring(0,filename.lastIndexOf("."));
		return tmp.substring(tmp.lastIndexOf("\\")+1,tmp.length());
	}
	/***
	 * 正则化，统一最大值
	 * @param sample
	 * @return
	 */
	private static int[] Regulize(int[] sample) {
		
		double max=GetMax(sample);
		int[] rt=new int[sample.length];
		for(int i=0;i<sample.length;++i)
		{
			double temp;
			double sound;
			sound = sample[i];
			if(max>C.smallnoise)
				temp = sound/max;
			else
				temp = 0;
			rt[i]= (int) (temp * C.maxwave);
		}
		return rt;
	}

	private static double GetMax(int[] sample) {
		// TODO Auto-generated method stub
		double max=0;
		for(int i=0;i<sample.length;++i)
		{
			if(Math.abs(sample[i])>max)
				max=Math.abs(sample[i]);
		}
		return max;
	}

	/***
	 * 取得样本文件特征值
	 * @param newsample
	 * @param standard
	 * @returnֵ
	 */
	private static Eigenvalue SameMatch(int[] newsample, int[] standard) {
		Eigenvalue rtev = new Eigenvalue(newsample,standard);
		return rtev;
	}
	
}
