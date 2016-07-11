import imagereader.IImageProcessor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class process implements IImageProcessor{

	//以下部分重写父类函数
	//提取红色色彩通道
	@Override
	public Image showChanelR(Image imageInput) {

		//取得输入图像的纵向像素数量和横向像素数量
		int high = imageInput.getHeight(null);
		int wide = imageInput.getWidth(null);
		
		//通过纵向和横向像素数量确定图像的总像素数量
		int imageSize = high * wide;
		
		//为每一个像素进行保存
		int[] imageData = new int[imageSize];
		
		//创建新的BufferImage对象，宽度高度均与输入图像相同，色彩模式为RGB
		BufferedImage imageOutput = new BufferedImage(wide,high,BufferedImage.TYPE_INT_RGB);

		//通过已有信息绘制图像
		imageOutput.getGraphics().drawImage(imageInput, 0, 0, wide, high, null);
		
		//像imageData中写入RGB数据
		imageOutput.getRGB(0, 0, wide, high, imageData, 0, wide );

		//建立存储红色色彩通道的数组
		int R[] = new int[wide * high];
		
		//提取每个像素中红色部分的值
		for(int i = 0; i < high * wide ;i++)
		{
			  int r =(imageData[i] & 0xff0000 ) >> 16;
		      R[i] = r << 16;

		}

		//将获得的红色色彩通道的值存入图像中
		imageOutput.setRGB(0, 0, wide, high, R, 0, wide);

		//返回图像
		return imageOutput;
	}

	//均大同小异，不同之处在于提取色彩通道时处理像素位置的不同
	@Override
	public Image showChanelG(Image imageInput) {
		int high = imageInput.getHeight(null);
		int wide = imageInput.getWidth(null);
		int imageSize = high * wide;
		int[] imageData = new int[imageSize];
		BufferedImage imageOutput = new BufferedImage(wide,high,BufferedImage.TYPE_INT_RGB);

		imageOutput.getGraphics().drawImage(imageInput, 0, 0, wide, high, null);
		imageOutput.getRGB(0, 0, wide, high, imageData, 0, wide );

		int G[] = new int[wide * high];
		for(int i = 0;i < high * wide;i++)
		{
			int g =(imageData[i] & 0xff0000 ) >> 16;
		    G[i] = 0 << 16 | g << 8 | 0;
		}

		imageOutput.setRGB(0, 0, wide, high, G, 0, wide);
		return imageOutput;
	}

	@Override
	public Image showChanelB(Image imageInput) {
		int high = imageInput.getHeight(null);
		int wide = imageInput.getWidth(null);
		int imageSize = high * wide;
		int[] imageData = new int[imageSize];
		BufferedImage imageOutput = new BufferedImage(wide,high,BufferedImage.TYPE_INT_RGB);

		imageOutput.getGraphics().drawImage(imageInput, 0, 0, wide, high, null);
		imageOutput.getRGB(0, 0, wide, high, imageData, 0, wide );

		int B[] = new int[wide * high];
		for(int i = 0;i < high * wide;i++)
		{
			int b = (imageData[i] & 0xff0000) >> 16;
			B[i] = 0 << 16 | 0 << 8 | b;
		}

		imageOutput.setRGB(0, 0, wide, high, B, 0, wide);
		return imageOutput;
	}

	@Override
	public Image showGray(Image imageInput) {
		int high = imageInput.getHeight(null);
		int wide = imageInput.getWidth(null);
		int imageSize = high*wide;
		int[] imageData = new int[imageSize];
		BufferedImage imageOutput = new BufferedImage(wide,high,BufferedImage.TYPE_INT_RGB);

		imageOutput.getGraphics().drawImage(imageInput, 0, 0, wide, high, null);
		imageOutput.getRGB(0, 0, wide, high, imageData, 0, wide );

		int G[] = new int[wide * high];
		for(int i=0;i<high * wide;i++)
		{
			int b = (imageData[i] & 0xff0000) >> 16;
			G[i] = b << 16 | b << 8 | b;
		}

		imageOutput.setRGB(0, 0, wide, high, G, 0, wide);
		return imageOutput;
	}


}
