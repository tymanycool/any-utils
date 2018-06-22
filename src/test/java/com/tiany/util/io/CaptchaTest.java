package com.tiany.util.io;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;




public class CaptchaTest {
    //	/**
//	 * 验证码
//	 * @param request
//	 * @param response
//	 * @param session
//	 * @throws IOException
//	 */
//	@RequestMapping("/code.jpg")
//	public void getCode3(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
//		int width = NumberUtils.toInt(request.getParameter("width"), 100);
//		int height = NumberUtils.toInt(request.getParameter("height"), 30);
//		int codeCount = NumberUtils.toInt(request.getParameter("codeCount"), 4);
//		int lineCount = NumberUtils.toInt(request.getParameter("lineCount"), 10);
//		if (width > 1000) width = 100;
//		if (height > 300) height = 30;
//		if (codeCount > 10) codeCount = 4;
//		if (lineCount > 100) lineCount = 10;
//		// 设置响应的类型格式为图片格式
//		response.setContentType("image/jpeg");
//		// 禁止图像缓存。
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
//		// 自定义参数
//		Captcha code = new Captcha(width, height, codeCount, lineCount);
//		String sessionId = session.getId();
//		RedisUtil.set("captcha_" + sessionId, code.getCode(), 60 * 30);
//		code.write(response.getOutputStream());
//	}

    @Test
    public void write() throws Exception {
        int width = 100;
		int height = 30;
		int codeCount = 4;
		int lineCount = 10;
        Captcha code = new Captcha(width, height, codeCount, lineCount);

        OutputStream os = FileUtil.getClassPathOutputStream("image2.jpg");
        code.write(os);
        System.out.println(code.getCode());

    }

    @Test
    public void getBuffImg() throws Exception {
    }

    @Test
    public void getCode() throws Exception {
    }

}