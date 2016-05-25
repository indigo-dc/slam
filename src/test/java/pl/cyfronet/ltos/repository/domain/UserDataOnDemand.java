package pl.cyfronet.ltos.repository.domain;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.AbstractDataOnDemand;

public class UserDataOnDemand extends AbstractDataOnDemand<User> {

    @Override
    protected int getExpectedElements() {
        return 2;
    }

    @Override
    public User getNewTransientObject(int i) {
        User user = new User();
        user.setName("name_" + i);
        user.setSurname("surname_" + i);
        user.setEmail("email_" + i + "@ltos.pl");
        return user;
    }

}
