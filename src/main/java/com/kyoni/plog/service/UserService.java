package com.kyoni.plog.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kyoni.plog.enums.LoginVerify;
import com.kyoni.plog.service.security.MemberServiceImpl;
import com.kyoni.plog.util.FileUtil;
import com.kyoni.plog.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberServiceImpl memberService;
	
	@Value("${spring.servlet.multipart.location}")
	private String absolutePath;

	@Value("${filePath}")
	private String relativePath;
	

	int KEY_SIZE = 2048;
	
	/***
	 * sign up 비즈니스 로직
	 * @param userVO
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public String register(UserVO userVO, Model model, HttpSession session) throws ServletException, IOException {
		
		LoginVerify lv = registerInputVerify(userVO, session);
		if(lv != LoginVerify.OK) {
			model.addAttribute("status", lv.getName());
			return "redirect:/user/register";
		}
		memberService.addUser(userVO);
		return "redirect:/user/login";
	}
	
	/***
	 * userVO의 username, email, password 정규식 검증
	 * @param userVO
	 * @return
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public LoginVerify registerInputVerify(UserVO userVO, HttpSession session) throws ServletException, IOException {
		if(!getLengthCheck(userVO.getUsername(), 2, 12)) {
			return LoginVerify.USERNAME;
		}
		if(!patternCheck(userVO.getEmail(), "EMAIL")) {
			return LoginVerify.EMAIL; 
		}
		String dpw = decryptRSA((PrivateKey) session.getAttribute("__rsaPrivateKey__"), userVO.getPwd());
		if(!patternCheck(dpw, "PW")) {
			return LoginVerify.PW;
		}
		userVO.setPwd(passwordEncoder.encode(dpw));
		return LoginVerify.OK;
	}
	
	/**
	 * 데이터의 최소, 최댓값을 활용하여 일치하는지 확인
	 * @param data
	 * @param min
	 * @param max
	 * @return
	 */
	public boolean getLengthCheck(String data, int min, int max) {
		if(data.length() < min || data.length() > max) {
			return false;
		}
		return true;
	}
	
	/**
	 * 데이터의 타입에 따라 정규식 검증
	 * @param data
	 * @param type
	 * @return
	 */
	public boolean patternCheck(String data, String type) {
		if(!Pattern.matches(getPattern(type), data)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 타입에 따라 정규식 패턴 가져오기
	 * @param type
	 * @return
	 */
	public String getPattern(String type) {
		if(type.equals(LoginVerify.EMAIL.getName())) {
			return "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		}
		if(type.equals(LoginVerify.PW.getName())) {
			return "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,16}$";
		}
		return "";
	}
	
	/***
	 * 비밀번호 암호화 키 쌍 생성
	 * @param request
	 * @param response
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void createKeyPair(HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException, ServletException, IOException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(KEY_SIZE);

		KeyPair keyPair = generator.genKeyPair();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		HttpSession session = request.getSession();
		// 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
		session.setAttribute("__rsaPrivateKey__", privateKey);

		// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

		model.addAttribute("publicKeyModules", publicKeyModulus);
		model.addAttribute("publicKeyExponent", publicKeyExponent);
		
	}

	/**
	 * RSA 복호화
	 * @param privateKey
	 * @param data
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String decryptRSA(PrivateKey privateKey, String data) throws ServletException, IOException { 
		if (privateKey == null) { 
			throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다."); 
		} 
		try { 
			Cipher cipher = Cipher.getInstance("RSA"); 
			byte[] encryptedBytes = hexToByteArray(data); 
			cipher.init(Cipher.DECRYPT_MODE, privateKey); 
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes); 
			return new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
		} catch (Exception ex) { 
			return "";
		} 
	}

	/**
	 * hex to byte array
	 * @param hex
	 * @return
	 */
	public byte[] hexToByteArray(String hex) { 
		if (hex == null || hex.length() % 2 != 0) { 
			return new byte[]{}; 
		} 
		byte[] bytes = new byte[hex.length() / 2]; 
		for (int i = 0; i < hex.length(); i += 2) { 
			byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16); 
			bytes[(int) Math.floor(i / 2)] = value; 
		} 
		return bytes; 
	}
	

	public void updateUserPicture(UserVO userVO, HttpServletRequest request) throws IOException {
		FileUtil.saveFile(userVO, absolutePath, relativePath);
		memberService.updateUserPicture(userVO);
	}
	
}
