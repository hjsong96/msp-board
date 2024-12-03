package kr.msp.example.encode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DbcpEncodeController {

	/**
	 * dbcpEncode.jsp 페이지를 호출하는 메서드
	 * @return dbcpEncode.jsp 페이지를 렌더링하는 ModelAndView 객체
	 */
	@GetMapping(value = "/dbcpEncode")
	public ModelAndView dbcpEncodeGet(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dbcpEncode");
		return modelAndView;
	}

	/**
	 * dbcpEncode.jsp 페이지로부터 전달받은 파라미터를 암호화하여 값을 리턴하는 메서드
	 * @param encodeParams 암호화할 url, name, password 값이 담긴 EncodeParams DTO 객체
	 * @return 암호화한 url, name, password 값이 담긴 Encode DTO 객체
	 */
	@PostMapping(value = "/dbcpEncode")
	@ResponseBody
	public Encode dbcpEncodePost(EncodeParams encodeParams){
		CryptoDataSource cryptoDataSource = new CryptoDataSource();
		String encUrl = cryptoDataSource.encode(encodeParams.getDbcpUrl());
		String encName = cryptoDataSource.encode(encodeParams.getDbcpName());
		String encPass = cryptoDataSource.encode(encodeParams.getDbcpPasswd());
		return new Encode(encUrl, encName, encPass);
	}

}
