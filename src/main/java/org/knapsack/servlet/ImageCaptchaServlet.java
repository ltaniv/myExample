package org.knapsack.servlet;

import org.demo.test.Configuration;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageCaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String charsLong = "23456789ABCDEFGHKLMNPRSTUVWXYZ";

	//private static String charsShort = "23456789";

	private static String chars = charsLong;

	private static String sessionKeyName ="captcha";

	public static String getSessionKeyName() {
		return sessionKeyName;
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)fc = 255;
		if (bc > 255)bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int charsLength = chars.length();

			response.setHeader("ragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);

			int width = 70;
			int height = 20;
			BufferedImage image = new BufferedImage(width, height, 1);

			Graphics g = image.getGraphics();

			Random random = new Random();

			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			g.setFont(new Font("Times New Roman", 2, 18));

			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 30; ++i) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}

			StringBuilder sRand = new StringBuilder();
			String[] fontNames = { "Times New Roman", "Arial", "Tahoma", "" };

			for (int i = 0; i < 4; ++i) {
				g.setFont(new Font(fontNames[random.nextInt(3)], 2, height));
				char rand = chars.charAt(random.nextInt(charsLength));
				sRand.append(rand);

				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(String.valueOf(rand), 16 * i + random.nextInt(6)
						+ 3, height - random.nextInt(4));
			}

			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 2; ++i) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(width);
				int yl = random.nextInt(width);
				g.drawLine(x, y, x + xl, y + yl);
			}

			request.getSession().setAttribute(sessionKeyName,sRand.toString().toLowerCase());

			g.dispose();
			try {
				Thread.sleep(100L);
			} catch (Exception localException) {
			}
			OutputStream os = response.getOutputStream();
			ImageIO.write(image, "JPEG", os);
			os.flush();
			os.close();
		} catch (Exception localException1) {}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init(ServletConfig config) throws ServletException {
		String key=config.getInitParameter("sessionKeyName");
		Configuration.SES_CAPTCHA=key;
		sessionKeyName=(key!=null&&!"".equals(key))?key:sessionKeyName;
	}
}
