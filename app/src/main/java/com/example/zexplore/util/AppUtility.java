package com.example.zexplore.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;

import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Notification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static android.net.Uri.fromFile;
import static android.util.Base64.encodeToString;

public class AppUtility {
    public static String setDateString(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return format.format(date);
    }

    public static Date getDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return calendar.getTime();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static void deleteFile(final String path) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                if (path != null) {
                    File file = new File(path);
                    if (file.exists()) {
                        file.getCanonicalFile().deleteOnExit();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public static void okDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }

    public static String saveImage(Context context, Bitmap bitmap, String sub, long name) {
        FileOutputStream outputStream = null;
        String path = null;
        String filename;
        try {
            ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());
            File directory = contextWrapper.getDir("ZXploreImages_" + sub, Context.MODE_PRIVATE);
            if (name != 0) {
                filename = name + ".png";
            } else {
                filename = System.currentTimeMillis() + ".png";
            }
            File file = new File(directory, filename);
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            path = file.getPath();
            outputStream.close();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return path;
    }

    public static Attachment getAttachedImage(List<Attachment> attachments, String type){
        Attachment photo = null;
        if (attachments != null && attachments.size() > 0){
            for (Attachment attachment : attachments){
                if (attachment.getType().equals(type)){
                    photo = attachment;
                }
            }
            return photo;
        }
        return null;
    }

    public static String encodeToBase64(String imagePath) throws IOException {
        ByteArrayOutputStream baos = null;
        byte[] byteArrayImage = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byteArrayImage = baos.toByteArray();
            baos.close();

        } catch (IOException io) {

        }
        return encodeToString(byteArrayImage, Base64.DEFAULT);
    }


    public static String charInitials(String name) {
        StringBuilder initials = new StringBuilder();
        for (String s : name.split(" ")) {
            initials.append(s.charAt(0));
        }
        return initials.toString();
    }

    public static Uri retrieveImage(String path) {
        Uri uri = null;
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                uri = fromFile(file);
            }
        }
        return uri;
    }

//    public static void addNotification(NotificationViewModel viewModel,
//                                       String title, String message, int type,
//                                       long formId) {
//        Notification notification = new Notification();
//        notification.setTitle(title);
//        notification.setMessage(message);
//        notification.setType(type);
//        notification.setFormId(formId);
//        notification.setStatus(1);
//        notification.setCreatedOn(AppUtility.getDate());
//        viewModel.insertNotification(notification);
//    }


    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static int getAge(int day, int month, int year){

        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(year, month, day);

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth.getTime());
        if (birthDate.after(today)) {
        }
        int todayYear = today.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);
        int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
        int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int birthDateMonth = birthDate.get(Calendar.MONTH);
        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
        int age = todayYear - birthDateYear;

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
            age--;
        }


        return age;
    }
}
