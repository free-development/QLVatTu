/**
 * 
 */
package vn.co.evn.materialmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quoip
 *
 */
@RestController
public class HomeController {

	@RequestMapping("/")
	public String showWelcomeMessage() {
		return "<h1><b>Welcome to MaterialManamentStub</r></h1>";
	}
}
