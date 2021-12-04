package doan.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import doan.constant.GlobalConstant;

public class FileUtil {

	public static String uploadFile(MultipartFile multipartFile, HttpServletRequest request) {
		String fileName = renameFile(multipartFile.getOriginalFilename());
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			try {
				String contextRoot = request.getServletContext().getRealPath(GlobalConstant.EMPTY);
				String dirUpload = contextRoot + GlobalConstant.DIR_UPLOAD;
				File file = new File(dirUpload);
				if (!file.exists()) {
					file.mkdirs();
				}
				String filePath = dirUpload + File.separator + fileName;
				multipartFile.transferTo(new File(filePath));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return GlobalConstant.EMPTY;
			}
		}
		return fileName;
	}

	public static String renameFile(String fileName) {
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			StringBuilder bd = new StringBuilder();
			bd.append(FilenameUtils.getBaseName(fileName)).append("-").append(System.nanoTime()).append(".")
					.append(FilenameUtils.getExtension(fileName));
			return bd.toString();
		}
		return GlobalConstant.EMPTY;
	}

	public static void delFile(String fileName, HttpServletRequest request) {
		if (!fileName.equals(GlobalConstant.EMPTY)) {
			StringBuilder bd = new StringBuilder();
			bd.append(request.getServletContext().getRealPath(GlobalConstant.EMPTY)).append(GlobalConstant.DIR_UPLOAD)
					.append(File.separator).append(fileName);
			File file = new File(bd.toString());
			file.delete();
		}
	}

	// check đuôi file
	public static boolean checkFileExtension(String fileName) {
		String fileNameExtension = FilenameUtils.getExtension(fileName);
		for (String fileExtension : GlobalConstant.FILE_EXTENSION) {
			if (fileExtension.equals(fileNameExtension)) {
				return true;
			}
		}
		return false;
	}

}
