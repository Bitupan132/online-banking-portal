package xyz.team1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.constants.Constants;
import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Account;
import xyz.team1.service.AccountService;

@RestController
@RequestMapping("/account")
@CrossOrigin("*")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private FeignClientInterface feign;

	@GetMapping("/getAll")
	private List<Account> getAllAccount(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
			String jwtToken = authorizationHeader.substring(7);
			String s = feign.validateToken(jwtToken);
			if (Constants.tokenValidString.equals(s)) {
				return accountService.getAll();
			}
			throw new Exception(Constants.tokenInvalidString);
		}catch(Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}

	}

	@PostMapping("/add")
	private Account addAccount(@RequestBody Account account, @RequestHeader("Authorization") String authorizationHeader)
			throws Exception {
		try {
			String jwtToken = authorizationHeader.substring(7);
			String s = feign.validateToken(jwtToken);
			if (Constants.tokenValidString.equals(s)) {
				return accountService.addAccount(account);
			}
			throw new Exception(Constants.tokenInvalidString);
		}catch(Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}

	}

	@GetMapping("/getAccountForUsername/{username}")
    private Object getAccountForUsername(@PathVariable String username,
    		@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
			String jwtToken = authorizationHeader.substring(7);
			String s = feign.validateToken(jwtToken);
			if (Constants.tokenValidString.equals(s)) 
			{
				try {
					return accountService.getAccountForUsername(username);
				}catch(Exception e) {
					throw new Exception("Error fetching Account Details");
				}
			}
			return Constants.tokenInvalidString;
		}catch(Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}

    }

	@PostMapping("/updateBalanceForTransaction")
	private ResponseEntity<String> updateBalanceForTransaction(@RequestBody Map<String, Object> requestData,
			 @RequestHeader("Authorization") String authorizationHeader)
					throws Exception {
				try {
					String jwtToken = authorizationHeader.substring(7);
					String s = feign.validateToken(jwtToken);
					if (Constants.tokenValidString.equals(s)) {
						String senderAccountNo = requestData.get("senderAccountNo").toString();
						String receiverAccountNo = requestData.get("receiverAccountNo").toString();
						Double transferAmount = Double.valueOf(requestData.get("transferAmount").toString());

						try {
							accountService.updateBalanceForTransaction(senderAccountNo, receiverAccountNo, transferAmount);
							return ResponseEntity.ok("Balance updated successfully!");
						} catch (Exception e) {
							throw new Exception(e.getLocalizedMessage());
						}
					}
					throw new Exception(Constants.tokenInvalidString);
				} catch (Exception e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body(e.getLocalizedMessage());
				}
		
	}
}
