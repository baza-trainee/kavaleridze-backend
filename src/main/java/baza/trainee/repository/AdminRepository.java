package baza.trainee.repository;


import baza.trainee.domain.model.Admin;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface AdminRepository extends RedisDocumentRepository<Admin, String> {


    Admin findByEmail(String email);
}
