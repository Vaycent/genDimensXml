package vaycent.gendimensadapter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Please be noted:
 * 1. Just one <dimen></dimen> tag one line in your dimen.xml
 * 2. U can use two character as the unit(like: dp,sp)
 * 3. Now base on 360dp as default
 * 4. Put your values/dimens.xml in the project that you want to gen
 * 
 */



public class autoGenDimenXml {
	
	private static final String basePath="/Users/vaycent/Documents/AndroidStudioWorkSpace";
	private static final String projectName="/genDimensAdapter";
	private static final String resourcePath="/app/src/main/res";
	private static final String dimensPath="/values/dimens.xml";
	
	//This dimensSet is used to control the size that u want to gen
	private static final String[] dimensSet={"sw360dp","sw411dp","sw480dp","sw576dp","sw600dp","sw800dp"};
	
	private static final int baseOnDp=360;
	
	// ***************************** U can just modify above content ********************************
	
	public static void main(String[] args) {
		String baseOnPath=basePath+projectName+resourcePath+dimensPath;
		
		cleanAllData();
		
		initDimensPath();
		
		float[] scaleSet=initScaleSet();
		
		autoGen(baseOnPath,scaleSet);
	}
	
	private static void cleanAllData(){
		for(int i=0;i<dimensSet.length;i++){
			String tempPathStr=basePath+projectName+resourcePath+"/values-"+dimensSet[i];
			
			File checkFolderPath=new File(tempPathStr);
			File checkFilePath=new File(tempPathStr+"/dimens.xml");

			if(checkFilePath.exists()){
				checkFilePath.delete();
			}	
			
			if(checkFolderPath.exists())
				checkFolderPath.delete();
			
		}
	}
	
	
	private static void initDimensPath(){
		for(int i=0;i<dimensSet.length;i++){
			String tempPathStr=basePath+projectName+resourcePath+"/values-"+dimensSet[i];
			
			File checkFolderPath=new File(tempPathStr);
			File checkFilePath=new File(tempPathStr+"/dimens.xml");

			
			if(!checkFolderPath.exists())
				checkFolderPath.mkdirs();
			if(!checkFilePath.exists()){
				try {
					checkFilePath.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}
	
	private static float[] initScaleSet(){
		float[] scaleSet = new float[dimensSet.length];  
		
		for(int i=0;i<dimensSet.length;i++){
			int valueLeftPosition=dimensSet[i].indexOf("sw")+2;
	    	int valueRightPosition=dimensSet[i].lastIndexOf("dp");
			
			String tempPathStr=dimensSet[i];
			int dpValue=Integer.parseInt(tempPathStr.substring(valueLeftPosition, valueRightPosition));
			
			scaleSet[i]=(float)dpValue/(float)baseOnDp;
			
		}
		
		for(int i =0;i<scaleSet.length;i++){
			System.out.println("scaleSet:"+scaleSet[i]);
		}
		
		return scaleSet;
	}
	
	
//	public static void writeFile(String file, String text) {
//      PrintWriter out = null;
//      try {
//          out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
//          out.println(text);
//      } catch (IOException e) {
//          e.printStackTrace();
//      }
//      out.close();
//	}
	
	public static void writeFile(String fileStr, String text) {
		File file=new File(fileStr);
		BufferedWriter buf;
	      try {
	  	  	   buf = new BufferedWriter(new FileWriter(file, true));
	    	   buf.append(text);
	    	   buf.newLine();
	 	      buf.close();

	      } catch (IOException e) {
	          e.printStackTrace();
	      }	 	 

	}
	 
	
	
	
	
	
	private static void calculateScale(String valueStart,String valueEnd,float valueNum,float[] scaleSet){
		for(int i=0;i<scaleSet.length;i++){
			StringBuilder sb = new StringBuilder();
			

			sb.append(valueStart);
			sb.append(String.format("%.2f", (valueNum * scaleSet[i])));
			sb.append(valueEnd);
			sb.append("\n");
			
			String dimenFilePath=basePath+projectName+resourcePath+"/values-"+dimensSet[i]+"/dimens.xml";
			writeFile(dimenFilePath, sb.toString());
		}
	}
	
	private static void writeDirectly(String lineStr,float[] scaleSet){
		for(int i=0;i<scaleSet.length;i++){
			StringBuilder sb = new StringBuilder();
			sb.append(lineStr).append("\n");
			
			String dimenFilePath=basePath+projectName+resourcePath+"/values-"+dimensSet[i]+"/dimens.xml";
			writeFile(dimenFilePath, sb.toString());
		}
	}
	
	
	
	
	
	
	
	
	
	
    private static void autoGen(String baseOnPath,float[] scaleSet) {
    	
        File baseOnFile = new File(baseOnPath);     
        BufferedReader bfReader = null;
        String lineStr;
    
        try{
        	bfReader = new BufferedReader(new FileReader(baseOnFile));
        	lineStr=bfReader.readLine();
        }catch(Exception e){
			lineStr="Error";
        	e.printStackTrace();
        }
      
        while(lineStr!=null&&lineStr!="Error"){
//        	System.out.println("lineStr:"+lineStr);
        	
        	if(lineStr.contains("</dimen>")){
        		int valueLeftPosition=lineStr.indexOf(">")+1;
            	int valueRightPosition=lineStr.lastIndexOf("<")-2;

            	String valueStart = lineStr.substring(0, valueLeftPosition);
                String valueEnd = lineStr.substring(valueRightPosition);
                
                float valueNum=Float.valueOf(lineStr.substring(valueLeftPosition,valueRightPosition));  
                
                calculateScale(valueStart,valueEnd,valueNum,scaleSet);
                
                
        	}else{
        		writeDirectly(lineStr,scaleSet);
        	}
        	
        	try {
				lineStr=bfReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
    }
 

   
}
