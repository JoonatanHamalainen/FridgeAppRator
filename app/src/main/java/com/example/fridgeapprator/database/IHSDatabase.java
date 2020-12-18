package com.example.fridgeapprator.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fridgeapprator.dao.ProductDao;
import com.example.fridgeapprator.dao.ProductTypeDao;
import com.example.fridgeapprator.dao.ShoppingListDao;
import com.example.fridgeapprator.dao.ShoppingListProductDao;
import com.example.fridgeapprator.model.Product;
import com.example.fridgeapprator.model.ProductType;
import com.example.fridgeapprator.model.ShoppingList;
import com.example.fridgeapprator.model.ShoppingListProduct;

@TypeConverters({Converters.class})
@Database(entities = {Product.class, ProductType.class, ShoppingList.class, ShoppingListProduct.class}, version = 51, exportSchema = false)
public abstract class IHSDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract ProductTypeDao productTypeDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract ShoppingListProductDao shoppingListProductDao();


    private static IHSDatabase INSTANCE;

    public static IHSDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IHSDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IHSDatabase.class, "fridge_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }

                @SuppressWarnings("deprecation")
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }

            };


    @SuppressWarnings("deprecation")
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ShoppingListDao mDao;

        PopulateDbAsync(IHSDatabase db) {
            mDao = db.shoppingListDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.insert(new ShoppingList("Ostoslista"));

            return null;
        }
    }

}
