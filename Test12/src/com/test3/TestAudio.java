package com.test3;

import java.io.*;

import javax.sound.sampled.*;
public class TestAudio {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AePlayWave apw=new AePlayWave("d:\\111.wav");
		Thread t=new Thread(apw);
		t.start();
	}

}

class AePlayWave implements Runnable
{
	private String filename;
	public AePlayWave(String wavfile)
	{
		filename=wavfile;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		File soundfile=new File(filename);
		
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream=AudioSystem.getAudioInputStream(soundfile);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return;
		}
		
		AudioFormat format=audioInputStream.getFormat();
		SourceDataLine auline=null;
		DataLine.Info info=new DataLine.Info(SourceDataLine.class, format);
		
		try {
			auline=(SourceDataLine)AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return;
		}
		
		auline.start();
		int nBytesRead=0;
		byte [] abData=new byte[1024];
		
		try {
			while(nBytesRead!=-1)
			{
				nBytesRead=audioInputStream.read(abData,0,abData.length);
				if(nBytesRead>0)
				{
					auline.write(abData, 0, nBytesRead);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally
		{
			auline.drain();
			auline.close();
		}
	}
	
	
}

