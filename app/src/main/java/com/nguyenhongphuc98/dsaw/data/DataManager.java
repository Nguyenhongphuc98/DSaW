package com.nguyenhongphuc98.dsaw.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.anychart.enums.CircularGaugePointerType;
import com.anychart.scales.DateTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenhongphuc98.dsaw.MainActivity;
import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.model.Account;
import com.nguyenhongphuc98.dsaw.data.model.Answer;
import com.nguyenhongphuc98.dsaw.data.model.AnswerViewModel;
import com.nguyenhongphuc98.dsaw.data.model.Area;
import com.nguyenhongphuc98.dsaw.data.model.Case;
import com.nguyenhongphuc98.dsaw.data.model.City;
import com.nguyenhongphuc98.dsaw.data.model.District;
import com.nguyenhongphuc98.dsaw.data.model.News;
import com.nguyenhongphuc98.dsaw.data.model.PublicData;
import com.nguyenhongphuc98.dsaw.data.model.Question;
import com.nguyenhongphuc98.dsaw.data.model.ReportModel;
import com.nguyenhongphuc98.dsaw.data.model.RouteData;
import com.nguyenhongphuc98.dsaw.data.model.Survey;
import com.nguyenhongphuc98.dsaw.data.model.SurveyModel;
import com.nguyenhongphuc98.dsaw.data.model.TrackingStatus;
import com.nguyenhongphuc98.dsaw.data.model.Ward;
import com.nguyenhongphuc98.dsaw.ui.home.HomeDelegate;
import com.nguyenhongphuc98.dsaw.ui.login.LoginActivity;
import com.nguyenhongphuc98.dsaw.ui.login.ResetPasswordActivity;
import com.nguyenhongphuc98.dsaw.ui.surveys.SurveyResultViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.google.firebase.storage.UploadTask;
import com.nguyenhongphuc98.dsaw.data.model.Account;
import com.nguyenhongphuc98.dsaw.data.model.AnswerViewModel;
import com.nguyenhongphuc98.dsaw.data.model.Area;
import com.nguyenhongphuc98.dsaw.data.model.Case;
import com.nguyenhongphuc98.dsaw.data.model.News;
import com.nguyenhongphuc98.dsaw.data.model.Post;
import com.nguyenhongphuc98.dsaw.data.model.Question;
import com.nguyenhongphuc98.dsaw.data.model.Warning;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

import com.nguyenhongphuc98.dsaw.data.model.Account;

import com.nguyenhongphuc98.dsaw.data.model.News;
import com.nguyenhongphuc98.dsaw.data.model.PublicData;
import com.nguyenhongphuc98.dsaw.data.model.Question;
import com.nguyenhongphuc98.dsaw.data.model.ReportModel;
import com.nguyenhongphuc98.dsaw.data.model.RouteData;
import com.nguyenhongphuc98.dsaw.data.model.Survey;
import com.nguyenhongphuc98.dsaw.data.model.SurveyModel;
import com.nguyenhongphuc98.dsaw.ui.home.HomeDelegate;
import com.nguyenhongphuc98.dsaw.ui.surveys.SurveyResultViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class DataManager {
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;
    LoginActivity loginProcess;
    ResetPasswordActivity resetPasswordActivity;

    Context mContext;

    private static DataManager _instance;
    public static DataManager Instance() {
        if(_instance == null) {
            _instance = new DataManager();
        }

        return _instance;
    }

    public static DataManager Instance(Context c) {
        if(_instance == null){
            _instance = new DataManager(c);
        }
        _instance.mContext = c;
        return _instance;
    }

    public DataManager() {
        internalInit();
    }

    public DataManager(Context c) {
        internalInit();
        mContext=c;
    }

    private void internalInit() {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void TestConnectDB() {
        final DatabaseReference organs_Reference = mDatabase.getReference("message");
        organs_Reference.setValue("this is test connenction from nguyenhongphuc98");
    }

    public void setLoginProcess(LoginActivity loginProcess) {
        this.loginProcess = loginProcess;
    }

    public void setResetPasswordActivity(ResetPasswordActivity resetPasswordActivity) {
        this.resetPasswordActivity = resetPasswordActivity;
    }

    public void ProcessLogin(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        //GetUserDataByEmail(email);
                        loginProcess.LoginSuccessful();
                        loginProcess.LoadUserDataComplete();
                        /*if(this.loginCallback!=null)
                        {
                            loginCallback.OnLoginComplete(LoginCallback.CODE_LOGIN_SUCCESS);
                            Log.d("DATAMANAGER","callback login");
                        }*/
                        Log.d("DATAMANAGER", "errcallback login");
                    }
                    else {
                        loginProcess.LoginFail();
                        /*if(loginCallback!=null)
                        {
                            loginCallback.OnLoginComplete(LoginCallback.CODE_LOGIN_INCORRECT);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }*/
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:failure");
                    }
                });

        /*if (mAuth.getCurrentUser() != null) return true;
        else return false;*/
    }

    public boolean registerProcess(String userName, String passWord) {

        Log.d(TAG, "createAccount:" + userName);

        // [START create_user_with_userName]
        mAuth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "createUserWithEmail:success");
                            // [START send_email_verification]
                            mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task1) {
                                            if (task1.isSuccessful()) {
                                                Log.d(TAG, "Verificate mail sent.");
                                            } else Log.w(TAG, "Send verificate mail failed.");
                                        }
                                    });
                            // [END send_email_verification]
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        // [END create_user_with_email]

        if (mAuth.getCurrentUser() != null) return true;
        else return false;
    }

    public Task sendMailResetPassword(String emailAddress) {
        return mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Reset email sent.");
                    }
                    else Log.w(TAG, "Send mail rest failed.");
                });
    }

    public void CreateNewAccount(Account account)
    {
        {
            try{
                String key=mDatabaseRef.child("Account").push().getKey();
                account.setId(key);
                Task task = mDatabaseRef.child("Account").child(key).setValue(account);
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DataManager",e.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Log.d("DataManager","Create new post success");
                    }
                });
            }
            catch (Exception e){
                Log.d("DataManager",e.toString());
            }
        }
    }

    public void CreateWarning(final Warning fWarning)
    {
        Query query = mDatabaseRef.child("Warnings");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 1;
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        count++;
                    }
                }

                Warning warning = new Warning(fWarning.getTitle(), fWarning.getContent(), fWarning.getCreator(), fWarning.getReceiver(), fWarning.getCode_city(), fWarning.getCode_district(), fWarning.getCode_ward());
                mDatabaseRef.child("Warnings").child(String.valueOf(count)).setValue(warning);
                //PushNotify(fWarning.getContent());
                Log.e("Data manager", "Add new warning successful");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    public void FetchWarning(final MutableLiveData<Warning> warningMutableLiveData)
    {
        Query query = mDatabaseRef.child("Warnings").orderByKey().limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //List<Warning> lsWarning = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Warning w = snapshot.getValue(Warning.class);
                        warningMutableLiveData.setValue(w);
                        Log.d(TAG, w.getTitle());
                        Log.d(TAG, w.getContent());
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    public void PushNotify(String content)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "CHANNEL_ID")
                .setSmallIcon(R.drawable.warning_icon)
                .setContentTitle("Cảnh báo nguy hiểm")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    public void GetUserData(final TextView name, final TextView identity, final TextView birthday, final TextView phonenumber)
    {
        try {
            Query query = mDatabase.getReference("Account").orderByChild("mail").equalTo(DataCenter.currentUser.getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Account account = snapshot.getValue(Account.class);

                            name.setText(account.getUsername());
                            identity.setText(account.getIdentity());
                            birthday.setText(account.getBirthday());
                            phonenumber.setText(account.getPhonenumber());

                            /*city = account.getCode_city();
                            district = account.getCode_district();
                            ward = account.getCode_ward();*/
                        }
                    }
                    else Log.e("DataManager","Account not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get account: " + e.getMessage());
        }
    }

    public void GetUserDataByEmail(String mail)
    {
        try {
            Query query = mDatabase.getReference("Account").orderByChild("mail").equalTo(mail);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Account account = snapshot.getValue(Account.class);
                            DataCenter.currentUser = account;
                        }
                    }
                    else Log.e("DataManager","Account not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get account: " + e.getMessage());
        }
    }

    public void UpdateUser(final String name, final String identity, final String birthday, final String phoneNumber,
                           final int code_city, int code_district, final int code_ward)
    {
        try {
            Query query = mDatabase.getReference("Account").orderByChild("mail").equalTo(DataCenter.currentUser.getEmail());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Account account = snapshot.getValue(Account.class);
                            account.setUsername(name);
                            account.setIdentity(identity);
                            account.setBirthday(birthday);
                            account.setPhonenumber(phoneNumber);
                            account.setCode_city(code_city);
                            account.setCode_district(code_district);
                            account.setCode_ward(code_ward);
                            DataCenter.currentUser = account;
                            mDatabase.getReference("Account").child(snapshot.getKey()).setValue(account);
                            Log.e("DataManager", "Update user successfully");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error update user: " + e.getMessage());
        }
    }

    public void GetNumberOfQuestion(final int amount)
    {
        try {
            Query query = mDatabase.getReference("Question").orderByChild("survey").equalTo("survey1_key");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        }
                    }
                    else Log.e("DataManager","Account not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get account: " + e.getMessage());
        }
    }

    public void GetAllQuestion(final List<Question> lsQuestion)
    {
        try {
            Query query = mDatabase.getReference("Question").orderByChild("survey").equalTo("survey4_key");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Question question = snapshot.getValue(Question.class);
                            lsQuestion.add(question);
                            Log.e("DataManager","Title of question was found is " + question.getTitle());
                            //Log.e("DataManager","Type of question was found is " + question.getType());
                            //Log.e("DataManager","Answer of question was found is " + question.getAnswers());
                        }
                    }
                    else Log.e("DataManager","Question not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get account: " + e.getMessage());
        }
    }

    public void GetAllQuestion(final MutableLiveData<List<Question>> mListQuestion)
    {
        try {
            Query query = mDatabase.getReference("Question").orderByChild("survey").equalTo(DataCenter.surveyID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<Question> lsQuestion = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Question question = snapshot.getValue(Question.class);
                            lsQuestion.add(question);
                            Log.e("DataManager","Title of question was found is " + question.getTitle());
                        }
                        mListQuestion.setValue(lsQuestion);
                    }
                    else Log.e("DataManager","Question not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get account: " + e.getMessage());
        }
    }

    public void AddNewSurvey(String type, List<Question> lsQuestion, String name)
    {
        try{
            //create new node event
            String key = mDatabaseRef.child("Survey").push().getKey();
            Survey newSurvey = new Survey(key, name, type);
            newSurvey.setId(key);

            //save to firebase
            Task task= mDatabaseRef.child("Survey").child(key).setValue(newSurvey);
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DataManager",e.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DataManager","Create new survey success");
                }
            });

            for (int i = 0; i < lsQuestion.size(); i++) {
                AddNewQuestion(lsQuestion.get(i).getTitle(), lsQuestion.get(i).getAnswers(), lsQuestion.get(i).getType(), key);
            }
        }
        catch (Exception e){
            Log.d("DataManager",e.toString());
        }
    }

    public void AddNewQuestion(String question, ArrayList<String> lsAnswer, String type, String survey)
    {
        Question newQues = new Question("setLater", lsAnswer, survey, question, type);
        //mDatabaseRef.child("Question").child("question4_key").setValue(newQues);

        try{
            //create new node event
            String key=mDatabaseRef.child("Question").push().getKey();
            newQues.setId(key);

            //save to firebase
            Task task= mDatabaseRef.child("Question").child(key).setValue(newQues);
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DataManager",e.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DataManager","Create new question success");
                }
            });
        }
        catch (Exception e){
            Log.d("DataManager",e.toString());
        }
    }

    public void AddNewAnswer(final String surveyKey, final String userId, final List<Question> questionList, final List<String> answerList)
    {
        Query query = mDatabaseRef.child("Answers").child(surveyKey).child(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        count++;
                    }
                }
                // save new answer here
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < questionList.size(); i++)
                {
                    if (questionList.get(i).getType().equalsIgnoreCase("image")) map.put(questionList.get(i).getId(), ">" + answerList.get(i));
                    else map.put(questionList.get(i).getId(), answerList.get(i));
                }
                mDatabaseRef.child("Answers").child(surveyKey).child(userId).child(String.valueOf(count)).updateChildren(map);
                Log.e("Data manager", "Add new answer successful");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    public void AddNewMultipleAnswer(final String surveyKey, final String userId, final List<Question> questionList, final ArrayList<ArrayList<String>> answerList, final ArrayList<ArrayList<Integer>> answerIdList)
    {
        Query query = mDatabaseRef.child("Answers").child(surveyKey).child(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        count++;
                    }
                }
                // save new answer here
                Map<String, Object> map = new HashMap<>();
                /*for (int i = 0; i < answerList.size(); i++)
                {
                    map.put(questionList.get(i).getId(), answerList.get(i));
                    //map.put(questionList.get(i).getId(), i);
                }*/
                for (int i = 0; i < answerIdList.size(); i++)
                {
                    map.put(questionList.get(i).getId(), answerIdList.get(i));
                }
                mDatabaseRef.child("Answers").child(surveyKey).child(userId).child(String.valueOf(count)).child("answers_key").updateChildren(map);
                Log.e("Data manager", "Add new answer successful");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    public void AddNewPost(String title, String link, String content, String idCover)
    {
        Post post = new Post(DataCenter.currentUser.getIdentity(), content, idCover, LocalDateTime.now().toString(), null, link, title, "write");

        try{
            String key=mDatabaseRef.child("Post").push().getKey();
            post.setId(key);
            Task task = mDatabaseRef.child("Post").child(key).setValue(post);
            task.addOnFailureListener(e -> Log.d("DataManager",e.toString())).addOnSuccessListener(o -> Log.d("DataManager","Create new post success"));
        }
        catch (Exception e){
            Log.d("DataManager",e.toString());
        }
    }

    public String UploadFileToFirebase(String folder, Uri uriOfImage)
    {
        String id;

        if (folder.equals("posts/")){
            Long localDateTime=System.currentTimeMillis();
            id = localDateTime.toString();
        }
        else id = "1";

        String child = folder + id;
        StorageReference childRef = mStorageRef.child(child);

        UploadTask uploadTask = childRef.putFile(uriOfImage);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("Data manager","save image suscess");
            }
        });
        return id;
    }

    public void UploadImageToReport(String id, String folder, Uri uriOfImage)
    {
        String child = folder + id;
        StorageReference childRef = mStorageRef.child(child);

        UploadTask uploadTask = childRef.putFile(uriOfImage);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("Data manager","save image suscess");
            }
        });
        //return id;
    }

    // Public data part
    // area : vn | tg
    public void SaveStatistic(final PublicData data){

        //update time format: "2020-05-19T23:00:00.000Z"
        String date = data.getUpdate_date();

//        PublicData d1 = new PublicData("tg","nil",
//                "Thế Giới","40",
//                "10","10",
//                "2020-05-19T23:00:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d1);
//
//        PublicData d2 = new PublicData("tg","nil",
//                "Thế Giới","150",
//                "7","100",
//                "2020-05-18T23:00:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d2);
//
//        PublicData d3 = new PublicData("tg","nil",
//                "Thế Giới","158",
//                "9","110",
//                "2020-05-17T23:00:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d3);
//
//        PublicData d4 = new PublicData("tg","nil",
//                "Thế Giới","220",
//                "5","230",
//                "2020-05-16T23:00:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d4);
//
//        PublicData d5 = new PublicData("tg","nil",
//                "Thế Giới","80",
//                "23","99",
//                "2020-05-15T23:00:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d5);
//
//        PublicData d6 = new PublicData("tg","nil",
//                "Thế Giới","10920186",
//                "481432","2558938",
//                "2020-07-22T16:40:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d6);
//
//        PublicData d7 = new PublicData("vn","nil",
//                "Việt Nam","396",
//                "0","365",
//                "2020-07-22T16:40:00.000Z");
//        mDatabaseRef.child("PublicData").push().setValue(d7);


        // Try to get curent static of this date to update
        Query query = mDatabaseRef.child("PublicData").orderByChild("update_date").equalTo(date);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    boolean found = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // co ton tai ngay hom nay nhung khong chac la co cua khu vuc can luu hay khong

                        if (snapshot.getValue(PublicData.class).getArea().equals(data.getArea())) {
                            mDatabaseRef.child("PublicData").child(snapshot.getKey()).setValue(data);
                            found = true;
                        }
                    }
                    // chua co du lieu ngay nay` cho khu vuc dang xet, chung ta se tao moi
                    if (found == false) {
                        Log.d("TAGGGG", "onDataChange: new update");
                        mDatabaseRef.child("PublicData").push().setValue(data);
                    }
                } else {
                    Log.d("TAGGGG", "onDataChange: new update");
                    // chua co khu vuc nao co du lieu ngay nay`
                    mDatabaseRef.child("PublicData").push().setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fetchPublicData(final MutableLiveData<List<PublicData>> ls, final String area) {

        // arrange increate so we get lastest date
        Query query = mDatabaseRef.child("PublicData").orderByChild("update_date").limitToLast(12);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    List<PublicData> newList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PublicData p = snapshot.getValue(PublicData.class);
                        if (p.getArea().equals(area))
                            newList.add(p);
                    }

                    ls.setValue(newList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // count number of reponse for special survey of a user
    int personalSurveyCount = 0;
    int relativesSurveyCount = 0;
    int reportSurveyCount = 0;
    public void fetchCountResponseFor(final MutableLiveData<List<SurveyModel>> ls,
                                      final List<SurveyModel> newList,
                                      final SurveyModel model,
                                      final String userID,
                                      final int numberOfSurvey,
                                      final String type) {

        Query query = mDatabaseRef.child("Answers").child(model.getId()).child(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = 0;
                if (dataSnapshot.exists()) {
                    count = dataSnapshot.getChildrenCount();
                }
                model.setCount(String.valueOf(count));

                if (type.equals("personal_medical")) {
                    personalSurveyCount ++;

                    // has process to last survey
                    if (personalSurveyCount == numberOfSurvey) {
                        ls.setValue(newList);
                        //reset for next fetch
                        personalSurveyCount = 0;
                        return;
                    }

                }

                if (type.equals("relatives_medical")) {
                    relativesSurveyCount ++;

                    // has process to last survey
                    if (relativesSurveyCount == numberOfSurvey) {
                        ls.setValue(newList);
                        //reset for next fetch
                        relativesSurveyCount = 0;
                        return;
                    }
                }

                if (type.equals("report")) {
                    reportSurveyCount ++;

                    // has process to last survey
                    if (reportSurveyCount == numberOfSurvey) {
                        ls.setValue(newList);
                        //reset for next fetch
                        reportSurveyCount = 0;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {  }
        });
    }

    int countSurveyResposeAdmin = 0;
    public void fetchCountResponseForAdmin(final MutableLiveData<List<SurveyModel>> ls,
                                      final List<SurveyModel> newList,
                                      final SurveyModel model,
                                      final int numberOfSurvey) {

        Query query = mDatabaseRef.child("Answers").child(model.getId());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = 0;
                if (dataSnapshot.exists()) {
                    // get list uid response this survey
                    Map<String, Object> userReponse = (HashMap<String, Object>) dataSnapshot.getValue();
                    //Log.d("TAGGG", "onDataChange: "+ userReponse);
                    for (String s : userReponse.keySet()) {
                        ArrayList<Map<String,Object>> ls = (ArrayList<Map<String,Object>>) userReponse.get(s);
                        count += ls.size();
                    }
                }
                model.setCount(String.valueOf(count));


                countSurveyResposeAdmin++;

                // has process to last survey
                if (countSurveyResposeAdmin == numberOfSurvey) {
                    ls.setValue(newList);
                    //reset for next fetch
                    countSurveyResposeAdmin = 0;
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {  }
        });
    }

    // List result depend on uid so we acn using it for fetch in user or admin view
    public void fetchListSurveyByType(final MutableLiveData<List<SurveyModel>> ls, final String type,final String uid) {

        Query query = null;
        if (uid.equals("admin"))
            query = mDatabaseRef.child("Survey");
        else
            query = mDatabaseRef.child("Survey").orderByChild("type").equalTo(type);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    List<SurveyModel> newList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Survey p = snapshot.getValue(Survey.class);
                        SurveyModel s = new SurveyModel(p.getId(),"0",p.getName(), p.getType());
                        newList.add(s);

                        if (uid.equals("admin"))
                            fetchCountResponseForAdmin(ls,newList,s,(int)dataSnapshot.getChildrenCount());
                        else
                            fetchCountResponseFor(ls,newList,s,uid,(int)dataSnapshot.getChildrenCount(), type);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Boolean fetchAllPosts(final MutableLiveData<List<News>> lsNews){

        try {

            Query query=mDatabaseRef.child("Post").orderByChild("createtime");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        List<News> ls = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            News n = snapshot.getValue(News.class);
                            ls.add(n);
                        }
                        lsNews.setValue(ls);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, databaseError.getMessage());
                }
            });
        }
        catch (Exception e){
            Log.e("DB","err get posts (news): "+e.getMessage());
            return false;
        }

        return true;
    }

    // folder = posts or report
    public void fetchPhoto(String fileName, final ImageView result, String folder) {
        mStorageRef.child(folder+"/"+fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext)
                        .load(uri)
                        .into(result);
                Log.e("DB","downloaded a photo:"+uri.getPath());
            }
        });
    }

    public void updateACcount(final Account user) {

        // arrange increate so we get lastest date
        Query query = mDatabaseRef.child("Account").orderByChild("identity").equalTo(user.getIdentity());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    List<PublicData> newList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //actualu it shoud have just one account match :))
                        String p = snapshot.getKey();
                        mDatabaseRef.child("Account").child(p).setValue(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fetchAllAreas(final MutableLiveData<List<Area>> lsAreas){

        Query query = mDatabaseRef.child("Area").orderByChild("name");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // get list area
//                        Map<String, Object> userReponse = (HashMap<String, Object>) dataSnapshot.getValue();
//                        for (String s : userReponse.keySet()) {
//                            ArrayList<Map<String,Object>> ls = (ArrayList<Map<String,Object>>) userReponse.get(s);
//                            count += ls.size();
//                        }

                    List<Area> ls = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Area n = snapshot.getValue(Area.class);
                        ls.add(n);
                    }
                    lsAreas.setValue(ls);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fetchAccountById(final String id, final MutableLiveData<Account> account) {

        Query query = mDatabaseRef.child("Account").orderByChild("identity").equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // We expect one account match
                        Account a = snapshot.getValue(Account.class);
                        account.setValue(a);
                        return;
                    }
                } else {
                    Account temp = new Account();
                    temp.setIdentity("null");
                    account.setValue(temp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fetchAccountByEmail(final String email, final MutableLiveData<Account> account) {

        Query query = mDatabaseRef.child("Account").orderByChild("mail").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // We expect one account match
                        Account a = snapshot.getValue(Account.class);
                        account.setValue(a);
                        return;
                    }
                } else {
                    Account temp = new Account();
                    temp.setIdentity("null");
                    account.setValue(temp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fetchAllAccount(final MutableLiveData<List<Account>> lsAccounts) {

        Query query = mDatabaseRef.child("Account");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    List<Account> ls = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // We expect one account match
                        Account a = snapshot.getValue(Account.class);
                        ls.add(a);
                    }

                    lsAccounts.setValue(ls);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Get cases base on query
    private void fetchCasesInfo(final MutableLiveData<List<Case>> lsCases, Query query) {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Case> ls = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    //just load case in this time, mean if da lanh benh thi k con nua
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
                    Date date = new Date();
                    String t = dateFormat.format(date);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Case a = snapshot.getValue(Case.class);
                        if (t.compareTo(a.getBegin_time()) >= 0 && t.compareTo(a.getEnd_time()) < 0 )
                            ls.add(a);
                    }
                }
                lsCases.setValue(ls);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Just manager can process case, and just case of area - which it manager
    public void fetchAllCaseOfArea(final MutableLiveData<List<Case>> lsCases, String area) {

        Query query = mDatabaseRef.child("Case").orderByChild("area").equalTo(area);

        fetchCasesInfo(lsCases, query);
    }

    // Fetch all case to visualize on map
    public void fetchAllCase(final MutableLiveData<List<Case>> lsCases) {

        Query query = mDatabaseRef.child("Case");

        fetchCasesInfo(lsCases, query);
    }

    public void insertCase(final Case aCase) {

        // check if exist need update status before
        // if exist id user in any case mean we have to update old and insert new
        Query query = mDatabaseRef.child("Case").orderByChild("user").equalTo(aCase.getUser());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
                    Date date = new Date();
                    String t = dateFormat.format(date);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // end time = zzz thi moi la cai can update
                        Case a = snapshot.getValue(Case.class);
                        if (a.getEnd_time().contains("zzzzzzz") ) {
                            a.setEnd_time(t);
                            mDatabaseRef.child("Case").child(snapshot.getKey()).setValue(a);
                        }

                    }
                }

                // cho du co ton tai truoc do hay khong thi van phai them moi nhu thuong
                // de trong nay de dam bao la kiem tra truoc khi insert, k la no xu ly cai vua insert thi chet toi :v
                String key = mDatabaseRef.child("Case").push().getKey();
                aCase.setId(key);
                mDatabaseRef.child("Case").child(key).setValue(aCase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void markWellCase(final Case aCase) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
        Date date = new Date();
        String t = dateFormat.format(date);

        mDatabaseRef.child("Case").child(aCase.getId()).child("end_time").setValue(t);

        Toast.makeText(mContext, "did well: "+ aCase.getId(), Toast.LENGTH_SHORT).show();
    }

    // featch all case to statitis (for admin) so we don't care about area. get all :v
    public void fetchAllCaseOfArea(final MutableLiveData<List<Case>> lsCases) {

        Query query = mDatabaseRef.child("Case");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    List<Case> ls = new ArrayList<>();
                    //just load case in this time, mean if da lanh benh thi k con nua
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
                    Date date = new Date();
                    String t = dateFormat.format(date);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Case a = snapshot.getValue(Case.class);

                        // mean endtime = zzz moi lay :v
                        if (t.compareTo(a.getBegin_time()) > 0 && t.compareTo(a.getEnd_time()) < 0 )
                            ls.add(a);
                    }

                    lsCases.setValue(ls);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /// Update status route data of secial user
    // if exists, update, else create new
    public void updateRouteData(final RouteData routeData) {

        // check exist route of this user
        Query query = mDatabaseRef.child("RouteData").orderByChild("user").equalTo(routeData.getUser());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    // just update status tracking
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //actualy it shoud have just one route match :))
                        String routeKey = snapshot.getKey();
                        RouteData r = snapshot.getValue(RouteData.class);
                        r.addStatus(routeData.getStatus().get(0));
                        mDatabaseRef.child("RouteData").child(routeKey).setValue(r);
//                        String keyOfStatus = mDatabaseRef.child("RouteData").child(routeKey).child("status").push().getKey();
//                        mDatabaseRef.child("RouteData").child(routeKey).child("status").child(keyOfStatus).setValue(routeData.getStatus());
                        return;
                    }
                } else {
                    // insert new data new user :)
                    String routeKey = mDatabaseRef.child("RouteData").push().getKey();
                    routeData.setId(routeKey);
                    mDatabaseRef.child("RouteData").child(routeKey).setValue(routeData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /// Fetch route for special user
    public void fetchRouteOf(final MutableLiveData<RouteData> routeData, String uid) {

        Query query = mDatabaseRef.child("RouteData").orderByChild("user").equalTo(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RouteData a = snapshot.getValue(RouteData.class);
                        Collections.reverse(a.getStatus());
                        routeData.setValue(a);
                        return;
                    }
                }
                routeData.setValue(new RouteData());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /// Fetch last location for special user
    public void fetchLastLocationOfUser(final MutableLiveData<TrackingStatus> trackingStatus, String uid) {

        Query query = mDatabaseRef.child("RouteData").orderByChild("user").equalTo(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RouteData a = snapshot.getValue(RouteData.class);
                        // item cuoi cung chinh la cai moi nhat
                        trackingStatus.setValue(a.getStatus().get(a.getStatus().size() - 1));

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /// Lay ra cau tra loi cua tat cua user nam trong khu vuc duoc chi dinh

    /// Lay ra thong ke cau tra loi cua cac cau hoi trong 1 survey
    public void fetchAnswerFor(final MutableLiveData<List<AnswerViewModel>> answersResult, final String surveyid) {

        Query query = mDatabaseRef.child("Answers").child(surveyid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // key = account id
                    // value list instance of a answer
                    Map<String, Object> userReponse = (HashMap<String, Object>) dataSnapshot.getValue();


                    /// map <questionID - hashMap<answer mau id, count checked>>
                    final HashMap<String, HashMap<Long,Long>> result = new HashMap<>();

                    // duyet tat ca account da tra loi survey nay
                    for (String accountID : userReponse.keySet()) {

                        ArrayList<Map<String,Object>> ls = (ArrayList<Map<String,Object>>) userReponse.get(accountID);

                        // submit cuoi cung la submit gan day nhat va han anh dung nhat cho thong ke hien tai
                        Map<String,Object> lastSubmitAnswer = ls.get(ls.size() - 1);

                        /// key - question key
                        /// value -list caclua chon da duoc tick cho mutichoice Q
                        Map<String,List<Long>> ansewerMutilChoices = (Map<String, List<Long>>) lastSubmitAnswer.get("answers_key");
                        
                        // duyet het tat ca cau hoi trong phan tra loi
                        for(Map.Entry<String, List<Long>> entry : ansewerMutilChoices.entrySet()) {
                            String key = entry.getKey();
                            List<Long> answerSelected = entry.getValue();

                            if (result.get(key) == null) {
                                // neu day la lan dau count cau tl cho cau hoi nay
                                // <key dc check - count>
                                HashMap ans = new HashMap<Long, Long>();
                                for (Long i : answerSelected) {
                                    ans.put(i, (long)1);
                                }

                                result.put(key, ans);
                            } else {
                                // neu day la lan thu 2+ thi phai count len 1 neu tim thay
                                for (Long i : answerSelected) {
                                    // chua co ai tick cau tra loi nay ca
                                    if (result.get(key).get(i) == null)
                                        result.get(key).put(i, (long) 1);
                                    else
                                        result.get(key).put(i,(Long) (result.get(key).get(i) + 1));
                                }

                            }
                        }
                    }

                    final List<AnswerViewModel> answers = new ArrayList<>();
                    /// sau khi thong ke xong can map cu the cau hoi do la gi va cac cau tra loi
                    Query query = mDatabaseRef.child("Question").orderByChild("survey").equalTo(surveyid);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Question q = snapshot.getValue(Question.class);
                                    AnswerViewModel a = new AnswerViewModel();
                                    a.setQuestionTitle(q.getTitle());

                                    List<String> mAnswers = new ArrayList<>();
                                    // lay ra tat ca cau tra loi goi y va gan so luong submit (checked)
                                    for (int i = 0; i < q.getAnswers().size(); i++) {
                                        // co the la chua ai check cai nay ca moi dau ne. vay thi cho no = 0
                                        // neu cau hoi nay k co thi cung  = 0 luon, ma thuc ra la phai co chu :v
                                        if (result.get(q.getId()) != null
                                                && result.get(q.getId()).get((Long)(long) i) != null)
                                            mAnswers.add(q.getAnswers().get(i) + "(" + result.get(q.getId()).get((Long)(long) i) +")");
                                        else
                                            mAnswers.add(q.getAnswers().get(i) + "(0)");
                                    }

                                    if (mAnswers.size() == 0)
                                        mAnswers.add("Chưa có người trả lời");

                                    a.setAnswers(mAnswers);
                                    answers.add(a);
                                }
                            }
                            answersResult.setValue(answers);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else
                    answersResult.setValue(new ArrayList<AnswerViewModel>());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /// Fetch cau tra loi danh rieng cho survey co cau truc report
    public void fetchAnswerForReport(final MutableLiveData<List<ReportModel>> answersResult, final String surveyid) {

        Query query = mDatabaseRef.child("Answers").child(surveyid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<ReportModel> listAnswers = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    // key = account id
                    // value list instance of a answer
                    Map<String, Object> userReponse = (HashMap<String, Object>) dataSnapshot.getValue();

                    // duyet tat ca account da tra loi survey nay
                    for (String accountID : userReponse.keySet()) {

                        // List<qid,textAnswer>
                        ArrayList<Map<String, String>> ls = (ArrayList<Map<String, String>>) userReponse.get(accountID);
                        for (Map map : ls) {
                            List<String> oneAnswer = new ArrayList<>();
                            ReportModel model = new ReportModel();
                            for (Object qid : map.keySet()) {
                                if (map.get(qid).toString().charAt(0) == '>')
                                {
                                    String[] imageName = map.get(qid).toString().split(">");
                                    model.setImageUrl(imageName[1]);
                                }  else
                                    oneAnswer.add(qid + ": "+ map.get(qid));
                            }
                            model.setLsAnswers(oneAnswer);
                            listAnswers.add(model);
                        }
                    }
                }

                // replace question key by title of question
                Query query = mDatabaseRef.child("Question").orderByChild("survey").equalTo(surveyid);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Question q = snapshot.getValue(Question.class);
                                for (ReportModel r : listAnswers) {
                                    List<String> newAnswers= new ArrayList<>();
                                    for (String s : r.getLsAnswers()) {
                                        String t = s.replace(q.getId(),q.getTitle());
                                        newAnswers.add(t);
                                    }
                                    r.setLsAnswers(newAnswers);
                                }
                            }
                        }
                        answersResult.setValue(listAnswers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void GetAllmCity(final MutableLiveData<List<City>> mListCity)
    {
        try {
            Query query = mDatabase.getReference("City");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<City> lsCity = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            City city = snapshot.getValue(City.class);
                            lsCity.add(city);
                            Log.e("DataManager","City was found is " + city.getName());
                        }
                        mListCity.setValue(lsCity);
                    }
                    else Log.e("DataManager","City not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get city: " + e.getMessage());
        }
    }

    public void GetmDistrictOfCity(final MutableLiveData<List<District>> mLsDistrict, String code)
    {
        try {
            //Query query = mDatabase.getReference("City").child(id).child("district");
            Query query = mDatabase.getReference("City").child(code).child("district");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<District> lsDistrict = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            District district = snapshot.getValue(District.class);
                            lsDistrict.add(district);
                            Log.e("DataManager","District was found is " + district.getName());
                        }
                        mLsDistrict.setValue(lsDistrict);
                    }
                    else Log.e("DataManager","District not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get district: " + e.getMessage());
        }
    }

    public void GetmWardOfDistrict(final MutableLiveData<List<Ward>> mLsWard, String cityCode, String districtCode)
    {
        try {
            Query query = mDatabase.getReference("City").child(cityCode).child("district").child(districtCode).child("wards");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<Ward> lsWard = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Ward ward = snapshot.getValue(Ward.class);
                            lsWard.add(ward);
                            Log.e("DataManager","Ward was found is " + ward.getName());
                        }
                        mLsWard.setValue(lsWard);
                    }
                    else Log.e("DataManager","Ward not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get district: " + e.getMessage());
        }
    }

    public void GetAllmCityWarning(final MutableLiveData<List<City>> mListCity)
    {
        try {
            Query query = mDatabase.getReference("City");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<City> lsCity = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            City city = snapshot.getValue(City.class);
                            lsCity.add(city);
                            Log.e("DataManager","City was found is " + city.getName());
                        }
                        mListCity.setValue(lsCity);
                    }
                    else Log.e("DataManager","City not found");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get city: " + e.getMessage());
        }
    }

    public void GetmDistrictOfCityWarning(final MutableLiveData<List<District>> mLsDistrict, String code)
    {
        try {
            //Query query = mDatabase.getReference("City").child(id).child("district");
            Query query = mDatabase.getReference("City").child(code).child("district");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<District> lsDistrict = new ArrayList<>();
                        lsDistrict.add(new District("Tất cả","0"));
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            District district = snapshot.getValue(District.class);
                            lsDistrict.add(district);
                            Log.e("DataManager","District was found is " + district.getName());
                        }
                        mLsDistrict.setValue(lsDistrict);
                    }
                    else
                    {
                        Log.e("DataManager","District not found");
                        List<District> lsDistrict = new ArrayList<>();
                        lsDistrict.add(new District("--","0"));
                        mLsDistrict.setValue(lsDistrict);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get district: " + e.getMessage());
        }
    }

    public void GetmWardOfDistrictWarning(final MutableLiveData<List<Ward>> mLsWard, String cityCode, String districtCode)
    {
        try {
            Query query = mDatabase.getReference("City").child(cityCode).child("district").child(districtCode).child("wards");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<Ward> lsWard = new ArrayList<>();
                        lsWard.add(new Ward("Tất cả","0"));
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Ward ward = snapshot.getValue(Ward.class);
                            lsWard.add(ward);
                            Log.e("DataManager","Ward was found is " + ward.getName());
                        }
                        mLsWard.setValue(lsWard);
                    }
                    else
                    {
                        Log.e("DataManager","Ward not found");
                        List<Ward> lsWard = new ArrayList<>();
                        lsWard.add(new Ward("--","0"));
                        mLsWard.setValue(lsWard);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DataManager","Error get district: " + e.getMessage());
        }
    }
}
