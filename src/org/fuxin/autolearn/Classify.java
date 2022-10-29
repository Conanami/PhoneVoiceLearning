package org.fuxin.autolearn;

import java.util.ArrayList;

import org.fuxin.autolearn.C.Operator;
import org.fuxin.autolearn.C.Type;



public class Classify {

	public static WaveFileResult filter(TrainFile tf,
			ArrayList<StandWave> standlist) {
		WaveFileReader reader = new WaveFileReader(tf.file);  
		//FuOutput.sop(reader);
		WaveFileResult rtwfr = new WaveFileResult(tf.file);
		//根据电话号码决定运营商
        rtwfr.setPhonenumber(tf.phonenumber);
        Operator operator = OpMgr.findOp(tf.phonenumber);  
        rtwfr.setOper(operator);
        
		if(reader.isSuccess())
        {
        	for(int i=0;i<standlist.size();++i)
        	{
        		StandWave stand=standlist.get(i);
        		if(operator==stand.getOperator()
        		    && rtwfr.getType()!=stand.getType()
        		    && rtwfr.getType()!=Type.Unknown)
        		{
        			int result=WaveMatcher.Compare(reader,stand.data,stand.threshold,stand.sourcefile);
        			if(result==0 && rtwfr.getType()==C.Type.Undo)
        				rtwfr.setType(stand.getType());
        			else if(result==0)
        				rtwfr.setType(C.Type.Unknown);
        		}
        		
        	}
        	//循环执行完毕，一个都没有对上，则认为是正常
        	if(rtwfr.getType()==C.Type.Undo)
        		rtwfr.setType(C.Type.Zc);
        	
        	
        }  
        else{  
            System.err.println("打开录音文件失败");  
            rtwfr.setType(C.Type.Unknown);
        } 
               
        return rtwfr;
	}

	
	

}
