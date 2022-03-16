package com.kyoni.plog.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kyoni.plog.vo.UserVO;

public class FileUtil {

	public static void saveFile(UserVO vo, String directoryPath, String path) throws IOException {
		MultipartFile file = vo.getProfile();
		
		// parent directory를 찾는다.
		Path directory = Paths.get(directoryPath).toAbsolutePath().normalize();

		// directory 해당 경로까지 디렉토리를 모두 만든다.
		Files.createDirectories(directory);

		Path filePath = Paths.get(path);
		String fileName = vo.getSeq() + "";
		// 파일을 저장할 경로를 Path 객체로 받는다.
		Path targetPath = filePath.resolve(fileName).normalize();
		vo.setPicture(targetPath.toString());
		try {
			targetPath = directory.resolve(fileName).normalize();
			// 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
			if (Files.exists(targetPath)) {
				Files.delete(targetPath);
			}
			file.transferTo(targetPath);
		} catch (Exception e) {
		}
		
	}

}
