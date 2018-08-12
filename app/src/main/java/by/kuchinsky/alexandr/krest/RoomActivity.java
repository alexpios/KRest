package by.kuchinsky.alexandr.krest;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import by.kuchinsky.alexandr.krest.Database.UserRepository;
import by.kuchinsky.alexandr.krest.Local.UserDataSource;
import by.kuchinsky.alexandr.krest.Local.UserDatabase;
import by.kuchinsky.alexandr.krest.Model.User;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RoomActivity extends AppCompatActivity {
private ListView lstView;
private FloatingActionButton fab;

List<User> userList = new ArrayList<>();
ArrayAdapter adapter;
private CompositeDisposable compositeDisposable;
private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        compositeDisposable = new CompositeDisposable();

        lstView = (ListView)findViewById(R.id.lstUsers);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userList);
        registerForContextMenu(lstView);
        lstView.setAdapter(adapter);

        UserDatabase userDatabase = UserDatabase.getmInstance(this);
        userRepository = UserRepository.getmInstance(UserDataSource.getmInstance(userDatabase.userDao()));
        loadData();
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //add new user + randomim email
        
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                User user = new User("Alexandr Kuchinsky", UUID.randomUUID().toString()+"@gmail.com"
                );

                userRepository.insertUser(user);
                e.onComplete();
            }
        })   .observeOn(AndroidSchedulers.mainThread())
.subscribeOn(Schedulers.io()).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RoomActivity.this, "User Added!", Toast.LENGTH_SHORT).show();

                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RoomActivity.this, "OH, man " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loadData();
                    }
                });
    }
});
    }

    private void loadData() {

       //rx usaem
        Disposable disposable = userRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                               @Override
                               public void accept(List<User> users) throws Exception {
                                   onGetAllUserSuccess(users);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(RoomActivity.this, "Take this man  "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }


                );
        compositeDisposable.add(disposable);

    }
//deystviya s elementami
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Select action:");

        menu.add(Menu.NONE, 0,Menu.NONE, "Update info");
        menu.add(Menu.NONE, 1,Menu.NONE, " Delete info");

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
final User user = userList.get(info.position);

switch (item.getItemId()){
    case 0:
        final EditText edtName = new EditText(RoomActivity.this);
        edtName.setText(user.getName());
        edtName.setHint("Enter Your Name, man");
        new AlertDialog.Builder(RoomActivity.this).setTitle("Edit").setMessage("Edit user name")
                .setView(edtName).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(edtName.getText().toString()))
                return;
                else{
                    user.setName(edtName.getText().toString());
                    updateUser(user);
                }
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
        break;
    case 1:

        new AlertDialog.Builder(RoomActivity.this).setMessage("Do u wanna to delete this guy?"+user.toString())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               deleteUser(user);
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
        break;

}



return true;
    }

    private void deleteUser(final User user) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                userRepository.deleteUser(user);
                e.onComplete();
            }
        })   .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RoomActivity.this, "", Toast.LENGTH_SHORT).show();

                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RoomActivity.this, "OH, man " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loadData();
                    }
                });
        compositeDisposable.add(disposable);

    }

    private void updateUser(final User user) {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
              userRepository.updateUser(user);
                e.onComplete();
            }
        })   .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RoomActivity.this, "", Toast.LENGTH_SHORT).show();

                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RoomActivity.this, "OH, man " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loadData();
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void onGetAllUserSuccess(List<User> users) {
        userList.clear();
        userList.addAll(users);
        adapter.notifyDataSetChanged();



    }
}
