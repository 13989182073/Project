package com.java456.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.java456.entity.Client;


public interface ClientDao extends JpaRepository<Client,Integer>,JpaSpecificationExecutor<Client>  {
	
	@Query(value="select * from t_client where id = ?1",nativeQuery = true)
	public Client findId(Integer id);
	
	
}
