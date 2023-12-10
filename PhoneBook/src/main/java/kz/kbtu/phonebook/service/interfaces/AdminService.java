package kz.kbtu.phonebook.service.interfaces;

import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    Boolean delete(Long id, HttpServletRequest request);
}
