package com.br.projeto.services.impl;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projeto.entity.Account;
import com.br.projeto.entity.dto.AccountDTO;
import com.br.projeto.exceptions.business.AccountCreateException;
import com.br.projeto.exceptions.business.AccountNotFoundException;
import com.br.projeto.repository.AccountRepository;
import com.br.projeto.services.IAccountService;
import com.br.projeto.util.MessageProperties;
import com.br.projeto.util.Utility;


@Service
public class AccountServiceImpl implements IAccountService {
	
	@Autowired
	private AccountRepository repository;
	
	@Override
	public AccountDTO createNewAccount() throws IOException {
		Account account = new Account();
		account.setDocumentNumber(Utility.generateNumberDocument());
		account = repository.save(account);
		
		if(account == null) {
			throw new AccountCreateException(MessageProperties.getKey("account.not.created"));
		}
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setDocument_number(account.getDocumentNumber());
		
		return accountDTO;
	}

	@Override
	public AccountDTO informationAccount(@Valid Long id) throws Exception {
		Optional<Account> account = repository.findById(id);
		
		AccountDTO accountDTO = null;
		
		if(account.isPresent()) {
			accountDTO = new AccountDTO(account.get().getId(), account.get().getDocumentNumber());
		}else {
			throw new AccountNotFoundException(MessageProperties.getKey("account.not.find"));
		}
		
		return accountDTO;
	}
}
