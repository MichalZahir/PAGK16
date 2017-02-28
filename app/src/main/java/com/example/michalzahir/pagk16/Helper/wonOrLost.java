package com.example.michalzahir.pagk16.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.resultActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zahirm on 2016-06-29.
 */
public class wonOrLost  {
    static public void CheckWhoWon(final Context c){
        if (MainActivity.LoggedInWithFB)
            CheckWhoWonFBusrs(c);
        else {
            if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()) && NewGameActivity.result.getFirstUserResult() > NewGameActivity.result.getSecondtUserResult()) {
                new AlertDialog.Builder(c)
                        .setTitle("مبروك!    أنت الرابح")
                        .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent i = new Intent(c, MainActivity.class);

                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        c.startActivity(i);
                                    }
                                }, 3000);

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()) && NewGameActivity.result.getSecondtUserResult() > NewGameActivity.result.getFirstUserResult()) {
                new AlertDialog.Builder(c)
                        .setTitle("مبروك!    أنت الرابح")
                        .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent i = new Intent(c, MainActivity.class);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        c.startActivity(i);
                                    }
                                }, 3000);
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else if (NewGameActivity.result.getSecondtUserResult() == NewGameActivity.result.getFirstUserResult()) {

                new AlertDialog.Builder(c)
                        .setTitle("اللعبة إنتهت بالتعادل!")
                        .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent i = new Intent(c, MainActivity.class);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        c.startActivity(i);
                                    }
                                }, 3000);
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }

            // the losin part
            else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()) && NewGameActivity.result.getSecondtUserResult() < NewGameActivity.result.getFirstUserResult()) {
                new AlertDialog.Builder(c)
                        .setTitle("للأسف اللعبة انتهت بخسارتك حظا أوفر في المرة القادمة")
                        .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent i = new Intent(c, MainActivity.class);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        c.startActivity(i);
                                    }
                                }, 3000);
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()) && NewGameActivity.result.getSecondtUserResult() > NewGameActivity.result.getFirstUserResult()) {
                new AlertDialog.Builder(c)
                        .setTitle("للأسف اللعبة انتهت بخسارتك حظا أوفر في المرة القادمة")
                        .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent i = new Intent(c, MainActivity.class);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        c.startActivity(i);
                                    }
                                }, 3000);
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        }

    }
    static public void CheckWhoWonFBusrs(final Context c) {
        if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()) && NewGameActivity.result.getFirstUserResult() > NewGameActivity.result.getSecondtUserResult()) {
            new AlertDialog.Builder(c)
                    .setTitle("مبروك!    أنت الرابح")
                    .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                    .setNeutralButton("شارك النتيجة مع أصدقائك على الفيسبوك", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resultActivity.ShowFbShareDialog("لقد ربحت ضد" + getOpponentName(),c);

                        }
                    })
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent i = new Intent(c, MainActivity.class);

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    c.startActivity(i);
                                }
                            }, 3000);

                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()) && NewGameActivity.result.getSecondtUserResult() > NewGameActivity.result.getFirstUserResult()) {
            new AlertDialog.Builder(c)
                    .setTitle("مبروك!    أنت الرابح")
                    .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                    .setNeutralButton("شارك النتيجة مع أصدقائك على الفيسبوك", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resultActivity.ShowFbShareDialog("لقد ربحت ضد" + getOpponentName(),c);

                        }
                    })
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent i = new Intent(c, MainActivity.class);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    c.startActivity(i);
                                }
                            }, 3000);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } else if (NewGameActivity.result.getSecondtUserResult() == NewGameActivity.result.getFirstUserResult()) {

            new AlertDialog.Builder(c)
                    .setTitle("اللعبة إنتهت بالتعادل!")
                    .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                    .setNeutralButton("شارك النتيجة مع أصدقائك على الفيسبوك", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resultActivity.ShowFbShareDialog("لقد تعادلت ضد" + getOpponentName(),c);

                        }
                    })
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent i = new Intent(c, MainActivity.class);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    c.startActivity(i);
                                }
                            }, 3000);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

        }

        // the losin part
        else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()) && NewGameActivity.result.getSecondtUserResult() < NewGameActivity.result.getFirstUserResult()) {
            new AlertDialog.Builder(c)
                    .setTitle("للأسف اللعبة انتهت بخسارتك حظا أوفر في المرة القادمة")
                    .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")

                    .setNeutralButton("شارك النتيجة مع أصدقائك على الفيسبوك", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resultActivity.ShowFbShareDialog("لقد خسرت ضد" + getOpponentName(),c);

                        }
                    })
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent i = new Intent(c, MainActivity.class);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    c.startActivity(i);
                                }
                            }, 3000);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        } else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()) && NewGameActivity.result.getSecondtUserResult() > NewGameActivity.result.getFirstUserResult()) {
            new AlertDialog.Builder(c)
                    .setTitle("للأسف اللعبة انتهت بخسارتك حظا أوفر في المرة القادمة")
                    .setNeutralButton("شارك النتيجة مع أصدقائك على الفيسبوك", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resultActivity.ShowFbShareDialog("لقد خسرت ضد" + getOpponentName(),c);

                        }
                    })
                    .setMessage(" OK للعودة إلى صفحتك الرئيسية اضغط الزر")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent i = new Intent(c, MainActivity.class);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    c.startActivity(i);
                                }
                            }, 3000);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }



    }
    public static String getOpponentName(){
      String opponentName = null;

        if (playerObejtID.getUserObjectID().equals(MainActivity.userName.getUserNameUSrObjectID()))
            opponentName = MainActivity.userName.getOponnentName();
        else if (playerObejtID.getUserObjectID().equals(MainActivity.userName.getOponnentUserObjectID()))
            opponentName = MainActivity.userName.getUserName();
        return opponentName;
    }

}
