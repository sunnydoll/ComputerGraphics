/**
* @Title Homework 4
* @student Zhichao Cao
* @email zc77@drexel.edu
* 
*/

//package homeWork4;

import java.io.File;
import java.util.*;

public class CG_hw4 {
	
	//static int yMax = -1;
	//static int yMin = 2001;  //For scan-line filling algorithm
	static char outputArray[][];
	static boolean easeInput = false;
	public static List<DrawPoint> pointSet;  //Unclipped points on lines
	public static List<DrawPoint> lineSet;   //Clipped points need to draw
	static String strOutput = "";  //String for output xpm file
	//string strOutputFile = "out.getX()pm";  //Output file name
	static String strInputFile = "bound-lo-sphere.smf";  //-f
	static double scalingFactor = 1.0;  //-s
	static int cClockwiseRotation = 0;  //-r
	static int xTranslation = 0;  //-m
	static int yTranslation = 0;  //-n
	static int xLowerBound = 0;  //-a
	static int yLowerBound = 0;  //-b
	static int xUpperBound = 500;  //-c
	static int yUpperBound = 500;  //-d
	static int viewXLowerBound = 0;  //-j
	static int viewYLowerBound = 0;  //-k
	static int viewXUpperBound = 500;  //-o
	static int viewYUpperBound = 500;  //-p
	static double xPRP = 0.0;  //-x
	static double yPRP = 0.0;  //-y
	static double zPRP = 1.0;  //-z
	static double xVRP = 0.0;  //-X
	static double yVRP = 0.0;  //-Y
	static double zVRP = 0.0;  //-Z
	static double xVPN = 0.0;  //-q
	static double yVPN = 0.0;  //-r
	static double zVPN = -1.0;  //-w
	static double xVUP = 0.0;  //-Q
	static double yVUP = 1.0;  //-R
	static double zVUP = 0.0;  //-W
	static double uMinVRC = -0.7;  //-u
	static double vMinVRC = -0.7;  //-v
	static double uMaxVRC = 0.7;  //-U
	static double vMaxVRC = 0.7;  //-V
	static int parallelProj = 0;  //-P  If this flag is not presented (be 0), use perspective projection, else (be 1), use parallel projection
	static double backFace = -0.6;  //-B
	static double frontFace = 0.6;  //-F
	String strPS = "";  //Content string in the .ps file
	String strSetPS[];  //Split strPS string
	int stIndex = 0;  
	int edIndex = 0;
	int stIndexOp = 0;
	String tempStrOp = "";
	int xyLnIndex = 0;  //Number of lines
	int xyThreeLnIndex = 0;
	int xyThreeLnIndexV = 0;
	int xyThreeLnIndexF = 0;
	int xyOpIndex = 0;  //Index for x,y-coop, for line command
	int negNum = 0;		//If it is negtive number of the input
	int fstPointIndex = 0;  //Temp index for first point of moveto and lineto command
	int sndPointIndex = 0;  //Temp index for second point of moveto and lineto command
	int countIndex = 0;  //Number of lines in .ps file
	String strLength = "";  //Length of pic
	String strWidth = "";  //Width of pic
	int lineType = 0;  //Type of command in .ps file. 1 for line, 2 for moveto, 3 for lineto
	static double viewX = 0;
	static double viewY = 0;
	static double tempX = 0;
	static double tempY = 0;
	static int realX = 0;
	static int realY = 0;
	static int vMinX;
	static int vMaxX;
	static int vMinY;
	static int vMaxY;
	
	public static void main(String[] args) throws Exception {
		pointSet = new ArrayList<DrawPoint>();
		lineSet = new ArrayList<DrawPoint>();
		outputArray = new char [501][501];  //Initialization of output cells		
		for (int i = 0; i <= 500; i++) 
			for (int j = 0; j <= 500; j++)
				outputArray[i][j] = ' ';
		
		if(easeInput == true) {
			strInputFile = "bound-lo-sphere.smf";
			scalingFactor = 1.0;
			cClockwiseRotation = 0;
			xTranslation = 0;
			yTranslation = 0;
			xLowerBound = 0;
			yLowerBound = 0;
			xUpperBound = 500;
			yUpperBound = 500;
			viewXLowerBound = 0;
			viewYLowerBound = 0;
			viewXUpperBound = 500;
			viewYUpperBound = 500;
			//strOutputFile = "out.getX()pm";
			xPRP = 0.0;
			yPRP = 0.0;
			zPRP = 1.0;
			xVRP = 0.0;
			yVRP = 0.0;
			zVRP = 0.0;
			xVPN = 0.0; 
		    yVPN = 0.0;
			zVPN = 1.0;
			xVUP = 0.0;
			yVUP = 1.0;
			zVUP = 0.0;
			uMinVRC = -0.7;
			vMinVRC = -0.7;
			uMaxVRC = 0.7;
			vMaxVRC = 0.7;
			parallelProj = 0;
			backFace = -0.6;
			frontFace = 0.6;
		}
		else {
			for (int i = 0; i < args.length; i++) {
				if(args[i].equals("-f")){
					strInputFile = args[i + 1];
				}
				else if(args[i].equals("-s")){
					scalingFactor = Double.parseDouble(args[i + 1]);
				}
				/*else if(tempStr == "-r")
				{
					cClockwiseRotation = Integer.parseInt(args[i + 1]);
				}*/
				else if(args[i].equals("-m")) {
					xTranslation = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-n")) {
					yTranslation = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-a")) {
					xLowerBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-b")) {
					yLowerBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-c")) {
					xUpperBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-d")) {
					yUpperBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-j")) {
					viewXLowerBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-k")) {
					viewYLowerBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-o")) {
					viewXUpperBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-p")) {
					viewYUpperBound = Integer.parseInt(args[i + 1]);
				}
				else if(args[i].equals("-x")) {
					xPRP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-y")) {
					yPRP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-z")) {
					zPRP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-X")) {
					xVRP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-Y")) {
					yVRP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-Z")) {
					zVRP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-q")) {
					xVPN = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-r")) {
					yVPN = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-w")) {
					zVPN = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-Q")) {
					xVUP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-R")) {
					yVUP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-W")) {
					zVUP = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-u")) {
					uMinVRC = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-v")) {
					vMinVRC = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-U")) {
					uMaxVRC = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-V")) {
					vMaxVRC = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-F")) {
					frontFace = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-B")) {
					backFace = Double.parseDouble(args[i + 1]);
				}
				else if(args[i].equals("-P")) {
					parallelProj = 1;
				}
			}
		}
		
		vMinX = viewXLowerBound;
		vMaxX = viewXUpperBound;
		vMinY = viewYLowerBound;
		vMaxY = viewYUpperBound;

 		Scanner scan = new Scanner(new File(strInputFile));
		
		//View port
		viewX = (double)(viewXUpperBound - viewXLowerBound) / 2;
		viewY = (double)(viewYUpperBound - viewYLowerBound) / 2;
		
		MatrixUtility paraProj = new MatrixUtility(4, 4);
		MatrixUtility persProj = new MatrixUtility(4, 4);
		
		ThreeDVector vectorPRP = new ThreeDVector(xPRP, yPRP, zPRP); //PRP
		ThreeDVector vectorVRP = new ThreeDVector(xVRP, yVRP, zVRP); //VRP
		ThreeDVector vectorVPN = new ThreeDVector(xVPN, yVPN, zVPN); //VPN
		ThreeDVector vectorVUP = new ThreeDVector(xVUP, yVUP, zVUP); //VUP
		
		//Translate VRP to the origin
		double[][] vTVRP = {
				{1, 0, 0, -vectorVRP.getX()},
				{0, 1, 0, -vectorVRP.getY()},
				{0, 0, 1, -vectorVRP.getZ()},
				{0, 0, 0, 1}
				};
		MatrixUtility mTVRP = new MatrixUtility(vTVRP);

		//Rotate
		ThreeDVector mn = vectorVPN.normalize();
		ThreeDVector mu = vectorVUP.crossMul(mn);
		mu = mu.normalize();
		ThreeDVector mv = mn.crossMul(mu);
		double[][] vR = {
				{mu.getX(), mu.getY(), mu.getZ(), 0},
				{mv.getX(), mv.getY(), mv.getZ(), 0},
				{mn.getX(), mn.getY(), mn.getZ(), 0},
				{0, 0, 0, 1}
				};
		MatrixUtility mR = new MatrixUtility(vR);
		
		//Shear
		double[][] vSHpar = {
				{1, 0, ((uMaxVRC + uMinVRC) / 2 - vectorPRP.getX()) / vectorPRP.getZ(), 0},
				{0, 1, ((vMaxVRC + vMinVRC) / 2 - vectorPRP.getY()) / vectorPRP.getZ(), 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
				};
		MatrixUtility mSHpar = new MatrixUtility(vSHpar);
		
		//Translate and scale for parallel projection
		double[][] vTpar = {
				{1, 0, 0, -(uMaxVRC + uMinVRC) / 2},
				{0, 1, 0, -(vMaxVRC + vMinVRC) / 2},
				{0, 0, 1, -frontFace},
				{0, 0, 0, 1}
				};
		MatrixUtility mTpar = new MatrixUtility(vTpar);		
		double[][] vSpar = {
				{2 / (uMaxVRC - uMinVRC), 0, 0, 0},
				{0, 2 / (vMaxVRC - vMinVRC), 0, 0},
				{0, 0, 1 / (frontFace - backFace), 0},
				{0, 0, 0, 1}
				};
		MatrixUtility mSpar = new MatrixUtility(vSpar);
		
		//Translate and scale for perspective projection
		double[][] vTPRP = {
				{1, 0, 0, -vectorPRP.getX()},
				{0, 1, 0, -vectorPRP.getY()},
				{0, 0, 1, -vectorPRP.getZ()},
				{0, 0, 0, 1}
				};
		MatrixUtility mTPRP = new MatrixUtility(vTPRP);		
		double[][] vSper = {
				{2 * (-vectorPRP.getZ()) / ((uMaxVRC - uMinVRC) * ((-vectorPRP.getZ()) + backFace)), 0, 0, 0},
				{0, 2 * (-vectorPRP.getZ()) / ((vMaxVRC - vMinVRC) * ((-vectorPRP.getZ()) + backFace)), 0, 0},
				{0, 0, -1 / ((-vectorPRP.getZ()) + backFace), 0},
				{0, 0, 0, 1}
				};
		MatrixUtility mSper = new MatrixUtility(vSper);
		
		//Final matrix for perspective projection
		double[][] vMper = {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, -1, 0}
				};		
		MatrixUtility mMper = new MatrixUtility(vMper);
		
		//Final matrix for parallel projection
		double[][] vMort = {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 1}
				};
		MatrixUtility mMort = new MatrixUtility(vMort);		
		
		if (parallelProj == 0) {
//			Nper = (Sper * (SHpar * (T(-PRP) * (R * T(-VRP)))))
			persProj = mMper.multiplication(mSper.multiplication(mSHpar.multiplication(mTPRP.multiplication(mR.multiplication(mTVRP)))));
		}
		else {
//			Npar = (Spar * (Tpar * (SHpar * (R * T(-VRP))))) 
			paraProj = mMort.multiplication(mSpar.multiplication(mTpar.multiplication(mSHpar.multiplication(mR.multiplication(mTVRP)))));
		}

		while (scan.hasNextLine()) {
			String bufferLine = scan.nextLine();
			
			if (bufferLine.trim().isEmpty() == false) {
				String[] inputLines = bufferLine.trim().split("\\s+");

				if (inputLines[0].equals("v")) {
					//Coordinate of points
					double x = Double.parseDouble(inputLines[1]);
					double y = Double.parseDouble(inputLines[2]);
					double z = Double.parseDouble(inputLines[3]);
					
					double[][] vP = {{x},{y},{z},{1}};  //Homogeneous matrix vector					
					MatrixUtility homoMatrix = new MatrixUtility(vP);  //Homogeneous matrix
					
					//Projection based on the result of matrix above in 2 types of projection
					if (parallelProj == 0) {
						MatrixUtility interMU = persProj.multiplication(homoMatrix);
						double divider = interMU.getCell(3, 0);
						tempX = interMU.getCell(0, 0) / divider;
						tempY = interMU.getCell(1, 0) / divider;
					}
					else {
						MatrixUtility interMU = paraProj.multiplication(homoMatrix);
						tempX = interMU.getCell(0, 0);
						tempY = interMU.getCell(1, 0);
					}
					//Convert into 2D coordinates
					realX = (int)((double)((tempX + 1) * viewX) + viewXLowerBound);
					realY = (int)((double)((tempY + 1) * viewY) + viewYLowerBound);
					DrawPoint drawPoint = new DrawPoint(realX, realY);
					pointSet.add(drawPoint);
				}				
				else if (inputLines[0].equals("f")) {
					//Face of points, which is made up of 3 lines
					int f1 = Integer.parseInt(inputLines[1]) - 1;
					int f2 = Integer.parseInt(inputLines[2]) - 1;
					int f3 = Integer.parseInt(inputLines[3]) - 1;					
					DrawPoint p1 = pointSet.get(f1);
					DrawPoint p2 = pointSet.get(f2);
					DrawPoint p3 = pointSet.get(f3);
					
					//Back-face culling
//					double[][] direMatrix = {
//							{p2.getX() - p1.getX(), p3.getX() - p1.getX(), p1.getX()},
//							{p2.getY() - p1.getY(), p3.getY() - p1.getY(), p1.getY()},
//							{0, 0, 1},
//							};					
//					MatrixUtility interMU = new MatrixUtility(direMatrix);
//					if(interMU.cullModel() > 0) {
//						lineSet.add(p1);
//						lineSet.add(p2);
//						lineSet.add(p3);
//						lineSet.add(p1);
//					}
					
					lineSet.add(p1);
					lineSet.add(p2);
					lineSet.add(p3);
					lineSet.add(p1);
					
					if(!lineSet.isEmpty()) {						
						for(int i = 0; i < lineSet.size() - 1; i++) {
							int tx = 0;
							int ty = 0;
							tx = lineSet.get(i).getX();
							ty = lineSet.get(i).getY();
							DrawPoint q = new DrawPoint(tx, ty);
							tx = lineSet.get(i + 1).getX();
							ty = lineSet.get(i + 1).getY();
							DrawPoint r = new DrawPoint(tx, ty);
							drawLine(q, r);
						}
					}					
					lineSet.clear();
				}
			}
			
		}
		
		scan.close();
		
		//Output as XPM file
//		strOutput = "/* XPM */\n";
//		strOutput += "static char *quad_bw[] = {\n";
//		strOutput += "/* columns rows colors chars-per-pixel */\n";
//		//strOutput += "\"" + strLength + " " + strWidth + " 2 1\",\n";
//		strOutput += "\" 501 501 2 1\",\n";
//		strOutput += "/* pixels */\n";
//		strOutput += "\"@ c #000000\",\n";
//		strOutput += "\"  c #FFFFFF\",\n";		
//				for(int i = 0; i <= (yUpperBound - yLowerBound); i++)
//				{
//					//Clipping line by Sutherland-Hodgman Algorithm 
//					strOutput += "\"";
//					for (int j = xLowerBound; j <= xUpperBound; j++)
//					{
//						strOutput += outputArray[j][i];
//						if(realWorldCoor[j][yUpperBound -i] == 1 && viewWorldCoor[j][yUpperBound -i] == 1)
//						//if(realWorldCoor[j][yUpperBound -i] == 1)
//						{
//							strOutput += "@";
//						}
//						else
//						{
//							strOutput += " ";
//						}
//					}
//					if(i == yUpperBound)
//					{
//						strOutput += "\"\n};";
//					}
//					else
//					{
//						strOutput += "\",\n";
//					}
//				}
//				System.out.println(strOutput);
		//Outputting
		System.out.println("/* XPM */");
		System.out.println("static char *quad_bw[] = {");
		System.out.println("/* columns rows colors chars-per-pixel */");
		System.out.println("\" 501 501 2 1\",");
		System.out.println("/* colors */");
		System.out.println("\"@ c #000000\",");
		System.out.println("\"  c #ffffff\",");		
		for(int i = 0; i <= 500; i++) {
			System.out.print("\"" + outputArray[0][500 - i]);
			if(i == yUpperBound - yLowerBound) {
				for(int j = 1; j <= 500; j++) {
					if(j == 500) {
//						strOutput += outputArray[j][500 - i] + "\"};";
						System.out.println(outputArray[j][500 - i] + "\"};");
					}
					else {
//						strOutput += outputArray[j][500 - i];
						System.out.print(outputArray[j][500 - i]);
					}
				}
			}
			else{
				for(int j = 1; j <= 500; j ++) {
					if(j == 500) {
//						strOutput += outputArray[j][500 -i] + "\",";
						System.out.println(outputArray[j][500 -i] + "\",");
					}
					else {
//						strOutput += outputArray[j][500 - i];
						System.out.print(outputArray[j][500 - i]);
					}
				}
			}
		}
//		System.out.println(strOutput);
	}

	public static void signPoint(int x, int y){		
		//Fill the point in pixels
		if(x >= vMinX && x <= vMaxX && y >= vMinY && y <= vMaxY) {
			outputArray[x][y] = '@';
		}
	}	

	protected static class LineClipping {
		//Class for clipping lines
		public boolean leftIn;
		public boolean rightIn;
		public boolean topIn;
		public boolean bottomIn;
		
		public LineClipping () {
			leftIn = false;
			rightIn = false;
			topIn = false;
			bottomIn = false;
		}
		 public boolean topBound(DrawPoint q, DrawPoint r) {
			 if(q.getY() <= vMaxY && r.getY() <= vMaxY) {
				 topIn = true;
			 }
			 else {
				 topIn = false;
			 }
			 return topIn;
		 }
		 public boolean bottomBound(DrawPoint q, DrawPoint r) {
			 if(q.getY() >= vMinY && r.getY() >= vMinY) {
				 bottomIn = true;
			 }
			 else {
				 bottomIn = false;
			 }
			 return bottomIn;
		 }
		 public boolean leftBound(DrawPoint q, DrawPoint r) {
			 if(q.getX() >= vMinX && r.getX() >= vMinX) {
				 leftIn = true;
			 }
			 else {
				 leftIn = false;
			 }
			 return leftIn;
		 }
		 public boolean rightBound(DrawPoint q, DrawPoint r) {
			 if(q.getX() <= vMaxX && r.getX() <= vMaxX) {
				 rightIn = true;
			 }
			 else {
				 rightIn = false;
			 }
			 return rightIn;
		 }
	}

	public static void drawLine(DrawPoint q, DrawPoint r) {
		int dx, dy, D, x, y;
		double slope;
		
		//Creating lines in different situations, using bresenham algorithm
		if(q != null && r != null) {
			// if r.x < q.x, swap r and q
			int sign = calXpoint(q.getX(), r.getX());
			if(sign < 0) { 
				int temp = 0;			
				temp = r.getX();
				r.setX(q.getX());
				q.setX(temp);			
				temp = r.getY();
				r.setY(q.getY());
				q.setY(temp);
			}		
			dx = r.getX() - q.getX();
			dy = r.getY() - q.getY();	
			y = q.getY();
			x = q.getX();		
			slope = calSlope(q.getX(), q.getY(), r.getX(), r.getY());
			if(dx == 0) { 				
				//if parallel to y axis, which means infinity slope
				for(y = q.getY(); y <= r.getY(); y++) {
					signPoint(x, y);
				}
			}		
			else if(slope == 0) { 		
				//slope = 0
				for(x = q.getX(); x <= r.getX(); x++) {
					signPoint(x, y);
				}
			}	
			else if (slope> 1) { 
				//slope > 1
				D = -2 * dx + dy;
				for(y = q.getY(); y <= r.getY(); y++) {
					signPoint(x, y);
					if(D > 0) {
						D += - 2 * dx;
					}
					else {
						D += 2 * (- dx + dy);
						x++;
					}
				}
			}		
			else if (slope < -1) {
				// slope < -1
				D = 2 * dx + dy; 					
				for(y = q.getY(); y >= r.getY(); y--) {
					signPoint(x, y);
					if(D <= 0) {
						D += 2 * dx;
					}
					else {
						D += 2 * (dy + dx);
						x++;
					}
				}
			}
			else if(slope > 0 && slope <= 1) { 
				//0 < slope <= 1
				D = 2 * dy - dx;
				for(x = q.getX(); x <= r.getX(); x++) {
					signPoint(x, y);
					if(D <= 0) {
						D += 2 * dy;
					}
					else {
						D += 2 * (dy - dx);
						y++;
					}
				}
			}
			else if (slope >= -1 && slope < 0) { 
				//-1 <= slope < 0
				D = 2 * dy + dx;
				for(x = q.getX(); x <= r.getX(); x++) {
					signPoint(x, y);
					if(D > 0) {
						D += 2 * dy;
					}
					else {
						D += 2 * (dy + dx);
						y--;
					}
				}
			}
		}
	}
	
	public static double calSlope(int qx, int qy, int rx, int ry)
	{
		//Calculate the slope of the line
		double slope = 0.0;
		if(rx - qx != 0)
			slope = (double) (ry - qy) / (rx - qx);
		else
		{
			if(ry - qy < 0)
				slope = -1000;
			else if(ry - qy > 0)
				slope = 1000;
			else if (ry - qy == 0)
				slope = 0;
		}
		return slope;
	}

	public static int calXpoint(int qx, int rx)
	{
		//Calculate the distance of points of x-coop
		if(rx - qx < 0)
		{
			return -1;
		}
		else if(rx - qx == 0)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	public static void transform(double x, double y, int sign, int index, double scal, int rota, int xTran, int yTran, int umin, int vmin, int umax, int vmax, int xl, int yl, int xu, int yu)
	{
		//Transformation of lines
//		double xScale = (double)(umax - umin) / (xu - xl);
//		double yScale = (double)(vmax - vmin) / (yu - yl);
		double tempX = x * scal;
		double tempY = y * scal;;
		double cosValue = Math.cos((rota*Math.PI)/180.0);
		double sinValue = Math.sin((rota*Math.PI)/180.0);
		x = tempX * cosValue - tempY * sinValue + xTran;
		y = tempX * sinValue + tempY * cosValue + yTran;
		/*x = (x - xl) * xScale + umin;
		y = (y - yl) * yScale + vmin;*/
	}
	
	//Class for points on 2D coordinates
	public static class DrawPoint {
		private int x;
		private int y;
		private double x3d;
		private double y3d;
		private double z3d;
		
		public DrawPoint () {
			x = 0;
			y = 0;
			this.x3d = 0.0;
			this.y3d = 0.0;
			this.z3d = 0.0;
		}
		
		public DrawPoint (int x, int y) {
			this.x = x;
			this.y = y;
			this.x3d = 0.0;
			this.y3d = 0.0;
			this.z3d = 0.0;
		}
		
		public DrawPoint(double x, double y, double z) {
			this.x = 0;
			this.y = 0;
			this.x3d = x;
			this.y3d = y;
			this.z3d = z;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public double getX3d() {
			return x3d;
		}

		public double getY3d() {
			return y3d;
		}

		public double getZ3d() {
			return z3d;
		}

		public void setX3d(double x3d) {
			this.x3d = x3d;
		}

		public void setY3d(double y3d) {
			this.y3d = y3d;
		}

		public void setZ3d(double z3d) {
			this.z3d = z3d;
		}
		
	}
	
	public static class MatrixUtility {
		private int rows; // number of rows
		private int cols; // number of columns
		private double[][] cell; // array
		
		public MatrixUtility(int m, int n) {
			this.rows = m;
			this.cols = n;
			this.cell = new double[m][n];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					this.cell[i][j] = 0;
				}
			}
		}
		
		public MatrixUtility(double[][] cell) {
			rows = cell.length;
			cols = cell[0].length;
			this.cell = new double[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					this.cell[i][j] = cell[i][j];
				}
			}
		}
		
		public double getCell(int x, int y) {
			return this.cell[x][y];
		}
		
		public void setCell(int x, int y, double value) {
			this.cell[x][y] = value;
		}
		
		public int getRows() {
			return rows;
		}

		public int getCols() {
			return cols;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public void setCols(int cols) {
			this.cols = cols;
		}
		
		public MatrixUtility multiplication(MatrixUtility bMatrix) {
			MatrixUtility cMatrix = new MatrixUtility(this.rows, bMatrix.cols);
			if (this.cols != bMatrix.rows) {
				return null;
			}
			else {
				for (int i = 0; i < cMatrix.rows; i++) {
					for (int j = 0; j < cMatrix.cols; j++) {
						for (int k = 0; k < this.cols; k++) {
							cMatrix.cell[i][j] += this.cell[i][k] * bMatrix.cell[k][j];
						}
					}
				}
			}
			return cMatrix;
		}
		
		public MatrixUtility equalSet() {
			MatrixUtility tarMatrix = new MatrixUtility(this.rows, this.cols);
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.cols; j++) {
					tarMatrix.setCell(i, j, this.getCell(i, j));
				}
			}
			return tarMatrix;
		}
		
		public double cullModel() {
			double v = 0.0;
			if(this.rows == 3 && this.cols == 3) {
				double a00 = this.getCell(0, 0);
				double a01 = this.getCell(0, 1);
				double a02 = this.getCell(0, 2);
				double a10 = this.getCell(1, 0);
				double a11 = this.getCell(1, 1);
				double a12 = this.getCell(1, 2);
				double a20 = this.getCell(2, 0);
				double a21 = this.getCell(2, 1);
				double a22 = this.getCell(2, 2);
				v = a00 * a11 * a22 + a01 * a12 * a20 + a02 * a10 * a21 - a02 * a11 * a20 - a01 * a10 * a22 - a00 * a12 * a21;
			}
			return v;
		}
		
	}
	
	//Class for vectors in 3D
	public static class ThreeDVector {
		private double x;
		private double y;
		private double z;
		
		public ThreeDVector() {
			x = 0;
			y = 0;
			z = 0;
		}
		
		public ThreeDVector(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getZ() {
			return z;
		}

		public void setX(double x) {
			this.x = x;
		}

		public void setY(double y) {
			this.y = y;
		}

		public void setZ(double z) {
			this.z = z;
		}
		
		public ThreeDVector normalize() {
			double root = Math.sqrt(x * x + y * y + z * z);
			ThreeDVector v = new ThreeDVector(x, y, z);
			if(root != 0) {
				v.x = x / root;
				v.y = y / root;
				v.z = z / root;
			}
			return v;
		}
		
		public ThreeDVector crossMul(ThreeDVector mulv) {
			ThreeDVector v = new ThreeDVector(x, y, z);
			v.x = this.y * mulv.z - this.z * mulv.y;
			v.y = this.z * mulv.x - this.x * mulv.z;
			v.z = this.x * mulv.y - this.y * mulv.x;
			return v;
		}
	}
	
	public static void beLine(int qx, int qy, int rx, int ry, int sign)
	{
		//Bresenham's algorithm
//		int length_line = 0;
		int dx, dy, D, x, y = 0;
		//cout<<qx<<","<<qy<<" "<<rx<<","<<ry<<endl;
		dx = rx - qx;
		dy = ry - qy;
		D = 2 * dy - dx;
		y = qy;
		for (x = qx; x <= rx; x++)
		{
			signPoint(x, y);
			//cout << x << "," << y << endl;
			if(D <= 0)
			{
				D += 2 * dy;
			}
			else
			{
				D += 2 * (dy -dx) ;
				y++;
			}
//			length_line++;
		}
//		lenOpPath[index] = length_line;
	}

}
