package com.example.app.service;

import java.rmi.server.ExportException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dto.ChangePasswordForm;
import com.example.app.entity.User;
import com.example.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvaliable(user) && checkPasswordValid(user)) {
			user = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		return userRepository.findById(id).orElseThrow(() -> new ExportException("El usuario no existe"));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new Exception("UsernotFound in deleteUser -" + this.getClass().getName()));
		userRepository.delete(user);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User userStored = userRepository.findById(form.getId())
				.orElseThrow(() -> new Exception("El usuario no existe"));

		if (!form.getCurrentPassword().equals(userStored.getPassword())) {
			throw new Exception("La contraseña actual no coincide.");
		}
		if (form.getCurrentPassword().equals(form.getNewPassword())) {
			throw new Exception("La nueva contraseña debe ser diferente a la actual");
		}
		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("Las contraseñas no coinciden");
		}
		userStored.setPassword(form.getNewPassword());
		return userRepository.save(userStored);
	}

	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}

	private boolean checkUsernameAvaliable(User user) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("El usuario ya existe.");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("La contraseña no coincide");
		}
		return true;
	}

}
