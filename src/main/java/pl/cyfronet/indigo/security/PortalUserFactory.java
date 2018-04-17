package pl.cyfronet.indigo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class PortalUserFactory {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    public PortalUserImpl createPortalUser(PortalUserImpl.Data data) {
        PortalUserImpl portalUser = new PortalUserImpl(data);
        beanFactory.autowireBean(portalUser);
        return portalUser;
    }
}
