package com.ob.roomrxjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ob.roomrxjava.databinding.ActivityMainBinding;
import com.ob.roomrxjava.model.User;
import com.ob.roomrxjava.roomdb.UserDao;
import com.ob.roomrxjava.roomdb.UserDatabase;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    UserDatabase db;
    UserDao userDao;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Button btnGetAll = binding.btnGetAll;
        Button btnFindByName = binding.btnFindByName;
        Button btnLoadAllByIds = binding.btnLoadAllByIds;
        Button btnInsert = binding.btnInsert;
        Button btnInsertAll = binding.btnInsertAll;
        Button btnDelete = binding.btnDelete;
        Button btnUpdate = binding.btnUpdate;

        db= Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"Users")
                //.allowMainThreadQueries() Main Thread Üzerinde Çalışmasına İzin Verir Uygulama Çökmez Fakat Önerilmez
                .build();
        userDao = db.userDao();

        btnGetAll.setOnClickListener(view1 -> compositeDisposable.add(userDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::handleGetAllResponse)
        ));

        btnFindByName.setOnClickListener(view1 -> compositeDisposable.add(userDao.findByName("Onur","Bilke")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::handleFindByNameResponse)
        ));

        btnLoadAllByIds.setOnClickListener(view1 -> compositeDisposable.add(userDao.loadAllByIds(new int[]{ 3,4 })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::handleLoadAllByIdsResponse)
        ));

        btnInsert.setOnClickListener(view1 -> {
            User user = new User("Cansu","Bilke");

            compositeDisposable.add(userDao.insert(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(MainActivity.this::handleInsertResponse)
            );
        });

        btnInsertAll.setOnClickListener(view1 -> {
            User user1 = new User("Onur","Bilke");
            User user2 = new User("Cansu","Bilke");
            User user3 = new User("Ada","Bilke");
            User user4 = new User("Deniz","Bilke");

            compositeDisposable.add(userDao.insertAll(user1,user2,user3,user4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(MainActivity.this::handleInsertAllResponse)
            );
        });

        btnDelete.setOnClickListener(view1 -> {
            User user = new User("Onur","Bilke");
            user.uid = 1;

            compositeDisposable.add(userDao.delete(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(MainActivity.this::handleDeleteResponse)
            );
        });

        btnUpdate.setOnClickListener(view1 -> {
            User user = new User("On","Bi");
            user.uid = 3;

            compositeDisposable.add(userDao.update(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(MainActivity.this::handleUpdateResponse)
            );
        });
    }

    private void handleInsertAllResponse()
    {
        Toast.makeText(getApplicationContext(),"Inserted All",Toast.LENGTH_LONG).show();
    }

    private void handleLoadAllByIdsResponse(List<User> users)
    {
        for (User user:users)
        {
            System.out.println("Id : " + user.uid + " Name : " + user.firstName + " Surname : " + user.lastName);
        }
    }

    private void handleFindByNameResponse(User user)
    {
        //DB de birden fazla aynı isimde kayıt varsa ilk kayıt geliyor
        System.out.println("Id : " + user.uid + " Name : " + user.firstName + " Surname : " + user.lastName);
    }

    private void handleGetAllResponse(List<User> users)
    {
        for (User user:users)
        {
            System.out.println("Id : " + user.uid + " Name : " + user.firstName + " Surname : " + user.lastName);
        }
    }

    private void handleInsertResponse()
    {
        Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
    }

    private void handleDeleteResponse()
    {
        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
    }

    private void handleUpdateResponse()
    {
        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        compositeDisposable.clear();
    }
}