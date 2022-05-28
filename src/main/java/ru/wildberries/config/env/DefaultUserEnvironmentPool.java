package ru.wildberries.config.env;

public final class DefaultUserEnvironmentPool extends AbstractUserEnvironmentPool {

    private static DefaultUserEnvironmentPool instance;

    private UserEnvironment userEnvironment;

    public static DefaultUserEnvironmentPool getInstance() {
        if (instance == null) {
            synchronized (DefaultUserEnvironmentPool.class) {
                instance = new DefaultUserEnvironmentPool();
            }
        }
        return instance;
    }

    @Override
    public UserEnvironment get() {
        if (userEnvironment == null) {
            this.userEnvironment = UserEnvironmentManager.getInstance().takeUserEnvironmentFromQueue();
        }

        return userEnvironment;
    }

    @Override
    public void set(UserEnvironment userEnvironment) {
        this.userEnvironment = userEnvironment;
    }

    @Override
    public void dismiss() {
        if (userEnvironment != null) {
            UserEnvironmentManager.getInstance().putBackUserEnvironmentToQueue(userEnvironment);
            userEnvironment = null;
        }
    }

}
