package by.kuchinsky.alexandr.krest.Local;

import java.util.List;

import by.kuchinsky.alexandr.krest.Database.IUserDataSourse;
import by.kuchinsky.alexandr.krest.Model.User;
import io.reactivex.Flowable;

public class UserDataSource implements IUserDataSourse {
    private UserDAO userDAO;
public static UserDataSource mInstance;
    public UserDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
public static UserDataSource getmInstance(UserDAO userDAO){
        if (mInstance == null){
            mInstance = new UserDataSource(userDAO);
        }return mInstance;
}

    @Override
    public Flowable<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
userDAO.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
userDAO.updateUser(users);
    }

    @Override
    public void deleteUser(User user) {
userDAO.deleteUser(user);
    }

    @Override
    public void deleteAllUsers() {
userDAO.deleteAllUsers();
    }
}
