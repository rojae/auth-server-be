package utils;

import io.github.rojae.authcoreapi.common.enums.PlatformType;
import io.github.rojae.authcoreapi.domain.Account;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class AccountFixtureFactory {

    static public Account create(){
        var param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Account.class);
    }

    static public Account create(String email){
        var param = new EasyRandomParameters();
        Account account = new EasyRandom(param).nextObject(Account.class);
        account.setEmail(email);
        return account;
    }

    static public Account create(PlatformType platformType){
        var param = new EasyRandomParameters();
        Account account = new EasyRandom(param).nextObject(Account.class);
        account.setPlatformType(platformType);
        return account;
    }

    static public Account create(String email, PlatformType platformType){
        var param = new EasyRandomParameters();
        Account account = new EasyRandom(param).nextObject(Account.class);
        account.setEmail(email);
        account.setPlatformType(platformType);
        return account;
    }

}
