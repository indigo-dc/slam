package pl.cyfronet.indigo.repository.domain;

import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.repository.AbstractDataOnDemand;

public class UserDataOnDemand extends AbstractDataOnDemand<User> {

    @Override
    protected int getExpectedElements() {
        return 2;
    }

    @Override
    public User getNewTransientObject(int i) {
        User user = new User();
        user.setName("name_" + i);
        user.setEmail("email_" + i + "@indigo.pl");
        return user;
    }

}
