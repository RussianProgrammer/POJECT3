package sis.pewpew.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sis.pewpew.MainActivity;
import sis.pewpew.R;

import static com.google.android.gms.internal.zzt.TAG;

public class ProfileFragment extends Fragment {

    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public long pointsFromDatabase;
    private String statusFromDatabase;
    private boolean closed;
    private Locale locale = new Locale("ru");
    private String date = new SimpleDateFormat("dd.MM.yyyy", locale).format(new Date());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        SharedPreferences settings = getActivity().getSharedPreferences("PROFILE", 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
            AlertDialog.Builder profileFragmentWelcomeDialog = new AlertDialog.Builder(getActivity());
            profileFragmentWelcomeDialog.setTitle(getString(R.string.profile_fragment_name));
            profileFragmentWelcomeDialog.setCancelable(false);
            profileFragmentWelcomeDialog.setIcon(R.drawable.ic_menu_profile);
            profileFragmentWelcomeDialog.setMessage("В разделе \"Профиль\" мы собрали всю самую интересную информацию о Вас. " +
                    "А именно все Ваши очки, достижения и заслуги перед планетой. Не забудьте похвастаться ими, " +
                    "коснувшись кнопки \"Поделиться\".");
            profileFragmentWelcomeDialog.setNegativeButton("Не забуду", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            profileFragmentWelcomeDialog.show();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true);
            editor.apply();
        }

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.profile_fragment_name));

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        ValueEventListener pointsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView points = (TextView) rootView.findViewById(R.id.profile_points);
                TextView status = (TextView) rootView.findViewById(R.id.profile_status);
                TextView rank = (TextView) rootView.findViewById(R.id.profile_rank);
                TextView achieves = (TextView) rootView.findViewById(R.id.profile_achieves);
                TextView savedTrees = (TextView) rootView.findViewById(R.id.profile_saved_trees);
                TextView savedAnimals = (TextView) rootView.findViewById(R.id.profile_saved_animals);
                TextView savedPeople = (TextView) rootView.findViewById(R.id.profile_saved_people);

                if (user != null && dataSnapshot.child("users").child(user.getUid()).child("points").getValue() != null) {
                    pointsFromDatabase = (long) dataSnapshot.child("users").child(user.getUid()).child("points").getValue();

                    points.setText("" + pointsFromDatabase);
                    savedTrees.setText("" + (int) pointsFromDatabase / 500);
                    savedAnimals.setText("" + (int) pointsFromDatabase / 1000);
                    savedPeople.setText("" + (int) pointsFromDatabase / 1500);

                    if (pointsFromDatabase < 100) {
                        achieves.setText("0");
                    } else if (pointsFromDatabase >= 100 && pointsFromDatabase < 500) {
                        achieves.setText("1");
                    } else if (pointsFromDatabase >= 500 && pointsFromDatabase < 1000) {
                        achieves.setText("2");
                    } else if (pointsFromDatabase >= 1000 && pointsFromDatabase < 1500) {
                        achieves.setText("3");
                    } else if (pointsFromDatabase >= 1500 && pointsFromDatabase < 2000) {
                        achieves.setText("4");
                    } else if (pointsFromDatabase >= 2000 && pointsFromDatabase < 2500) {
                        achieves.setText("5");
                    } else if (pointsFromDatabase >= 2500 && pointsFromDatabase < 3000) {
                        achieves.setText("6");
                    } else if (pointsFromDatabase >= 3000 && pointsFromDatabase < 3500) {
                        achieves.setText("7");
                    } else if (pointsFromDatabase >= 3500 && pointsFromDatabase < 4000) {
                        achieves.setText("8");
                    } else if (pointsFromDatabase >= 4000 && pointsFromDatabase < 4500) {
                        achieves.setText("9");
                    } else if (pointsFromDatabase >= 4500 && pointsFromDatabase < 5000) {
                        achieves.setText("10");
                    } else if (pointsFromDatabase >= 5000 && pointsFromDatabase < 5500) {
                        achieves.setText("11");
                    } else if (pointsFromDatabase >= 5500 && pointsFromDatabase < 6000) {
                        achieves.setText("12");
                    } else if (pointsFromDatabase >= 6000 && pointsFromDatabase < 6500) {
                        achieves.setText("13");
                    } else if (pointsFromDatabase >= 6500 && pointsFromDatabase < 7000) {
                        achieves.setText("14");
                    } else {
                        achieves.setText("15");
                    }

                    if (!closed) {

                        if (pointsFromDatabase >= 100) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE1", 0);
                            boolean achieve1Gotten = settings.getBoolean("achieve1Gotten", true);
                            if (achieve1Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("1").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve1Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE2", 0);
                            boolean achieve2Gotten = settings.getBoolean("achieve2Gotten", true);
                            if (achieve2Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("2").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve2Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 1000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE3", 0);
                            boolean achieve3Gotten = settings.getBoolean("achieve3Gotten", true);
                            if (achieve3Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("3").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve3Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 1500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE4", 0);
                            boolean achieve4Gotten = settings.getBoolean("achieve4Gotten", true);
                            if (achieve4Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("4").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve4Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 2000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE5", 0);
                            boolean achieve5Gotten = settings.getBoolean("achieve5Gotten", true);
                            if (achieve5Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("5").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve5Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 2500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE6", 0);
                            boolean achieve6Gotten = settings.getBoolean("achieve6Gotten", true);
                            if (achieve6Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("6").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve6Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 3000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE7", 0);
                            boolean achieve7Gotten = settings.getBoolean("achieve7Gotten", true);
                            if (achieve7Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("7").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve7Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 3500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE8", 0);
                            boolean achieve8Gotten = settings.getBoolean("achieve8Gotten", true);
                            if (achieve8Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("8").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve8Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 4000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE9", 0);
                            boolean achieve9Gotten = settings.getBoolean("achieve9Gotten", true);
                            if (achieve9Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("9").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve9Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 4500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE10", 0);
                            boolean achieve10Gotten = settings.getBoolean("achieve10Gotten", true);
                            if (achieve10Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("10").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve10Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 5000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE11", 0);
                            boolean achieve11Gotten = settings.getBoolean("achieve11Gotten", true);
                            if (achieve11Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("11").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve11Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 5500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE12", 0);
                            boolean achieve12Gotten = settings.getBoolean("achieve12Gotten", true);
                            if (achieve12Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("12").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve12Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 6000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE13", 0);
                            boolean achieve13Gotten = settings.getBoolean("achieve13Gotten", true);
                            if (achieve13Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("13").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve13Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 6500) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE14", 0);
                            boolean achieve14Gotten = settings.getBoolean("achieve14Gotten", true);
                            if (achieve14Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("14").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve14Gotten", false);
                            editor.apply();
                        }
                        if (pointsFromDatabase >= 7000) {
                            SharedPreferences settings = getActivity().getSharedPreferences("ACHIEVE15", 0);
                            boolean achieve15Gotten = settings.getBoolean("achieve15Gotten", true);
                            if (achieve15Gotten) {
                                mDatabase.child("users").child(user.getUid()).child("achievements").child("15").setValue(date);
                            }
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("achieve15Gotten", false);
                            editor.apply();
                        }
                    }

                    if ((int) pointsFromDatabase < 500) {
                        rank.setText("Новичок");
                    } else if (pointsFromDatabase >= 500 && pointsFromDatabase < 1000) {
                        rank.setText("Начинающий");
                    } else if (pointsFromDatabase >= 1000 && pointsFromDatabase < 2000) {
                        rank.setText("Опытный");
                    } else if (pointsFromDatabase >= 2000 && pointsFromDatabase < 3500) {
                        rank.setText("Защитник флоры");
                    } else if (pointsFromDatabase >= 3500 && pointsFromDatabase < 5500) {
                        rank.setText("Защитник фауны");
                    } else if (pointsFromDatabase >= 5500 && pointsFromDatabase < 8000) {
                        rank.setText("Защитник людей");
                    } else if (pointsFromDatabase >= 8000 && pointsFromDatabase < 11000) {
                        rank.setText("Защитник Земли");
                    } else if (pointsFromDatabase >= 11000 && pointsFromDatabase < 14500) {
                        rank.setText("Герой");
                    } else {
                        rank.setText("Легенда");
                    }
                } else {

                    points.setText("" + 0);
                    savedTrees.setText("" + 0);
                    savedAnimals.setText("" + 0);
                    savedPeople.setText("" + 0);
                    achieves.setText("" + 0);
                    rank.setText("Нет ранга");
                }

                if (user != null && dataSnapshot.child("users").child(user.getUid()).child("status").getValue() != null) {
                    statusFromDatabase = dataSnapshot.child("users").child(user.getUid()).child("status").getValue().toString();

                    switch (statusFromDatabase) {
                        case "1":
                            status.setText("Сотрудник");
                            break;
                        case "2":
                            status.setText("Организатор");
                            break;
                        case "3":
                            status.setText("Модератор");
                            break;
                        case "4":
                            status.setText("Администратор");
                            break;
                        case "5":
                            status.setText("Создатель");
                            break;
                    }
                } else {
                    status.setText("Пользователь");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(pointsListener);

        if (user != null && user.getDisplayName() != null) {
            TextView username = (TextView) rootView.findViewById(R.id.profile_username);
            username.setText(user.getDisplayName());
        } else {
            TextView username = (TextView) rootView.findViewById(R.id.profile_username);
            username.setText("Имя пользователя");
        }

        final AlertDialog.Builder profileMainCardDialog = new AlertDialog.Builder(getActivity());
        profileMainCardDialog.setTitle("Карточка профиля");
        profileMainCardDialog.setIcon(R.drawable.profile_icon);
        profileMainCardDialog.setMessage("Это Ваша карточка профиля, на которой мы аккуратно " +
                "выгравировали Ваше имя и фамилию. У Вас мог возникнуть вопрос, " +
                "что означает пометка под именем. Отвечаем: она показывает, какую роль Вы выполняете в нашем сообществе. " +
                "Вы можете быть как обычным пользователем, так и модератором. " +
                "По всем вопросам касательно пометки Вы всегда можете обратиться к нам на нашем сайте.");
        profileMainCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profileMainCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shareProfile();
            }
        });

        final AlertDialog.Builder profilePointsCardDialog = new AlertDialog.Builder(getActivity());
        profilePointsCardDialog.setTitle("Карточка очков");
        profilePointsCardDialog.setIcon(R.drawable.profile_points_icon_2);
        profilePointsCardDialog.setMessage("Здесь показано количество заработанных Вами очков. " +
                "Их можно получать, используя экологические пункты на карте. И помните: " +
                "с каждой новой цифрой на этой карточке мы все ближе к светлому будущему.");
        profilePointsCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profilePointsCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
            }
        });

        final AlertDialog.Builder profileRankCardDialog = new AlertDialog.Builder(getActivity());
        profileRankCardDialog.setTitle("Карточка звания");
        profileRankCardDialog.setIcon(R.drawable.profile_rank_icon);
        profileRankCardDialog.setMessage("Здесь паказано Ваше звание. Чем больше очков Вы зарабатываете, тем оно выше. " +
                "Не забывайте им хвастаться время от времени.");
        profileRankCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profileRankCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
            }
        });

        final AlertDialog.Builder profileAchievesCardDialog = new AlertDialog.Builder(getActivity());
        profileAchievesCardDialog.setTitle("Карточка достижений");
        profileAchievesCardDialog.setIcon(R.drawable.profile_achieves_icon);
        profileAchievesCardDialog.setMessage("Здесь мы посчитали все собранные Вами достижения. Как Вы уже наверняка знаете, " +
                "всего их 25, но со временем их количество будет расти. За каждые 200 очков Вы получаете одно новое. " +
                "Кроме того Вы всегда можете поделиться ими.");
        profileAchievesCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profileAchievesCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
            }
        });

        final AlertDialog.Builder profileSavedTreesCardDialog = new AlertDialog.Builder(getActivity());
        profileSavedTreesCardDialog.setTitle("Карточка спасенных деревьев");
        profileSavedTreesCardDialog.setIcon(R.drawable.profile_saved_trees_icon);
        profileSavedTreesCardDialog.setMessage("Здесь отображено количество спасенных Вами деревьев. " +
                "Каждый раз зарабатывая 500 очков, Вы спасаете одно новое дерево, " +
                "которое могло погибнуть из-за токсинов и химикатов, находящихся в мусоре. " +
                "Кстати, собрав 2 млн очков, Вы спасете целый лес. Думаете, невозможно? " +
                "Просто сделайте использование приложения своей привычкой. Причем очень полезной.");
        profileSavedTreesCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profileSavedTreesCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
            }
        });

        final AlertDialog.Builder profileSavedAnimalsCardDialog = new AlertDialog.Builder(getActivity());
        profileSavedAnimalsCardDialog.setTitle("Карточка спасенных животных");
        profileSavedAnimalsCardDialog.setIcon(R.drawable.profile_saved_animals_icon);
        profileSavedAnimalsCardDialog.setMessage("Здесь показано количество спасенных Вами животных. " +
                "Каждый раз собирая 1000 очков, Вы спасаете одного зверька, а может даже и целого зверя. " +
                "Не стесняйтесь делиться своими заслугами перед фауной нашей планеты с миром.");
        profileSavedAnimalsCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profileSavedAnimalsCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
            }
        });

        final AlertDialog.Builder profileSavedPeopleCardDialog = new AlertDialog.Builder(getActivity());
        profileSavedPeopleCardDialog.setTitle("Карточка спасенных людей");
        profileSavedPeopleCardDialog.setIcon(R.drawable.profile_saved_people_icon);
        profileSavedPeopleCardDialog.setMessage("Здесь мы посчитали, сколько реальных людей Вы спасли, используя приложение. " +
                "За каждые заработанные Вами 1500 очков один человек из будущего или даже настоящего говорит Вам \"Спасибо\" " +
                "за сохраненную жизнь.");
        profileSavedPeopleCardDialog.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        profileSavedPeopleCardDialog.setPositiveButton("Поделиться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
            }
        });

        CardView profileMainCard = (CardView) rootView.findViewById(R.id.profile_main_card);
        CardView profilePointsCard = (CardView) rootView.findViewById(R.id.profile_points_card);
        CardView profileRankCard = (CardView) rootView.findViewById(R.id.profile_rank_card);
        CardView profileAchievesCard = (CardView) rootView.findViewById(R.id.profile_achieves_card);
        CardView profileSavedTreesCard = (CardView) rootView.findViewById(R.id.profile_saved_trees_card);
        CardView profileSavedAnimalsCard = (CardView) rootView.findViewById(R.id.profile_saved_animals_card);
        CardView profileSavedPeopleCard = (CardView) rootView.findViewById(R.id.profile_saved_people_card);

        profileMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileMainCardDialog.show();
            }
        });

        profilePointsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePointsCardDialog.show();
            }
        });

        profileRankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileRankCardDialog.show();
            }
        });

        profileAchievesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileAchievesCardDialog.show();
            }
        });

        profileSavedTreesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSavedTreesCardDialog.show();
            }
        });

        profileSavedAnimalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSavedAnimalsCardDialog.show();
            }
        });

        profileSavedPeopleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSavedPeopleCardDialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.profile_action_share) {
            shareProfile();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareProfile() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "У меня крутой профиль: " + (int) pointsFromDatabase;
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Поделиться профилем"));
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