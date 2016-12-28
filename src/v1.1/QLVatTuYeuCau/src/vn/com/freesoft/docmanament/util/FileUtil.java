/*
 * Vo Phu Quoi
 * B1203959
 * quoipro94@gmail.com
 */
package vn.com.freesoft.docmanament.util;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtil.
 */
public final class FileUtil {

	/**
	 * Instantiates a new file util.
	 */
	private FileUtil() {

	}

	/**
	 * Gets the name file.
	 *
	 * @param file
	 *            the file
	 * @return the name file
	 */
	public static String getNameFile(final File file) {
		String fullNameFile = file.getName();
		int i = fullNameFile.lastIndexOf(".");
		if (i != -1) {
			return fullNameFile.substring(0, i);
		}
		return fullNameFile;
	}

	/**
	 * Gets the extension.
	 *
	 * @param file
	 *            the file
	 * @return the extension
	 */
	public static String getExtension(final File file) {
		String fullNameFile = file.getName();
		int i = fullNameFile.lastIndexOf(".");
		if (i != -1) {
			return fullNameFile.substring(i + 1);
		}
		return "";
	}

	public static String getExtensionByPath(final String path) {
		int i = path.lastIndexOf(".");
		if (i != -1) {
			return path.substring(i + 1);
		}
		return "";
	}

	/**
	 * Gets the type.
	 *
	 * @param file
	 *            the file
	 * @return the type
	 */
	public static String getType(final File file) {
		@SuppressWarnings("unused")
		String fullName = file.getName();
		String fileExtension = FileUtil.getExtension(file);
		// if(fileExtension.e)
		if (fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx")) {
			fileExtension = "WORD";
		}
		if (fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx")) {
			fileExtension = "EXCEL";
		}
		if (fileExtension.equalsIgnoreCase("ppt") || fileExtension.equalsIgnoreCase("pptx")) {
			fileExtension = "POWERPOINT";
		}
		if (fileExtension.equalsIgnoreCase("txt")) {
			fileExtension = "TEXT";
		}
		return fileExtension.toUpperCase();
	}

	/**
	 * Gets the type.
	 *
	 * @param path
	 *            the path
	 * @return the type
	 */
	public static String getTypeByPath(final String path) {
		@SuppressWarnings("unused")
		String fileExtension = FileUtil.getExtensionByPath(path);
		// if(fileExtension.e)
		if (fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx")) {
			fileExtension = "WORD";
		}
		if (fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx")) {
			fileExtension = "EXCEL";
		}
		if (fileExtension.equalsIgnoreCase("ppt") || fileExtension.equalsIgnoreCase("pptx")) {
			fileExtension = "POWERPOINT";
		}
		if (fileExtension.equalsIgnoreCase("txt")) {
			fileExtension = "TEXT";
		}
		return fileExtension.toUpperCase();
	}

	public static String getName(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i != -1) {
			return fileName.substring(0, i);
		}
		return "";
	}

	public static String getExtension(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i != -1) {
			return fileName.substring(i + 1);
		}
		return "";
	}
	/*
	 * public static void main(String[] args) { File file = new
	 * File("D:/FileUpload/GstFormFileUpload/Screenshot (1)-1");
	 * System.out.print(FileUtil.getNameFile(file)); String extensionFile =
	 * FileUtil.getExtension(file); if(extensionFile.length()>0)
	 * System.out.print("." + extensionFile); }
	 */
}
