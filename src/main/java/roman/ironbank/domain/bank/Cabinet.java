package roman.ironbank.domain.bank;

import org.apache.log4j.Logger;
import roman.ironbank.domain.users.User;

public class Cabinet {
    private User user;
    private Logger logger;

    public Cabinet() {
    }

    public Cabinet(User user, Logger logger) {
        this.user = user;
        this.logger = logger;
    }
}
