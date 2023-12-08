package kz.kbtu.phonebook.service.impls;

import kz.kbtu.phonebook.service.interfaces.CasbinService;
import lombok.RequiredArgsConstructor;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CasbinServiceImpl implements CasbinService {
    private final Enforcer enforcer;
    @Override
    public Boolean checkAuthorize(String sub, String obj, String act) {
        return enforcer.enforce(sub, obj, act);
    }
}
