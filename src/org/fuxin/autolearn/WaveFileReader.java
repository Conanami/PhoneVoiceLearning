package org.fuxin.autolearn;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class WaveFileReader {
	
	private String filename = null;  
    private int[][] data = null;  
  
    private int len = 0;  
      
    private String chunkdescriptor = null;  
    static private int lenchunkdescriptor = 4;  
  
    private long chunksize = 0;  
    @SuppressWarnings("unused")
	static private int lenchunksize = 4;  
  
    private String waveflag = null;  
    static private int lenwaveflag = 4;  
  
    private String fmtubchunk = null;  
    static private int lenfmtubchunk = 4;  
      
    private long subchunk1size = 0;  
    @SuppressWarnings("unused")
	static private int lensubchunk1size = 4;  
      
    private int audioformat = 0;  
    @SuppressWarnings("unused")
	static private int lenaudioformat = 2;  
      
    private int numchannels = 0;  
    @SuppressWarnings("unused")
	static private int lennumchannels = 2;  
      
    private long samplerate = 0;  
    @SuppressWarnings("unused")
	static private int lensamplerate = 2;  
      
    private long byterate = 0;  
    @SuppressWarnings("unused")
	static private int lenbyterate = 4;  
      
    private int blockalign = 0;  
    @SuppressWarnings("unused")
	static private int lenblockling = 2;  
      
    private int bitspersample = 0;  
    @SuppressWarnings("unused")
	static private int lenbitspersample = 2;  
      
    private String datasubchunk = null;  
    static private int lendatasubchunk = 4;  
      
    private long subchunk2size = 0;  
    @SuppressWarnings("unused")
	static private int lensubchunk2size = 4;  
      
      
    private FileInputStream fis = null;  
    private BufferedInputStream bis = null;  
      
    private boolean issuccess = false;  
      
    public WaveFileReader(String filename) {  
          
        this.initReader(filename);  
    }  
      
    // �ж��Ƿ񴴽�wav��ȡ���ɹ�  
    public boolean isSuccess() {  
        return issuccess;  
    }  
      
    // ��ȡÿ������ı��볤�ȣ�8bit����16bit  
    public int getBitPerSample(){  
        return this.bitspersample;  
    }  

    //��ȡ�ļ���
    public String getFilename(){
        return this.filename;
    }
    // ��ȡ������  
    public long getSampleRate(){  
        return this.samplerate;  
    }  
      
    // ��ȡ�������1��?��� 2���������  
    public int getNumChannels(){  
        return this.numchannels;  
    }  
      
    // ��ȡ��ݳ��ȣ�Ҳ����һ��������ٸ�  
    public int getDataLen(){  
        return this.len;  
    }  
      
    // ��ȡ���  
    // �����һ����ά���飬[n][m]����n������ĵ�m������ֵ  
    public int[][] getData(){  
        return this.data;  
    }  
      
    private void initReader(String filename){  
        this.filename = filename;  
  
        try {  
            fis = new FileInputStream(this.filename);  
            bis = new BufferedInputStream(fis);  
  
            this.chunkdescriptor = readString(lenchunkdescriptor);  
            if(!chunkdescriptor.endsWith("RIFF"))  
                throw new IllegalArgumentException("RIFF miss, " + filename + " is not a wave file.");  
              
            this.chunksize = readLong();  
            this.waveflag = readString(lenwaveflag);  
            if(!waveflag.endsWith("WAVE"))  
                throw new IllegalArgumentException("WAVE miss, " + filename + " is not a wave file.");  
              
            this.fmtubchunk = readString(lenfmtubchunk);  
            if(!fmtubchunk.endsWith("fmt "))  
                throw new IllegalArgumentException("fmt miss, " + filename + " is not a wave file.");  
              
            this.subchunk1size = readLong();  
            this.audioformat = readInt();  
            this.numchannels = readInt();  
            this.samplerate = readLong();  
            this.byterate = readLong();  
            this.blockalign = readInt();  
            this.bitspersample = readInt();  
              
            this.datasubchunk = readString(lendatasubchunk);  
            if(!datasubchunk.endsWith("data"))  
                throw new IllegalArgumentException("data miss, " + filename + " is not a wave file.");  
            this.subchunk2size = readLong();  
              
            this.len = (int)(this.subchunk2size/(this.bitspersample/8)/this.numchannels);  
              
            this.data = new int[this.numchannels][this.len];  
              
            for(int i=0; i<this.len; ++i){  
                for(int n=0; n<this.numchannels; ++n){  
                    if(this.bitspersample == 8){  
                        this.data[n][i] = bis.read();  
                    }  
                    else if(this.bitspersample == 16){  
                        this.data[n][i] = this.readInt();  
                    }  
                }  
            }  
              
            issuccess = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try{  
            if(bis != null)  
                bis.close();  
            if(fis != null)  
                fis.close();  
            }  
            catch(Exception e1){  
                e1.printStackTrace();  
            }  
        }  
    }  
      
    private String readString(int len){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String(buf);  
    }  
      
    private int readInt(){  
        byte[] buf = new byte[2];  
        int res = 0;  
        try {  
            if(bis.read(buf)!=2)  
                throw new IOException("no more data!!!");  
            res = (buf[0]&0x000000FF) | (((int)buf[1])<<8);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
      
    private long readLong(){  
        long res = 0;  
        try {  
            long[] l = new long[4];  
            for(int i=0; i<4; ++i){  
                l[i] = bis.read();  
                if(l[i]==-1){  
                    throw new IOException("no more data!!!");  
                }  
            }  
            res = l[0] | (l[1]<<8) | (l[2]<<16) | (l[3]<<24);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
      
    @SuppressWarnings("unused")
	private byte[] readBytes(int len){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buf;  
    }

	@Override
	public String toString() {
		return "WaveFileReader [filename=" + filename + ", data="
				+ Arrays.toString(data[0]) + ", len=" + len + ", chunkdescriptor="
				+ chunkdescriptor + ", chunksize=" + chunksize + ", waveflag="
				+ waveflag + ", fmtubchunk=" + fmtubchunk + ", subchunk1size="
				+ subchunk1size + ", audioformat=" + audioformat
				+ ", numchannels=" + numchannels + ", samplerate=" + samplerate
				+ ", byterate=" + byterate + ", blockalign=" + blockalign
				+ ", bitspersample=" + bitspersample + ", datasubchunk="
				+ datasubchunk + ", subchunk2size=" + subchunk2size + ", fis="
				+ fis + ", bis=" + bis + ", issuccess=" + issuccess + "]";
	}  
    
}  

