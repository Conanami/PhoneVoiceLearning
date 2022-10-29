package org.fuxin.autolearn;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadTrainFile {

	public static TrainList read(String trainfile) {
		 	int i;
		 	TrainList rtlist = new TrainList();
	        Sheet sheet;
	        Workbook book;
	        Cell cell1,cell2,cell3,cell4;
	        System.out.println("try to read "+trainfile);
	        try { 
	            //t.xls为要读取的excel文件名
	            book= Workbook.getWorkbook(new File(trainfile)); 
	             
	            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
	            sheet=book.getSheet(0); 
	            i=0;         
	            
	            while(true)
	            {
	                //获取每一行的单元格 
	                cell1=sheet.getCell(0,i);//（列，行）
	                cell2=sheet.getCell(1,i);
	                cell3=sheet.getCell(2,i);
	                cell4=sheet.getCell(4,i);
	                TrainFile tf= new TrainFile(cell1.getContents(),
	                		cell2.getContents(),
	                		cell3.getContents(),
	                		cell4.getContents());
	                rtlist.list.add(tf);
	                if("".equals(cell1.getContents())==true)    //如果读取的数据为空
	                    break;
	                System.out.println(cell1.getContents()+"\t"+cell2.getContents()+"\t"+cell3.getContents()); 
	                i++;
	            }
	            book.close(); 
	        }
	        catch(Exception e)  { }
			return rtlist;
	}

}
