import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import imagereader.IImageIO;


public  class imageIO implements IImageIO
{

	//分别用来存储每个像素的颜色数值信息
	private int[] imageData;
	private int[] red;
	private int[] green;
	private int[] blue;

	//从文件信息中获得的图像文件的基本性质，包括横向像素数量、纵向像素数量、色彩位数和文件占用字节数
	private int wide;
	private int high;
	private int colorType;
	private int imageSize;

	//一下部分重写父类函数
	@Override
	public Image myRead(String filePath) throws IOException
	{
		//用于读取图片文件并应用已有接口进行处理
		FileInputStream fileOutput = null;
		filePath = "/Users/SJD/Desktop/1.bmp";

		try
		{
			//读取对应路径的文件
			fileOutput = new FileInputStream(filePath);

			//读取位图文件头
			int imageStart = 14;
			byte imageS[] = new byte[imageStart];
			fileOutput.read(imageS, 0, imageStart);

			//读取位图信息部分
			int imageInformation = 40;
			byte imageI[] = new byte[imageInformation];

			//从偏移值0开始，逐字节读取长度为imageInformation的信息
			fileOutput.read(imageI, 0, imageInformation);

			//通过位运算的方式将读到的小端存储的二进制信息转换为int格式信息
			wide = ((int)imageI[4] & 0xff ) | (((int)imageI[5] & 0xff) << 8)
					| (((int)imageI[6] & 0xff) << 16)|(((int)imageI[7] & 0xff) << 24);
			high = ((int)imageI[8] & 0xff ) | (((int)imageI[9] & 0xff) << 8)
					| (((int)imageI[10] & 0xff) << 16) | (((int)imageI[11] & 0xff) << 24);
			colorType = ((int)imageI[14] & 0xff) | (((int)imageI[15] & 0xff) << 8);
			imageSize = ((int)imageI[20] & 0xff ) | (((int)imageI[21] & 0xff) << 8)
						|(((int)imageI[22] & 0xff) << 16) | (((int)imageI[23] & 0xff) << 24);

			//如果是真彩色
			 if (colorType == 24)
			 {

				 	//用来计算补位
		            int Buwei = (imageSize / high) - wide * 3;

		            //初始化数组
		            imageData = new int[high * wide];
		            red = new int[high * wide];
		            green = new int[high * wide];
		            blue = new int[high * wide];

		            //读取文件数据，包括补位的数据
		            byte threeColors[] = new byte[(wide + Buwei) * 3 * high];
		            fileOutput.read(threeColors, 0, (wide + Buwei) * 3 * high);

		            //通过Pianyi存储threeColors所在的相对位置
		            int Pianyi = 0;

		            //逐个像素提取颜色信息并保存
		            for (int j = 0; j < high; j++)
		            {
		                   for (int i = 0; i < wide; i++)
		                   {
		                     imageData[wide * (high - j - 1) + i] = (255 & 0xff) << 24
		                               | (((int) threeColors[Pianyi + 2] & 0xff) << 16)
		                              | (((int) threeColors[Pianyi + 1] & 0xff) << 8)
		                               | (int) threeColors[Pianyi] & 0xff;
		                     blue[wide * (high - j - 1) + i]=(int) threeColors[Pianyi] & 0xff;
		                     green[wide * (high - j - 1) + i]=(int) threeColors[Pianyi+1] & 0xff;
		                     red[wide * (high - j - 1) + i]=(int) threeColors[Pianyi+2] & 0xff;

		                     //读取下一个像素
		                     Pianyi = Pianyi + 3;
		                   }

		                   //跳过补位
		                   Pianyi = Pianyi + Buwei;
		            }
			 }

		}catch(Exception e)
		{
			//捕获异常
			e.printStackTrace();
		}
		finally
		{
			if(fileOutput != null)
				fileOutput.close();
		}

		//创建一个又刚刚获取到的信息绘制的图像并返回
		return (Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(wide,high,imageData,0,wide)));
	}

	//读取一个Image对象并将其以JPEG格式输出至指定位置
	public Image myWrite(Image imageInput, String filePath) throws IOException {

		//输出文件
		File outFile = new File(filePath);

		//建立一个宽度和高度与Image相同的，颜色模式为RGB的BufferImage的对象
		BufferedImage bufimage = new BufferedImage(imageInput.getWidth(null), imageInput.getHeight(null),BufferedImage.TYPE_INT_RGB);

		//将刚刚创建的BufferImage对象按Image的形式绘制出来
		bufimage.getGraphics().drawImage(imageInput, 0, 0, imageInput.getWidth(null), imageInput.getHeight(null), null);
		try{
			//写文件到指定位置
			ImageIO.write(bufimage, "JPEG", outFile);
		}catch(IOException a){
			a.printStackTrace();
		}
		return null;
	}
}
