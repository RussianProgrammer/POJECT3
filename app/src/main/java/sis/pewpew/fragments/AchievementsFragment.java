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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sis.pewpew.MainActivity;
import sis.pewpew.R;
import sis.pewpew.utils.AchievementInfoActivity;

import static com.google.android.gms.internal.zzt.TAG;

public class AchievementsFragment extends Fragment {

    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Locale locale = new Locale("ru");
    private String date = new SimpleDateFormat("dd.MM.yyyy", locale).format(new Date());
    public long points;
    private boolean closed;
    public String dateFromDatabase;

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
        limits.add(600);
        limits.add(1100);
        limits.add(1600);
        limits.add(2100);
        limits.add(2600);
        limits.add(3100);
        limits.add(3600);
        limits.add(4100);
        limits.add(4600);
        limits.add(5100);
        limits.add(5600);
        limits.add(6100);
        limits.add(6600);
        limits.add(7100);

        final List<Integer> ids = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            ids.add(i);
        }

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

        final List<String> colors1 = new ArrayList<>();
        colors1.add("#262427");
        colors1.add("#979329");
        colors1.add("#705544");
        colors1.add("#E3B06E");
        colors1.add("#E0A81D");
        colors1.add("#440C1A");
        colors1.add("#D00624");
        colors1.add("#DCB141");
        colors1.add("#DDD705");
        colors1.add("#DD0F3A");
        colors1.add("#778F03");
        colors1.add("#F95754");
        colors1.add("#AD7568");
        colors1.add("#C6A59A");
        colors1.add("#EA8E04");

        final List<String> colors2 = new ArrayList<>();
        colors2.add("#8B5D5B");
        colors2.add("#D7E5ED");
        colors2.add("#B09F83");
        colors2.add("#996A4C");
        colors2.add("#36680A");
        colors2.add("#6F3C40");
        colors2.add("#306026");
        colors2.add("#A59A8D");
        colors2.add("#859F03");
        colors2.add("#D7E5ED");
        colors2.add("#F45F3D");
        colors2.add("#32630F");
        colors2.add("#30130B");
        colors2.add("#6F986C");
        colors2.add("#5D6E07");

        final List<String> colors3 = new ArrayList<>();
        colors3.add("#D7E5ED");
        colors3.add("#F1C919");
        colors3.add("#D7E5ED");
        colors3.add("#311E18");
        colors3.add("#6B3D12");
        colors3.add("#6E5242");
        colors3.add("#D7E5ED");
        colors3.add("#510803");
        colors3.add("#BE8500");
        colors3.add("#111613");
        colors3.add("#FFA366");
        colors3.add("#A30002");
        colors3.add("#FEA961");
        colors3.add("#D7E5ED");
        colors3.add("#E0CE0B");

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

                if (!closed) {

                    if (points >= 100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE1", 0);
                        boolean achieve1Gotten = settings.getBoolean("achieve1Gotten", true);
                        if (achieve1Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("1").setValue(date);
                            mDatabase.child("achieves").child("1").child(user.getUid()).setValue(date);
                            showNewAchieveDialog("На этот раз что-то сладенькое.");
                        }

                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve1Gotten", false);
                        editor.apply();
                    }
                    if (points >= 600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE2", 0);
                        boolean achieve2Gotten = settings.getBoolean("achieve2Gotten", true);
                        if (achieve2Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("2").setValue(date);
                            mDatabase.child("achieves").child("2").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve2Gotten", false);
                        editor.apply();
                    }
                    if (points >= 1100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE3", 0);
                        boolean achieve3Gotten = settings.getBoolean("achieve3Gotten", true);
                        if (achieve3Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("3").setValue(date);
                            mDatabase.child("achieves").child("3").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve3Gotten", false);
                        editor.apply();
                    }
                    if (points >= 1600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE4", 0);
                        boolean achieve4Gotten = settings.getBoolean("achieve4Gotten", true);
                        if (achieve4Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("4").setValue(date);
                            mDatabase.child("achieves").child("4").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve4Gotten", false);
                        editor.apply();
                    }
                    if (points >= 2100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE5", 0);
                        boolean achieve5Gotten = settings.getBoolean("achieve5Gotten", true);
                        if (achieve5Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("5").setValue(date);
                            mDatabase.child("achieves").child("5").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve5Gotten", false);
                        editor.apply();
                    }
                    if (points >= 2600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE6", 0);
                        boolean achieve6Gotten = settings.getBoolean("achieve6Gotten", true);
                        if (achieve6Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("6").setValue(date);
                            mDatabase.child("achieves").child("6").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve6Gotten", false);
                        editor.apply();
                    }
                    if (points >= 3100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE7", 0);
                        boolean achieve7Gotten = settings.getBoolean("achieve7Gotten", true);
                        if (achieve7Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("7").setValue(date);
                            mDatabase.child("achieves").child("7").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve7Gotten", false);
                        editor.apply();
                    }
                    if (points >= 3600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE8", 0);
                        boolean achieve8Gotten = settings.getBoolean("achieve8Gotten", true);
                        if (achieve8Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("8").setValue(date);
                            mDatabase.child("achieves").child("8").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve8Gotten", false);
                        editor.apply();
                    }
                    if (points >= 4100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE9", 0);
                        boolean achieve9Gotten = settings.getBoolean("achieve9Gotten", true);
                        if (achieve9Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("9").setValue(date);
                            mDatabase.child("achieves").child("9").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve9Gotten", false);
                        editor.apply();
                    }
                    if (points >= 4600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE10", 0);
                        boolean achieve10Gotten = settings.getBoolean("achieve10Gotten", true);
                        if (achieve10Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("10").setValue(date);
                            mDatabase.child("achieves").child("10").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve10Gotten", false);
                        editor.apply();
                    }
                    if (points >= 5100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE11", 0);
                        boolean achieve11Gotten = settings.getBoolean("achieve11Gotten", true);
                        if (achieve11Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("11").setValue(date);
                            mDatabase.child("achieves").child("11").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve11Gotten", false);
                        editor.apply();
                    }
                    if (points >= 5600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE12", 0);
                        boolean achieve12Gotten = settings.getBoolean("achieve12Gotten", true);
                        if (achieve12Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("12").setValue(date);
                            mDatabase.child("achieves").child("12").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve12Gotten", false);
                        editor.apply();
                    }
                    if (points >= 6100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE13", 0);
                        boolean achieve13Gotten = settings.getBoolean("achieve13Gotten", true);
                        if (achieve13Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("13").setValue(date);
                            mDatabase.child("achieves").child("13").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve13Gotten", false);
                        editor.apply();
                    }
                    if (points >= 6600) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE14", 0);
                        boolean achieve14Gotten = settings.getBoolean("achieve14Gotten", true);
                        if (achieve14Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("14").setValue(date);
                            mDatabase.child("achieves").child("14").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve14Gotten", false);
                        editor.apply();
                    }
                    if (points >= 7100) {
                        SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE15", 0);
                        boolean achieve15Gotten = settings.getBoolean("achieve15Gotten", true);
                        if (achieve15Gotten) {
                            mDatabase.child("users").child(user.getUid()).child("achievements").child("15").setValue(date);
                            mDatabase.child("achieves").child("15").child(user.getUid()).setValue(date);
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("achieve15Gotten", false);
                        editor.apply();
                    }
                }

                for (int i = 0; i < cardViews.size(); i++) {

                    final String title = titles.get(i);
                    final CardView card = cardViews.get(i);
                    final int limit = limits.get(i);
                    final int iconId = iconIds.get(i);
                    final String color1 = colors1.get(i);
                    final String color2 = colors2.get(i);
                    final String color3 = colors3.get(i);
                    final ImageView iconImageView = iconImageViews.get(i);
                    final TextView titleTextView = titleTextViews.get(i);
                    final TextView progressTextView = progressTextViews.get(i);
                    final int id = ids.get(i) + 1;
                    if (points >= limit) {
                        card.setCardBackgroundColor(Color.parseColor(color1));
                        titleTextView.setText(title);
                        if (dataSnapshot.child("users").child(user.getUid())
                                .child("achievements").child("" + (i + 1)).getValue() != null) {
                            dateFromDatabase = dataSnapshot.child("users").child(user.getUid())
                                    .child("achievements").child("" + (i + 1)).getValue().toString();
                        } else {
                            dateFromDatabase = "Когда-то получено";
                        }
                        progressTextView.setText("Получено " + dateFromDatabase);
                        iconImageView.setImageResource(iconId);
                    } else {
                        progressTextView.setText("Выполнено на " + points * 100 / limit + "%");
                    }

                    card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (points >= limit) {
                                Intent intent = new Intent(getActivity(), AchievementInfoActivity.class);
                                intent.putExtra("TITLE", title);
                                intent.putExtra("ID", id);
                                intent.putExtra("DATE", dateFromDatabase);
                                intent.putExtra("COLOR1", color1);
                                intent.putExtra("COLOR2", color2);
                                intent.putExtra("COLOR3", color3);
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

    private void showNewAchieveDialog(String uniqueText) {
        AlertDialog.Builder newAchieveDialogDialog = new AlertDialog.Builder(getActivity());
        newAchieveDialogDialog.setTitle("Новое достижение");
        newAchieveDialogDialog.setIcon(R.drawable.unknown_achieve_icon);
        newAchieveDialogDialog.setMessage("Похоже, Вы разблокировали новое достижение. " + uniqueText);
        newAchieveDialogDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        newAchieveDialogDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        closed = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        closed = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}