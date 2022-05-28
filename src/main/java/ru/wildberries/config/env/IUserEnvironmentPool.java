package ru.wildberries.config.env;

public interface IUserEnvironmentPool {

    UserEnvironment get();

    void set(UserEnvironment userEnvironment);

    void dismiss();

}
