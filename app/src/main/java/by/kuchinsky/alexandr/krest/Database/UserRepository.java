package by.kuchinsky.alexandr.krest.Database;

import java.util.List;

import by.kuchinsky.alexandr.krest.Model.User;
import io.reactivex.Flowable;

public class UserRepository implements IUserDataSourse {

    private IUserDataSourse mLocalDataSource;
    private static UserRepository mInstance;

    public UserRepository(IUserDataSourse mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }
public static UserRepository getmInstance (IUserDataSourse mLocalDataSource){
        if (mInstance == null){
mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;



}
    @Override
    public Flowable<User> getUserById(int userId) {
        return mLocalDataSource.getUserById(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        mLocalDataSource.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void deleteUser(User user) {
        mLocalDataSource.deleteUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();
    }
}
