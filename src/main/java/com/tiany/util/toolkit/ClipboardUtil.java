package com.tiany.util.toolkit;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public abstract class ClipboardUtil {
	/**
	 * 从剪切板获得图片
	 */
	public static Image getClipboardImage(){
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable cc = sysc.getContents(null);
		if (cc == null) {
			return null;
		} else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor)) {
			try {
				return (Image) cc.getTransferData(DataFlavor.imageFlavor);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 从剪切板获得文字
	 * 
	 * @return
	 */
	public static String getClipboardText() {
		String ret = "";
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipTf = sysClip.getContents(null);
		if (clipTf != null) {
			// 检查内容是否为文本类型
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	/**
	 * 复制图片到粘贴板
	 */
	public static void setClipboard(final Image image) {
		Transferable trans = new Transferable() {
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}

			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}
		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
	}

	/**
	 * 将字符串复制到剪切板
	 */
	public static void setClipboard(String text) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tText = new StringSelection(text);
		clip.setContents(tText, null);
	}
}
