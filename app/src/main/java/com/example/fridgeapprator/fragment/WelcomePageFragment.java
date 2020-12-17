package com.example.fridgeapprator.fragment;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fridgeapprator.R;
import com.example.fridgeapprator.model.ProductTypeWithProducts;
import com.example.fridgeapprator.viewModel.ProductTypeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class WelcomePageFragment extends Fragment {
    private ProductTypeViewModel productTypeViewModel;
    private boolean wasExecuted = false;
    private static final String CHANNEL_ID = "channelID";
    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat;

    public WelcomePageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_page_fragment, container, false);
        Button toNewShoppingList = v.findViewById(R.id.button_to_new_shopping_list);
        Button toAddProduct = v.findViewById(R.id.button_to_add_product);
        Button toInstructions = v.findViewById(R.id.button_to_instructions);
        simpleDateFormat = new SimpleDateFormat(pattern);
        //fridgeProductListAdapter = new FridgeProductListAdapter(inflater.getContext());

        toAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                //transaction.remove(fragment).commit(); // wipe the old
                FridgeProductListFragment fridgeProductListFragment = new FridgeProductListFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fridgeProductListFragment)
                        .commit();
            }
        });

        toNewShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                //transaction.remove(fragment).commit(); // wipe the old
                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, shoppingListFragment)
                        .commit();
            }
        });

        toInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                //transaction.remove(fragment).commit(); // wipe the old
                InstructionsFragment instructionsFragment = new InstructionsFragment();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, instructionsFragment)
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productTypeViewModel = new ViewModelProvider(getActivity()).get(ProductTypeViewModel.class);
        System.out.println(Calendar.getInstance().getTime() + "time printti");

        productTypeViewModel.getAllProductTypes().observe(getViewLifecycleOwner(), productTypes -> {

           if (productTypeViewModel.getAllProductTypes().getValue() != null && !wasExecuted) {
               wasExecuted = true;
               int counter = 0;

               String stringDate = simpleDateFormat.format(new Date());
               Date date = java.sql.Date.valueOf(stringDate);

               //2 pv nykyisest päöiväst
               final long MILLIS_IN_3_DAYS = (1000 * 60 * 60 * 24) * 3;
               Date date2 = new Date(date.getTime() + MILLIS_IN_3_DAYS);
               StringBuilder result = new StringBuilder();

               List<ProductTypeWithProducts> productTypeWithProductsList = productTypeViewModel.getAllProductTypes().getValue();
               //loop label used for breaking out of the outer loop
               outerloop:
               for (int i = 0; i < productTypeWithProductsList.size(); i++) {
                   for (int j = 0; j < productTypeWithProductsList.get(i).products.size(); j++) {
                       if (productTypeWithProductsList.get(i).products.get(j).getExpirationDate().before(date2)) {
                            result.append(productTypeWithProductsList.get(i).productType.getProductTypeName()).append(": ").append(productTypeWithProductsList.get(i).products.get(j).getExpirationDate()).append("<br>");
                            counter++;
                            if (counter == 9) {
                                break outerloop;
                            }
                       }
                   }
               }

               Spanned formattedResult;

               if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) formattedResult = Html.fromHtml(result.toString());
               else formattedResult = Html.fromHtml(result.toString(), Html.FROM_HTML_MODE_LEGACY);

               NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
               createNotificationChannel();
               NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                       .setSmallIcon(R.drawable.check)
                       .setContentTitle("Joko vanhetunteita tai vanhenevia tuotteita")
                       .setContentText(formattedResult)
                       .setStyle(new NotificationCompat.BigTextStyle().bigText(formattedResult).setBigContentTitle("Joko vanhetunteita tai vanhenevia tuotteita"))
                       .setPriority(NotificationCompat.PRIORITY_DEFAULT);

               notificationManager.notify(0, builder.build());

           }
        });

        //seuraavat tuotteet menevät vanhaksi lähiaikoina:
        //ProductType.getName() + product.getPvm()

        //shoppingListViewModel.getAllShoppingListProducts();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Hei hei";
            String description = "Terve terve";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
