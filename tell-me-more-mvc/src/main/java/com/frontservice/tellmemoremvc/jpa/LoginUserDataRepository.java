package com.frontservice.tellmemoremvc.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frontservice.tellmemoremvc.bean.LoginUser;


public interface LoginUserDataRepository extends JpaRepository<LoginUser,Integer> {
	
	Optional<LoginUser> findByName(String username);
}
