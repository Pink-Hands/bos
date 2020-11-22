<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Random"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.awt.Color"%>
<%@ page import="java.awt.Font"%>
<%@ page import="java.awt.Graphics"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="javax.imageio.ImageIO"%>
<%
	int width = 80;
	int height = 32;
	//创建图片
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics g = image.getGraphics();
	//设置背景色
	g.setColor(new Color(0xDCDCDC));
	g.fillRect(0, 0, width, height);
	//边界颜色
	g.setColor(Color.black);
	g.drawRect(0, 0, width - 1, height - 1);
	//随机数,16进制
	Random rdm = new Random();
	String hash1 = Integer.toHexString(rdm.nextInt());
	//随机小点,模糊化图片
	for (int i = 0; i < 50; i++) {
		int x = rdm.nextInt(width);
		int y = rdm.nextInt(height);
		g.drawOval(x, y, 0, 0);
	}
	//取前四位
	String capstr = hash1.substring(0, 4);
	session.setAttribute("key", capstr);//存进key中
	g.setColor(new Color(0, 100, 0));
	g.setFont(new Font("Candara", Font.BOLD, 24));
	g.drawString(capstr, 8, 24);
	g.dispose();
	response.setContentType("image/jpeg");
	out.clear();
	out = pageContext.pushBody();
	OutputStream strm = response.getOutputStream();
	ImageIO.write(image, "jpeg", strm);
	strm.close();
%>