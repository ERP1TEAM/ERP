package com.quickkoala;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class videopage {

	@GetMapping("video")
	public String video() {
		return "video";
	}
}
