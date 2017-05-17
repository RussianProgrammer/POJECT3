package sis.pewpew.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sis.pewpew.MainActivity;
import sis.pewpew.R;
import sis.pewpew.utils.AcheivementInfoActivity;

import static com.google.android.gms.internal.zzt.TAG;

public class AchievementsFragment extends Fragment {

    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public long points;
    public String date;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVEMENTS", 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
            AlertDialog.Builder achievementsFragmentWelcomeDialog = new AlertDialog.Builder(getActivity());
            achievementsFragmentWelcomeDialog.setTitle(getString(R.string.achievements_fragment_name));
            achievementsFragmentWelcomeDialog.setCancelable(false);
            achievementsFragmentWelcomeDialog.setIcon(R.drawable.ic_menu_achievements);
            achievementsFragmentWelcomeDialog.setMessage("В разделе \"Достижения\" Вы сможете наблюдать за своим личным прогрессом и текущем рангом, " +
                    "а также исследовать полученные награды.");
            achievementsFragmentWelcomeDialog.setNegativeButton("Понятно", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            achievementsFragmentWelcomeDialog.show();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true);
            editor.apply();
        }

        final View rootView = inflater.inflate(R.layout.fragment_achievements, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.achievements_fragment_name));


        //ВНИМАНИЕ! ОТКАЗ ОТ ИСПОЛЬЗОВАНИЯ RECYCLERVIEW ИЛИ LISTVIEW БЫЛ ПРИНЯТ ИЗ-ЗА НЕВОЗМОЖНОСТИ ИСПОЛЬЗОВАНИЯ
        //ПРЕИМУЩЕСТВ ОБЛАЧНОГО ХРАНИЛИЩА ДЛЯ НЕОБХОДИМОГО ФУНКЦИОНАЛА.

        final List<String> titles = new ArrayList<>();
        titles.add("Мангостин");
        titles.add("Дуриан");
        titles.add("Купуасу");
        titles.add("Тамаринд");
        titles.add("Цитрон");
        titles.add("Жаботикаба");
        titles.add("Рамбутан");
        titles.add("Лонган");
        titles.add("Карамбола");
        titles.add("Питайя");
        titles.add("Гуава");
        titles.add("Чомпу");
        titles.add("Салак");
        titles.add("Баиль");
        titles.add("Кивано");

        final List<TextView> titleTextViews = new ArrayList<>();
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_1_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_2_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_3_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_4_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_5_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_6_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_7_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_8_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_9_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_10_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_11_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_12_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_13_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_14_title));
        titleTextViews.add((TextView) rootView.findViewById(R.id.achieve_15_title));

        final List<TextView> progressTextViews = new ArrayList<>();
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_1_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_2_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_3_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_4_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_5_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_6_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_7_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_8_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_9_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_10_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_11_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_12_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_13_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_14_progress));
        progressTextViews.add((TextView) rootView.findViewById(R.id.achieve_15_progress));

        final List<Integer> limits = new ArrayList<>();
        limits.add(100);
        limits.add(500);
        limits.add(1000);
        limits.add(1500);
        limits.add(2000);
        limits.add(2500);
        limits.add(3000);
        limits.add(3500);
        limits.add(4000);
        limits.add(4500);
        limits.add(5000);
        limits.add(5500);
        limits.add(6000);
        limits.add(6500);
        limits.add(7000);

        final List<Integer> iconIds = new ArrayList<>();
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);
        iconIds.add(R.drawable.profile_saved_trees_icon);

        final List<String> colors = new ArrayList<>();
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");
        colors.add("#FBC02D");

        final List<ImageView> iconImageViews = new ArrayList<>();
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_1_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_2_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_3_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_4_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_5_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_6_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_7_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_8_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_9_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_10_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_11_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_12_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_13_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_14_icon));
        iconImageViews.add((ImageView) rootView.findViewById(R.id.achieve_15_icon));

        final List<CardView> cardViews = new ArrayList<>();
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_1_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_2_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_3_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_4_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_5_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_6_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_7_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_8_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_9_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_10_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_11_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_12_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_13_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_14_card));
        cardViews.add((CardView) rootView.findViewById(R.id.achieve_15_card));

        mDatabase.keepSynced(true);

        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users").child(user.getUid()).child("points").getValue() != null) {
                    points = (long) dataSnapshot.child("users").child(user.getUid()).child("points").getValue();
                } else {
                    points = 0;
                }

                for (int i = 0; i < cardViews.size(); i++) {

                    final String title = titles.get(i);
                    final CardView card = cardViews.get(i);
                    final int limit = limits.get(i);
                    final int iconId = iconIds.get(i);
                    final String color = colors.get(i);
                    final ImageView iconImageView = iconImageViews.get(i);
                    final TextView titleTextView = titleTextViews.get(i);
                    final TextView progressTextView = progressTextViews.get(i);
                    if (points >= limit) {
                        card.setCardBackgroundColor(Color.parseColor(color));
                        titleTextView.setText(title);
                        if (dataSnapshot.child("users").child(user.getUid())
                                .child("achievements").child("" + (i + 1)).getValue() != null) {
                            date = "Получено " + dataSnapshot.child("users").child(user.getUid())
                                    .child("achievements").child("" + (i + 1)).getValue().toString();
                        } else {
                            date = "Когда-то получено";
                        }
                        progressTextView.setText(date);
                        iconImageView.setImageResource(iconId);
                    } else {
                        progressTextView.setText("Выполнено на " + points * 100 / limit + "%");
                    }

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (points >= limit) {
                                Intent intent = new Intent(getActivity(), AcheivementInfoActivity.class);
                                intent.putExtra("TITLE", title);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Чтобы открыть это достижение, " +
                                        "необходимо заработать еще " + (limit - points) + " очков.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);


        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}