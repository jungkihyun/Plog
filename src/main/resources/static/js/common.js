/**
 * 
 */
 
 
 /*
입력값 검증 성공여부 체크 함수
true / false 반환
*/
function messageVerfiy() {
	var text = "";
	var result = true;
	if(getMessage(".username") != "") {
		text = "닉네임을";
		result = false;
	} else if(getMessage(".email") != "") {
		text = "이메일을";
		result = false;
	} else if(getMessage(".password") != "") {
		text = "비밀번호를";
		result = false;
	} else if(getMessage(".password-repeat") != "") {
		text = "비밀번호 재확인을";
		result = false;
	}
	if(result === false) {
		checkAlert(text);
	}
	return result;
}

/*
입력값 검증 실패했을 경우 나오는 메세지 가져오는 함수
*/
function getMessage(ele) {
	return $(ele+"-message").text();
}

/*
메세지를 받아 alert출력하는 함수
*/
function checkAlert(m) {
	if(m != "") {
		alert(m + " 확인해주세요.");
	}
}

/*
type 0 : hide
type 1 : show
type에 따라 경고 메세지 출력 여부 결정하는 함수
*/
function messageShowAndHide(ele, type, t) {
	if(type == 1) {
		$(ele+"-section").show();
	} else {
		$(ele+"-section").hide();
	}
	$(ele+"-message").text(t);
}

/*
input value의 길이 구하는 함수
*/
function getValLength(v) {
	return v.length;
}

/*
type 0 : 는
type 1 : 은
type에 따라 입력값 길이 메세지 가져오는 함수
*/
function getLengthText(target, type, min, max) {
	if(type == 0) {
		return target + "는 " + min + "~" + max + "자 내외로 설정 가능합니다.";
	} else {
		return target + "은 " + min + "~" + max + "자 내외로 설정 가능합니다.";
	}
}

/*
전달받은 element의 길이가 최소, 최대 조건에 맞는지 확인하는 함수
*/
function getLengthCheck(ele, min, max) {
	var v = getValLength(ele);
	if(v == 0) {
		return true;
	} else if(v < min || v >= max) {
		return false;
	} else {
		return true;
	}
}

/*
이메일 정규식 함수
*/
function emailExp(v) {
	var e = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
	return e.test(v);
}

/*
비밀번호 정규식 함수
*/
function passwordExp(v) {
	var e = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
	return e.test(v);
}


var securedPassword = '';
/*
RSA 암호화
*/
function validateEncryptedForm(value) {
	try {
		var rsa = new RSAKey();
		rsa.setPublic(publicKeyModules, publicKeyExponent);
		securedPassword = rsa.encrypt(value);
	} catch(err) {
		alert(err);
		return false;
	}
	return true;
}